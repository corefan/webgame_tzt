package com.snail.webgame.engine.component.scene.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.protocol.Message;
import org.epilot.ccf.core.protocol.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.common.cache.DropBagMap;
import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.common.info.AIInfo;
import com.snail.webgame.engine.common.info.BuffInfo;
import com.snail.webgame.engine.common.info.DefendInfo;
import com.snail.webgame.engine.common.info.DropBag;
import com.snail.webgame.engine.common.info.EquipInfo;
import com.snail.webgame.engine.common.info.EquipRel;
import com.snail.webgame.engine.common.info.NPCInfo;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.common.info.ViewChangeInfo;
import com.snail.webgame.engine.common.pathfinding.astar.Step;
import com.snail.webgame.engine.common.pathfinding.mesh.GameMap3D;
import com.snail.webgame.engine.common.pathfinding.mesh.Path3D;
import com.snail.webgame.engine.common.pathfinding.mesh.Step3D;
import com.snail.webgame.engine.common.util.DataFormat;
import com.snail.webgame.engine.component.scene.GameMessageHead;
import com.snail.webgame.engine.component.scene.cache.NPCInfoMap;
import com.snail.webgame.engine.component.scene.cache.ServerMap;
import com.snail.webgame.engine.component.scene.config.GameConfig;
import com.snail.webgame.engine.component.scene.event.EventActor;
import com.snail.webgame.engine.component.scene.protocal.buff.BuffResp;
import com.snail.webgame.engine.component.scene.protocal.drop.TransDropBag;
import com.snail.webgame.engine.component.scene.protocal.equip.queryrel.EquipRelRe;
import com.snail.webgame.engine.component.scene.protocal.fight.attack.AttackResp;
import com.snail.webgame.engine.component.scene.protocal.fight.skill.DefendInfoResp;
import com.snail.webgame.engine.component.scene.protocal.fight.skill.SkillAttackResp;
import com.snail.webgame.engine.component.scene.protocal.move.AreaChangeResp;
import com.snail.webgame.engine.component.scene.protocal.move.MoveEndResp;
import com.snail.webgame.engine.component.scene.protocal.move.MoveResp;
import com.snail.webgame.engine.component.scene.protocal.npc.TransformNPCInfo;
import com.snail.webgame.engine.component.scene.protocal.otherrole.OtherRole;
import com.snail.webgame.engine.component.scene.protocal.rebirth.RebirthResp;


public class SceneService {
	private static final Logger logger = LoggerFactory.getLogger("logs");
	
	/**
	 * 给附近角色发送移动(排除自己)
	 */
	public static void sendMoveMsgExcludeSelf(AIInfo aiInfo, Path3D path)
	{
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(aiInfo.getMapId());
		if (gameMap == null)
			return;
		
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		List<Long> roleIds = gameMap.getAreaOtherRole(aiInfo, refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		byte actionActor = EventActor.EVENT_ACTOR_PLAYER;
		if (aiInfo instanceof NPCInfo) {
			actionActor = EventActor.EVENT_ACTOR_NPC;
		}
		RoleInfo roleInfo = null;
		for(long roleId : roleIds){
			// 如果是npc的移动，该对象没有gateServerId这个属性，则取不到对应的session
			roleInfo = RoleInfoMap.getRoleInfo(roleId);
			// 如果缓存中没有该角色对象
			if (roleInfo == null) {
				continue;
			}
			
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB006);
			header.setUserID0((int)roleId);
			message.setHeader(header);
			
			MoveResp resp = new MoveResp();
			resp.setActionActor(actionActor);
			resp.setRoleId(aiInfo.getId());
			resp.setCurrX(aiInfo.getCurrX());
			resp.setCurrY(aiInfo.getCurrY());
			resp.setCurrZ(aiInfo.getCurrZ());
			
			resp.setMoveMaxTime(aiInfo.getCurrMoveMaxTime());
			// 将移动路径转换成字符串对象
			String str = parseMovePath(path);
			
			resp.setMoveList(str);
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
					"-" + roleInfo.getGateServerId());
			
			sendMessage(session, message, str, roleId);
		}
	}
	
	/**
	 * 给附近角色发送移动
	 */
	public static void sendMoveMsg(AIInfo aiInfo, Path3D path)
	{
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(aiInfo.getMapId());
		if (gameMap == null)
			return;
		
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		byte actionActor = EventActor.EVENT_ACTOR_PLAYER;
		// 如果是玩家移动，则查询附近其他的玩家
		List<Long> roleIds = gameMap.getAreaAllRole((int)aiInfo.getCurrX(), (int)aiInfo.getCurrY(),
				(int)aiInfo.getCurrZ(), refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		// 如果为npc的移动，则查询附近所有的玩家
		if (aiInfo instanceof NPCInfo) {
			actionActor = EventActor.EVENT_ACTOR_NPC;
		}
		//aiInfo.setOtherRoleIds(roleIds);
		RoleInfo roleInfo = null;
		
		for(long roleId : roleIds){
			roleInfo = RoleInfoMap.getRoleInfo(roleId);
			// 如果缓存中没有该角色对象
			if (roleInfo == null) {
				continue;
			}
			
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB006);
			header.setUserID0((int)roleId);
			message.setHeader(header);
			
			MoveResp resp = new MoveResp();

			resp.setActionActor(actionActor);
			resp.setRoleId(aiInfo.getId());
			resp.setCurrX(aiInfo.getCurrX());
			resp.setCurrY(aiInfo.getCurrY());
			resp.setCurrZ(aiInfo.getCurrZ());

			resp.setMoveMaxTime(aiInfo.getCurrMoveMaxTime());
			
			String str = parseMovePath(path);
			
			resp.setMoveList(str);
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
					"-" + roleInfo.getGateServerId());
			
			sendMessage(session, message, str, roleId);
		}
	}
	
	/**
	 * 给玩家roleId发送对象aiInfo的移动消息
	 * @param session
	 * @param message
	 * @param path
	 * @author wangxf
	 * @param roleId 
	 * @date 2012-8-14
	 */
	private static void sendMessage(IoSession session, Message message,
			String moveList, long roleId) {
		if(session != null && session.isConnected())
		{ 
			session.write(message);
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("game", "GAME_SCENE_INFO_4") + ": roleId = " + roleId + 
						", moveList=" + moveList);
			}
		}
		
	}

	/**
	 * 给指定角色发送移动
	 */
	public static void sendMoveMsg(RoleInfo roleInfo, List<Long> target, Path3D path)
	{
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(roleInfo.getMapId());
		if (gameMap == null)
			return;
		
		for(long roleId : target){
		
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB006);
			header.setUserID0((int)roleId);
			message.setHeader(header);
			
			MoveResp resp = new MoveResp();
			resp.setRoleId(roleInfo.getId());
			resp.setCurrX(roleInfo.getCurrX());
			resp.setCurrY(roleInfo.getCurrY());
			resp.setCurrZ(roleInfo.getCurrZ());
			resp.setMoveMaxTime(roleInfo.getCurrMoveMaxTime());
		
			String str = parseMovePath(path);
			
			resp.setMoveList(str);
			message.setBody(resp);
			
			
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+roleInfo.getGateServerId());
			if(session!=null&&session.isConnected())
			{ 
				session.write(message);
				
				if(logger.isInfoEnabled())
				{
					logger.info(Resource.getMessage("game", "GAME_SCENE_INFO_4")+":roleId="+roleId + ", currX="+ roleInfo.getCurrX() + ",currY=" + roleInfo.getCurrY()
							+ ", aimX=" + roleInfo.getAimX() + ",aimY=" + roleInfo.getAimY());
				}
	 
			}
		}
	}
	
	/**
	 * 生成移动点的字符串
	 * @param path
	 * @return
	 * @author wangxf
	 * @date 2012-8-14
	 */
	private static String parseMovePath(Path3D path) {
		String str = "";
		ArrayList<Step3D> list = path.getSteps();
		for(int k = 0; k < list.size(); k++)
		{
			Step3D step = list.get(k);
			str = str + step.getX() + "," + step.getY() + "," + step.getZ() + ",";
		}
		str = str.substring(0, str.length()-1);
		return str;
	}

	/**
	 * 发送移动停止
	 */
	public static void sendMoveEndMsg(AIInfo aiInfo)
	{
		GameMap3D gameMap = SceneGameMapFactory
				.getGameMap(aiInfo.getMapId());
		if (gameMap == null)
			return;
		// 设置状态为停止
		aiInfo.setStatus(0);
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		// 查询当前对象所在点范围内的玩家
		List<Long> roleIds = gameMap.getAreaAllRole((int)aiInfo.getCurrX(), (int)aiInfo.getCurrY(),
				(int)aiInfo.getCurrZ(), refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		byte actionActor = EventActor.EVENT_ACTOR_PLAYER;
		// 如果是NPC，则表示动作发起者为NPC
		if (aiInfo instanceof NPCInfo) {
			actionActor = EventActor.EVENT_ACTOR_NPC;
		}
		RoleInfo roleInfo = null;
		for(long roleId : roleIds){
			roleInfo = RoleInfoMap.getRoleInfo(roleId);
			if (roleInfo == null) {
				continue;
			}
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB007);
			header.setUserID0((int)roleId);
			message.setHeader(header);
			
			MoveEndResp resp = new MoveEndResp();
			resp.setActionActor(actionActor);
			resp.setRoleId(aiInfo.getId());
			resp.setCurrX(aiInfo.getCurrX());
			resp.setCurrY(aiInfo.getCurrY());
			resp.setCurrZ(aiInfo.getCurrZ());
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + "-" + roleInfo.getGateServerId());
			if(session != null && session.isConnected())
			{ 
				session.write(message);
				
				if(logger.isInfoEnabled())
				{
					logger.info(Resource.getMessage("game", "GAME_SCENE_INFO_5") + ":roleId=" 
							+ roleId + ", " + aiInfo.getClass().getSimpleName() + ", x=" + resp.getCurrX() + ", y=" + resp.getCurrY() + 
							", z=" + resp.getCurrZ());
				}
	 
			}
		}
	}
	
	/**
	 * 发送移动停止，排除自己
	 */
	public static void sendMoveEndMsgExcludeSelf(AIInfo aiInfo)
	{
		GameMap3D gameMap = SceneGameMapFactory
				.getGameMap(aiInfo.getMapId());
		if (gameMap == null)
			return;
		
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		byte actionActor = EventActor.EVENT_ACTOR_PLAYER;
		// 如果是NPC，则表示动作发起者为NPC
		if (aiInfo instanceof NPCInfo) {
			actionActor = EventActor.EVENT_ACTOR_NPC;
		}
		List<Long> roleIds = gameMap.getAreaOtherRole(aiInfo, refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		RoleInfo roleInfo = null;
		for(long roleId : roleIds){
			roleInfo = RoleInfoMap.getRoleInfo(roleId);
			// 如果缓存中没有改角色对象
			if (roleInfo == null) {
				continue;
			}
		
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB007);
			header.setUserID0((int)roleId);
			message.setHeader(header);
			
			MoveEndResp resp = new MoveEndResp();
			resp.setActionActor(actionActor);
			resp.setRoleId(aiInfo.getId());
			resp.setCurrX(aiInfo.getCurrX());
			resp.setCurrY(aiInfo.getCurrY());
			resp.setCurrZ(aiInfo.getCurrZ());
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
					"-" + roleInfo.getGateServerId());
			if(session != null && session.isConnected())
			{ 
				session.write(message);
				
				if(logger.isInfoEnabled())
				{
					logger.info(Resource.getMessage("game", "GAME_SCENE_INFO_5") + 
							":roleId = " + roleId + ", x=" + resp.getCurrX() + 
							", y=" + resp.getCurrY() + ", z=" + resp.getCurrZ());
				}
			}
		}
	}
	
	private static void addNPCResp(AreaChangeResp resp, List<Long> addNpcIds, RoleInfo roleInfo){
		List<TransformNPCInfo> npcInfoList = new ArrayList<TransformNPCInfo>();
		TransformNPCInfo transformNPCInfo = null;
		for(long npcId : addNpcIds){
			NPCInfo npcInfo = (NPCInfo) NPCInfoMap.getNPCInfo(roleInfo.getMapId(), npcId);
			if(npcInfo != null){
				// npc增加附近玩家
				npcInfo.addOtherRoleId(roleInfo.getId());
				transformNPCInfo = new TransformNPCInfo();
				
				transformNPCInfo.setId(npcInfo.getId());
				transformNPCInfo.setNo(npcInfo.getNo());
				transformNPCInfo.setName(npcInfo.getName());
				transformNPCInfo.setNpcType(npcInfo.getNpcType());
				transformNPCInfo.setLevel(npcInfo.getLevel());
				transformNPCInfo.setCurrX(npcInfo.getCurrX());
				transformNPCInfo.setCurrY(npcInfo.getCurrY());
				transformNPCInfo.setCurrZ(npcInfo.getCurrZ());
				transformNPCInfo.setMapId(npcInfo.getMapId());
				transformNPCInfo.setMaxHP(npcInfo.getMaxHP());
				transformNPCInfo.setMaxMP(npcInfo.getMaxMP());
				transformNPCInfo.setCurrHP(npcInfo.getCurrHP());
				transformNPCInfo.setCurrMP(npcInfo.getCurrMP());
				transformNPCInfo.setCurrAttack(DataFormat.getDouble2Scale(npcInfo.getCurrAttack()));
				transformNPCInfo.setCurrDefend(DataFormat.getDouble2Scale(npcInfo.getCurrDefend()));
				transformNPCInfo.setAction(npcInfo.getAction());
				transformNPCInfo.setCurrMaxMoveTime(npcInfo.getCurrMoveMaxTime());
				transformNPCInfo.setFaceMode(npcInfo.getFaceMode());
				transformNPCInfo.setStatus(npcInfo.getStatus());
				Path3D path = npcInfo.getPath();
				if(path != null){
					String str = parseMovePath(path);
					transformNPCInfo.setMoveList(str);
				}
				transformNPCInfo.setFunction(npcInfo.getFunction());
				BuffService.checkPhalanxBuff(npcInfo);
				List<BuffInfo> buffList = npcInfo.getBuffList();
				List<BuffResp> buffRespList = new ArrayList<BuffResp>();
				if(buffList != null && !buffList.isEmpty()){
					for(BuffInfo buff : buffList){
						BuffResp buffResp = new BuffResp();
						buffResp.setBuffId(buff.getId());
						buffResp.setBuffNo(buff.getBuffNo());
						buffResp.setBuffAim(npcInfo.getId());
						buffResp.setBuffAimType(0);
						buffResp.setBuffType(buff.getType());
						buffResp.setBuffTime(buff.getBuffTime()-(int)((System.currentTimeMillis()-buff.getCreateTime())/1000));
						buffRespList.add(buffResp);
					}
				}
				transformNPCInfo.setBuffCount(buffRespList.size());
				transformNPCInfo.setBuffList(buffRespList);
				
				npcInfoList.add(transformNPCInfo);
			}
		}
		resp.setNpcCount(npcInfoList.size());
		resp.setNpcInfoList(npcInfoList);
	}
	
	private static void addRoleResp(AreaChangeResp resp, List<Long> addRoleIds){
		List<OtherRole> otherRoleList = new ArrayList<OtherRole>();
		for(long roleId : addRoleIds){
			RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
			if(roleInfo != null){
				OtherRole otherRole = new OtherRole();
				otherRole.setRoleId(roleInfo.getId());
				otherRole.setRoleName(roleInfo.getRoleName());
				otherRole.setSex(roleInfo.getSex());
				otherRole.setLevel(roleInfo.getLevel());
				otherRole.setSceneId(roleInfo.getSceneId());
				otherRole.setCurrX(roleInfo.getCurrX());
				otherRole.setCurrY(roleInfo.getCurrY());
				otherRole.setCurrZ(roleInfo.getCurrZ());
				otherRole.setMapId(roleInfo.getMapId());
				otherRole.setCurrHP(roleInfo.getCurrHP());
				otherRole.setCurrMP(roleInfo.getCurrMP());
				otherRole.setMaxHP(roleInfo.getMaxHP());
				otherRole.setMaxMP(roleInfo.getMaxMP());
				otherRole.setCurrAttack(DataFormat.getDouble2Scale(roleInfo.getCurrAttack()));
				otherRole.setCurrDefend(DataFormat.getDouble2Scale(roleInfo.getCurrDefend()));
				otherRole.setAction(roleInfo.getAction());
				otherRole.setCurrMaxMoveTime(roleInfo.getCurrMoveMaxTime());
				otherRole.setFaceMode(roleInfo.getFaceMode());
				otherRole.setStatus(roleInfo.getStatus());
				Path3D path = roleInfo.getPath();
				if(path != null){
					String str = parseMovePath(path);
					otherRole.setMoveList(str);
				}
				
				List<EquipRelRe> equipRelReList = new ArrayList<EquipRelRe>();
				Map<Integer, EquipRel> equipRelMap = roleInfo.getEquipRelMap();
				if(equipRelMap != null && !equipRelMap.isEmpty()){
					Map<Long, EquipInfo> equipMap = roleInfo.getEquipMap();
					if(equipMap != null){
						Set<Integer> keys = equipRelMap.keySet();
						for(Integer key : keys){
							EquipRel equipRel = equipRelMap.get(key);
							EquipInfo equipInfo = equipMap.get(equipRel.getEquipId());
							if(equipInfo == null)
								continue;
							EquipRelRe re = new EquipRelRe();
							re.setId(equipRel.getId());
							re.setEquipId(equipRel.getEquipId());
							re.setPartNO(equipRel.getPartNO());
							
							re.setEquipNo(equipInfo.getEquipNO());
							equipRelReList.add(re);
						}
					}
				}
				otherRole.setEquipRelCount(equipRelReList.size());
				otherRole.setEquipRelList(equipRelReList);
				
				BuffService.checkPhalanxBuff(roleInfo);
				List<BuffInfo> buffList = roleInfo.getBuffList();
				List<BuffResp> buffRespList = new ArrayList<BuffResp>();
				if(buffList != null && !buffList.isEmpty()){
					for(BuffInfo buff : buffList){
						BuffResp buffResp = new BuffResp();
						buffResp.setBuffId(buff.getId());
						buffResp.setBuffNo(buff.getBuffNo());
						buffResp.setBuffAim(roleInfo.getId());
						buffResp.setBuffAimType(1);
						buffResp.setBuffType(buff.getType());
						buffResp.setBuffTime(buff.getBuffTime()-(int)((System.currentTimeMillis()-buff.getCreateTime())/1000));
						buffRespList.add(buffResp);
					}
				}
				otherRole.setBuffCount(buffRespList.size());
				otherRole.setBuffList(buffRespList);
				
				otherRoleList.add(otherRole);
			}
		}
		resp.setOtherRolecount(otherRoleList.size());
		resp.setOtherRoleList(otherRoleList);
	}
	/**
	 * 发送附近NPC和玩家的变化
	 */
	public static void sendAreaChangeMsg(RoleInfo roleInfo, ViewChangeInfo viewChangeInfo)
	{
		Message message = new Message();
		GameMessageHead header = new GameMessageHead();
		header.setMsgType(0xB008);
		header.setUserID0((int)roleInfo.getId());
		message.setHeader(header);
		
		AreaChangeResp resp = new AreaChangeResp();
		
		StringBuffer roleIdStr = new StringBuffer();
		for(long roleId : viewChangeInfo.getDelRoleIds()){
			roleIdStr.append(roleId);
			roleIdStr.append(",");
		}
		if(roleIdStr.length() > 0){
			String result = roleIdStr.substring(0, roleIdStr.length()-1);
			resp.setDelRoleId(result);
		}
		
		StringBuffer npcIdStr = new StringBuffer();
		for(long npcId : viewChangeInfo.getDelNPCIds()){
			NPCInfo npcInfo = NPCInfoMap.getNPCInfo(roleInfo.getMapId(), npcId);
			if (npcInfo != null) {
				// 删除npc附近的玩家
				npcInfo.getOtherRoleIds().remove(roleInfo.getId());
				
				npcIdStr.append(npcId);
				npcIdStr.append(",");
			}
		}
		if(npcIdStr.length() > 0){
			String result = npcIdStr.substring(0, npcIdStr.length() - 1);
			resp.setDelNpcId(result);
		}
		
		// 删除消失的掉落包
		StringBuffer dropBagIdStr = new StringBuffer();
		for (long dropBagId : viewChangeInfo.getDelDropBagIds()) {
			dropBagIdStr.append(dropBagId);
			dropBagIdStr.append(",");
		}
		if (dropBagIdStr.length() > 0) {
			String result = dropBagIdStr.substring(0, dropBagIdStr.length() - 1);
			/*System.out.println("====删除的掉落包" + viewChangeInfo.getDelDropBagIds().size() + 
					"," + dropBagIdStr.toString() + "," + result);*/
			resp.setDelDropBagIds(result);
		}
		
		addNPCResp(resp, viewChangeInfo.getAddNPCIds(), roleInfo);
		
		addRoleResp(resp, viewChangeInfo.getAddRoleIds());
		
		addDropBagResp(resp, viewChangeInfo.getAddDropBagIds(), roleInfo);
		
		message.setBody(resp);
		
		//System.out.println("wangxf======" + getAreaChangeMsg(resp));
		
		IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
				"-" + roleInfo.getGateServerId());
		if(session != null && session.isConnected())
		{ 
			session.write(message);
		}
	}
	
	/**
	 * 
	 * @param resp
	 * @param addDropBagIds
	 * @param roleInfo
	 * @author wangxf
	 * @date 2012-9-21
	 */
	private static void addDropBagResp(AreaChangeResp resp,
			List<Long> addDropBagIds, RoleInfo roleInfo) {
		List<TransDropBag> dropBagList = new ArrayList<TransDropBag>();
		TransDropBag transInfo = null;
		for(long dropBagId : addDropBagIds){
			DropBag dropBag = DropBagMap.getDropBag(dropBagId);
			if(dropBag != null){
				transInfo = convertToTransformObj(dropBag);
				dropBagList.add(transInfo);
			}
		}
		resp.setDropBagCount(dropBagList.size());
		resp.setDropBagList(dropBagList);
	}

	/**
	 * 获取发送给客户端玩家和npc变化的消息
	 * @param resp
	 * @return
	 * @author wangxf
	 * @date 2012-8-17
	 */
	private static String getAreaChangeMsg(AreaChangeResp resp) {
		StringBuffer s = new StringBuffer();
		s.append("消失的玩家： " + resp.getDelRoleId() + "， 消失的npc:" + resp.getDelNpcId() + "新增的玩家：");
		if (resp.getOtherRoleList() != null && resp.getOtherRoleList().size() > 0) {
			for (OtherRole roleInfo : resp.getOtherRoleList()) {
				s.append(roleInfo.getRoleId() + ",");
			}
		}
		s.append("新增的npc：");
		if (resp.getNpcInfoList() != null && resp.getNpcInfoList().size() > 0) {
			for (TransformNPCInfo npcInfo : resp.getNpcInfoList()) {
				s.append(npcInfo.getId() + ",");
			}
		}
		return s.toString();
	}

	/**
	 * 发送添加其它角色信息
	 */
	public static void sendAddRoleInfoMsg(RoleInfo role, RoleInfo roleInfo, Path3D path)
	{
		Message message = new Message();
		GameMessageHead header = new GameMessageHead();
		header.setMsgType(0xB008);
		header.setUserID0((int)role.getId());
		message.setHeader(header);
		
		AreaChangeResp resp = new AreaChangeResp();
		List<OtherRole> otherRoleList = new ArrayList<OtherRole>();
		
		OtherRole otherRole = new OtherRole();
		otherRole.setRoleId(roleInfo.getId());
		otherRole.setRoleName(roleInfo.getRoleName());
		otherRole.setSex(roleInfo.getSex());
		otherRole.setLevel(roleInfo.getLevel());
		otherRole.setSceneId(roleInfo.getSceneId());
		otherRole.setCurrX(roleInfo.getCurrX());
		otherRole.setCurrY(roleInfo.getCurrY());
		otherRole.setCurrZ(roleInfo.getCurrZ());
		otherRole.setMapId(roleInfo.getMapId());
		otherRole.setCurrHP(roleInfo.getCurrHP());
		otherRole.setCurrMP(roleInfo.getCurrMP());
		otherRole.setMaxHP(roleInfo.getMaxHP());
		otherRole.setMaxMP(roleInfo.getMaxMP());
		otherRole.setCurrAttack(DataFormat.getDouble2Scale(roleInfo.getCurrAttack()));
		otherRole.setCurrDefend(DataFormat.getDouble2Scale(roleInfo.getCurrDefend()));
		otherRole.setAction(roleInfo.getAction());
		otherRole.setCurrMaxMoveTime(roleInfo.getCurrMoveMaxTime());
		otherRole.setFaceMode(roleInfo.getFaceMode());
		otherRole.setStatus(roleInfo.getStatus());
		
		if(path != null){
			String str = parseMovePath(path);
			otherRole.setMoveList(str);
		}
		
		
		List<EquipRelRe> equipRelReList = new ArrayList<EquipRelRe>();
		Map<Integer, EquipRel> equipRelMap = roleInfo.getEquipRelMap();
		if(equipRelMap != null && !equipRelMap.isEmpty()){
			Map<Long, EquipInfo> equipMap = roleInfo.getEquipMap();
			if(equipMap != null){
				Set<Integer> keys = equipRelMap.keySet();
				for(Integer key : keys){
					EquipRel equipRel = equipRelMap.get(key);
					EquipInfo equipInfo = equipMap.get(equipRel.getEquipId());
					if(equipInfo == null)
						continue;
					EquipRelRe re = new EquipRelRe();
					re.setId(equipRel.getId());
					re.setEquipId(equipRel.getEquipId());
					re.setPartNO(equipRel.getPartNO());
					
					re.setEquipNo(equipInfo.getEquipNO());
					equipRelReList.add(re);
				}
			}
		}
		otherRole.setEquipRelCount(equipRelReList.size());
		otherRole.setEquipRelList(equipRelReList);
		
		BuffService.checkPhalanxBuff(roleInfo);
		List<BuffInfo> buffList = roleInfo.getBuffList();
		List<BuffResp> buffRespList = new ArrayList<BuffResp>();
		if(buffList != null && !buffList.isEmpty()){
			for(BuffInfo buff : buffList){
				BuffResp buffResp = new BuffResp();
				buffResp.setBuffId(buff.getId());
				buffResp.setBuffNo(buff.getBuffNo());
				buffResp.setBuffAim(roleInfo.getId());
				buffResp.setBuffAimType(1);
				buffResp.setBuffType(buff.getType());
				buffResp.setBuffTime(buff.getBuffTime()-(int)((System.currentTimeMillis()-buff.getCreateTime())/1000));
				buffRespList.add(buffResp);
			}
		}
		otherRole.setBuffCount(buffRespList.size());
		otherRole.setBuffList(buffRespList);
		
		
		otherRoleList.add(otherRole);
		
		resp.setOtherRolecount(otherRoleList.size());
		resp.setOtherRoleList(otherRoleList);
		
		message.setBody(resp);
		
		IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
				"-" + role.getGateServerId());
		if(session != null && session.isConnected())
		{ 
			session.write(message);
		}
		
	}
	
	/**
	 * 发送删除其它角色信息
	 */
	public static void sendRemoveRoleInfoMsg(RoleInfo role, long roleId)
	{
		Message message = new Message();
		GameMessageHead header = new GameMessageHead();
		header.setMsgType(0xB008);
		header.setUserID0((int)role.getId());
		message.setHeader(header);
		
		AreaChangeResp resp = new AreaChangeResp();
		resp.setDelRoleId(String.valueOf(roleId));
		message.setBody(resp);
		
		IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
				"-" + role.getGateServerId());
		if(session != null && session.isConnected())
		{ 
			session.write(message);
		}
		
	}
	
	/**
	 * 发送攻击信息给客户端
	 */
	public static void sendAttackMsg(AIInfo attack, AIInfo defend, int lossHP){
		List<Long> roleIds = new ArrayList<Long>();
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(attack.getMapId());
		if (gameMap == null)
			return;
		
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		roleIds.addAll(gameMap.getAreaAllRole((int)attack.getCurrX(), (int)attack.getCurrY(), (int)attack.getCurrZ(), 
				refreshRadiiX, refreshRadiiY, refreshRadiiZ));
		
		List<Long> defendArea = gameMap.getAreaAllRole((int)defend.getCurrX(), (int)defend.getCurrY(), (int)defend.getCurrZ(), 
				refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		for(long defendAreaRole : defendArea){
			if(!roleIds.contains(defendAreaRole))
				roleIds.add(defendAreaRole);
		}
		
		for(long roleId : roleIds){
			RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
			if(roleInfo == null)
				continue;
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB021);
			header.setUserID0((int)roleId);
			message.setHeader(header);
			
			AttackResp resp = new AttackResp();
			resp.setAimLossHP(lossHP);
			resp.setAttackAim(defend.getId());
			if(defend instanceof RoleInfo)
				resp.setAttackAimType(1);
			else if(defend instanceof NPCInfo)
				resp.setAttackAimType(0);
			resp.setAttackAimCurrHP(defend.getCurrHP());
			
			resp.setAttack(attack.getId());
			if(attack instanceof RoleInfo)
				resp.setAttackType(1);
			else if(attack instanceof NPCInfo)
				resp.setAttackType(0);
			
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
					"-" + roleInfo.getGateServerId());
			if(session != null && session.isConnected())
			{ 
				session.write(message);
			}
		}
	}
	
	
	/**
	 * 发送技能攻击信息给客户端
	 */
	public static void sendSkillAttackMsg(AIInfo attack, AIInfo defend, int lossHP, int skillType, int skillLevel){
		List<Long> roleIds = new ArrayList<Long>();
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(attack.getMapId());
		if (gameMap == null)
			return;
		
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		roleIds.addAll(gameMap.getAreaAllRole((int)attack.getCurrX(), (int)attack.getCurrY(), (int)attack.getCurrZ(), 
				refreshRadiiX, refreshRadiiY, refreshRadiiZ));
		
		List<Long> defendArea = gameMap.getAreaAllRole((int)defend.getCurrX(), (int)defend.getCurrY(), (int)defend.getCurrZ(), 
				refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		for(long defendAreaRole : defendArea){
			if(!roleIds.contains(defendAreaRole))
				roleIds.add(defendAreaRole);
		}
		
		for(long roleId : roleIds){
			RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
			if(roleInfo == null)
				continue;
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB023);
			header.setUserID0((int)roleId);
			message.setHeader(header);
			
			SkillAttackResp resp = new SkillAttackResp();
			resp.setResult(1);
			resp.setAttack(attack.getId());
			if(attack instanceof RoleInfo)
				resp.setAttackType(1);
			else if(attack instanceof NPCInfo)
				resp.setAttackType(0);
			resp.setSkillType(skillType);
			resp.setSkillLevel(skillLevel);
			resp.setAttackCurrX(attack.getCurrX());
			resp.setAttackCurrY(attack.getCurrY());
			resp.setAttackCurrZ(attack.getCurrZ());
			
			resp.setAttackAimCurrX(defend.getCurrX());
			resp.setAttackAimCurrY(defend.getCurrY());
			resp.setAttackAimCurrZ(defend.getCurrZ());
			
			List<DefendInfoResp> defendRespList = new ArrayList<DefendInfoResp>();
			DefendInfoResp defendInfoResp = new DefendInfoResp();
			defendInfoResp.setAimLossHP(lossHP);
			defendInfoResp.setAttackAim(defend.getId());
			if(defend instanceof RoleInfo)
				defendInfoResp.setAttackAimType(1);
			else if(defend instanceof NPCInfo)
				defendInfoResp.setAttackAimType(0);
			defendInfoResp.setAttackAimCurrHP(defend.getCurrHP());
			defendRespList.add(defendInfoResp);
			
			resp.setDefendCount(defendRespList.size());
			resp.setDefendList(defendRespList);
			
			
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
					"-" + roleInfo.getGateServerId());
			if(session != null && session.isConnected())
			{ 
				session.write(message);
			}
		}
	}
	
	
	/**
	 * 发送技能攻击信息给客户端
	 */
	public static void sendSkillAttackMsg(AIInfo attack, List<DefendInfo> defendList, float aimX, float aimY, float aimZ,
			int skillType, int skillLevel, int skillBeatRange){
		List<Long> roleIds = new ArrayList<Long>();
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(attack.getMapId());
		if (gameMap == null)
			return;
		
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		roleIds.addAll(gameMap.getAreaAllRole((int)attack.getCurrX(), (int)attack.getCurrY(), (int)attack.getCurrZ(), 
				refreshRadiiX, refreshRadiiY, refreshRadiiZ));
		
		List<Long> defendArea = gameMap.getAreaAllRole((int)aimX, (int)aimY, (int)aimZ, 
				refreshRadiiX+skillBeatRange, refreshRadiiY+skillBeatRange, refreshRadiiZ+skillBeatRange);
		for(long defendAreaRole : defendArea){
			if(!roleIds.contains(defendAreaRole))
				roleIds.add(defendAreaRole);
		}
		
		for(long roleId : roleIds){
			RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
			if(roleInfo == null)
				continue;
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB023);
			header.setUserID0((int)roleId);
			message.setHeader(header);
			
			SkillAttackResp resp = new SkillAttackResp();
			resp.setResult(1);
			resp.setAttack(attack.getId());
			if(attack instanceof RoleInfo)
				resp.setAttackType(1);
			else if(attack instanceof NPCInfo)
				resp.setAttackType(0);
			resp.setSkillType(skillType);
			resp.setSkillLevel(skillLevel);
			resp.setAttackCurrX(attack.getCurrX());
			resp.setAttackCurrY(attack.getCurrY());
			resp.setAttackCurrZ(attack.getCurrZ());
			
			resp.setAttackAimCurrX(aimX);
			resp.setAttackAimCurrY(aimY);
			resp.setAttackAimCurrZ(aimZ);
			
			List<DefendInfoResp> defendRespList = new ArrayList<DefendInfoResp>();
			for(DefendInfo defendInfo : defendList){
				DefendInfoResp defendInfoResp = new DefendInfoResp();
				defendInfoResp.setAimLossHP(defendInfo.getAimLossHP());
				defendInfoResp.setAttackAim(defendInfo.getDefendId());
				defendInfoResp.setAttackAimType(defendInfo.getDefendType());
				defendInfoResp.setAttackAimCurrHP(defendInfo.getCurrHP());
				defendRespList.add(defendInfoResp);
			}
			
			resp.setDefendCount(defendRespList.size());
			resp.setDefendList(defendRespList);
			
			
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
					"-" + roleInfo.getGateServerId());
			if(session != null && session.isConnected())
			{ 
				session.write(message);
			}
		}
	}
	
	/**
	 * 发送技能攻击错误信息给客户端
	 */
	public static void sendSkillAttackErrorMsg(RoleInfo attack, int result, int skillType, int skillLevel){
		
			if(attack == null)
				return;
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB023);
			header.setUserID0((int)attack.getId());
			message.setHeader(header);
			
			SkillAttackResp resp = new SkillAttackResp();
			resp.setResult(result);
			resp.setSkillType(skillType);
			resp.setSkillLevel(skillLevel);
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
					"-" + attack.getGateServerId());
			if(session != null && session.isConnected())
			{ 
				session.write(message);
			}
	}
	
	/**
	 * 路线总长度
	 * @param steps
	 * @return
	 */
	public static double getLine(ArrayList<Step> steps){
		double result = 0;
		for(int i = 0; i < steps.size()-1; i ++){
			Step step1 = steps.get(i);
			Step step2 = steps.get(i+1);
			//两点在同一X轴
			if(step1.getX() == step2.getX()){
				result += Math.abs(step1.getY() - step2.getY());
			}
			//两点在同一Y轴
			else if(step1.getY() == step2.getY()){
				result += Math.abs(step1.getX() - step2.getX());
			}
			else{
				double a = Math.abs(step2.getX()-step1.getX());
				double b = Math.abs(step2.getY()-step1.getY());
				result += Math.sqrt(a*a+b*b);
			}
		}
		return result;
	}
	
	/**
	 * 两点之间长度
	 * @param step1
	 * @param step2
	 * @return
	 */
	public static float getLine(Step3D step1, Step3D step2){
		double result = Math.sqrt((step2.getX() - step1.getX()) * (step2.getX() - step1.getX()) + 
				(step2.getY() - step1.getY()) * (step2.getY() - step1.getY()) + 
				(step2.getZ() - step1.getZ()) * (step2.getZ() - step1.getZ()));
		
		return (float)result;
	}
	
	/**
	 * 发送玩家附近新出现一个NPC
	 * @param otherRoleInfo
	 * @param aiInfo
	 * @param path
	 * @author wangxf
	 * @date 2012-8-7
	 */
	public static void sendAddNPCInfoMsg(RoleInfo roleInfo,
			NPCInfo npcInfo, Path3D path) {
		Message message = new Message();
		// 消息头
		GameMessageHead header = new GameMessageHead();
		header.setMsgType(0xB008);
		header.setUserID0((int)roleInfo.getId());
		message.setHeader(header);
		
		// 消息体
		AreaChangeResp resp = new AreaChangeResp();
		List<TransformNPCInfo> npcInfoList = new ArrayList<TransformNPCInfo>();
		TransformNPCInfo transformNPCInfo = new TransformNPCInfo();
		
		transformNPCInfo.setId(npcInfo.getId());
		transformNPCInfo.setNo(npcInfo.getNo());
		transformNPCInfo.setName(npcInfo.getName());
		transformNPCInfo.setNpcType(npcInfo.getNpcType());
		transformNPCInfo.setLevel(npcInfo.getLevel());
		transformNPCInfo.setCurrX(npcInfo.getCurrX());
		transformNPCInfo.setCurrY(npcInfo.getCurrY());
		transformNPCInfo.setCurrZ(npcInfo.getCurrZ());
		transformNPCInfo.setMapId(npcInfo.getMapId());
		transformNPCInfo.setMaxHP(npcInfo.getMaxHP());
		transformNPCInfo.setMaxMP(npcInfo.getMaxMP());
		transformNPCInfo.setCurrHP(npcInfo.getCurrHP());
		transformNPCInfo.setCurrMP(npcInfo.getCurrMP());
		transformNPCInfo.setCurrAttack(DataFormat.getDouble2Scale(npcInfo.getCurrAttack()));
		transformNPCInfo.setCurrDefend(DataFormat.getDouble2Scale(npcInfo.getCurrDefend()));
		transformNPCInfo.setAction(npcInfo.getAction());
		transformNPCInfo.setCurrMaxMoveTime(npcInfo.getCurrMoveMaxTime());
		transformNPCInfo.setFaceMode(npcInfo.getFaceMode());
		transformNPCInfo.setStatus(npcInfo.getStatus());
		if(path != null){
			String str = parseMovePath(path);
			transformNPCInfo.setMoveList(str);
		}

		transformNPCInfo.setFunction(npcInfo.getFunction());
		BuffService.checkPhalanxBuff(npcInfo);
		List<BuffInfo> buffList = npcInfo.getBuffList();
		List<BuffResp> buffRespList = new ArrayList<BuffResp>();
		if(buffList != null && !buffList.isEmpty()){
			for(BuffInfo buff : buffList){
				BuffResp buffResp = new BuffResp();
				buffResp.setBuffId(buff.getId());
				buffResp.setBuffNo(buff.getBuffNo());
				buffResp.setBuffAim(npcInfo.getId());
				buffResp.setBuffAimType(0);
				buffResp.setBuffType(buff.getType());
				buffResp.setBuffTime(buff.getBuffTime()-(int)((System.currentTimeMillis()-buff.getCreateTime())/1000));
				buffRespList.add(buffResp);
			}
		}
		transformNPCInfo.setBuffCount(buffRespList.size());
		transformNPCInfo.setBuffList(buffRespList);
		
		npcInfoList.add(transformNPCInfo);	
		
		resp.setNpcCount(npcInfoList.size());
		resp.setNpcInfoList(npcInfoList);
		message.setBody(resp);
		
		IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
				"-" + roleInfo.getGateServerId());
		if(session != null && session.isConnected())
		{ 
			session.write(message);
		}
	}

	/**
	 * 给玩家发送删除一个消失在视野范围的npc
	 * @param otherRoleInfo
	 * @param id
	 * @author wangxf
	 * @date 2012-8-7
	 */
	public static void sendDelNPCInfoMsg(RoleInfo roleInfo, long id) {
		// 消息头
		Message message = new Message();
		GameMessageHead header = new GameMessageHead();
		header.setMsgType(0xB008);
		header.setUserID0((int)roleInfo.getId());
		message.setHeader(header);
				
		// 消息体
		AreaChangeResp resp = new AreaChangeResp();
		resp.setDelNpcId(String.valueOf(id));
		message.setBody(resp);
				
		IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
				"-" + roleInfo.getGateServerId());
		if(session != null && session.isConnected())
		{ 
			session.write(message);
		}
		
	}
	
	/**
	 * 创建返回给客户端的消息类
	 * @param roleId
	 * @param body
	 * @param msgType
	 * @return
	 * @author wangxf
	 * 2012-8-29
	 */
	public static Message createMessage(long roleId, MessageBody body, int msgType) {
		Message message = new Message();
		GameMessageHead head = new GameMessageHead();
		head.setMsgType(msgType);
		head.setUserID0((int) roleId);
		message.setHeader(head);
		message.setBody(body);
		return message;
	}

	/**
	 * 给掉落包周围玩家发送掉落包消息
	 * @param defend
	 * @param dropBag
	 * @author wangxf
	 * @date 2012-9-7
	 */
	public static void sendAddDropBagMsg(DropBag dropBag) {
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(dropBag.getMapId());
		if (gameMap == null)
			return;
		
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		// 查询当前对象所在点范围内的玩家
		List<Long> roleIds = gameMap.getAreaAllRole((int)dropBag.getX(), (int)dropBag.getY(),
				(int)dropBag.getZ(), refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		RoleInfo roleInfo = null;
		for(long roleId : roleIds){
			roleInfo = RoleInfoMap.getRoleInfo(roleId);
			if (roleInfo == null) {
				continue;
			}
			// 添加玩家附近的掉落包id
			roleInfo.getDropBagIds().add(dropBag.getId());
			
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB008);
			header.setUserID0((int)roleId);
			message.setHeader(header);
			
			AreaChangeResp resp = new AreaChangeResp();
			List<TransDropBag> dropBagList = new ArrayList<TransDropBag>();
			TransDropBag tmpObj = convertToTransformObj(dropBag);
			dropBagList.add(tmpObj);
			resp.setDropBagCount(dropBagList.size());
			resp.setDropBagList(dropBagList);
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + "-" + roleInfo.getGateServerId());
			if(session != null && session.isConnected())
			{ 
				session.write(message);
				
				if(logger.isInfoEnabled())
				{
					logger.info(Resource.getMessage("game", "GAME_DROP_INFO_3") + ":roleId=" 
							+ roleId + ", " + dropBag.getClass().getSimpleName() + ", x=" + dropBag.getX() + 
							", y=" + dropBag.getY() + ", z=" + dropBag.getZ());
				}
	 
			}
		}
	}

	/**
	 * 转成发送给客户端的掉落包对象
	 * @param dropBag
	 * @return
	 * @author wangxf
	 * @date 2012-9-7
	 */
	private static TransDropBag convertToTransformObj(DropBag dropBag) {
		TransDropBag tmpObj = new TransDropBag();
		tmpObj.setId(dropBag.getId());
		tmpObj.setModelId(dropBag.getModelId());
		tmpObj.setX(dropBag.getX());
		tmpObj.setY(dropBag.getY());
		tmpObj.setZ(dropBag.getZ());
		tmpObj.setDropTime(dropBag.getDropTime());
		tmpObj.setProtectTime(dropBag.getProtectTimePeriod() - (int)(System.currentTimeMillis() - dropBag.getDropTime()) / 1000);
		tmpObj.setClearTime(dropBag.getClearTimePeriod() - (int)(System.currentTimeMillis() - dropBag.getDropTime()) / 1000);
		tmpObj.setOwnerId(dropBag.getOwnerId());
		return tmpObj;
	}

	/**
	 * 给掉落包附近的玩家发送掉落包消失的协议
	 * @param dropBag
	 * @author wangxf
	 * @date 2012-9-7
	 */
	public static void sendClearDropBagMsg(DropBag dropBag) {
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(dropBag.getMapId());
		if (gameMap == null)
			return;
		
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		// 查询当前对象所在点范围内的玩家
		List<Long> roleIds = gameMap.getAreaAllRole((int)dropBag.getX(), (int)dropBag.getY(),
				(int)dropBag.getZ(), refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		RoleInfo roleInfo = null;
		for(long roleId : roleIds){
			roleInfo = RoleInfoMap.getRoleInfo(roleId);
			if (roleInfo == null) {
				continue;
			}
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xB008);
			header.setUserID0((int)roleId);
			message.setHeader(header);
			
			AreaChangeResp resp = new AreaChangeResp();
			resp.setDelDropBagIds(String.valueOf(dropBag.getId()));
			message.setBody(resp);
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + "-" + roleInfo.getGateServerId());
			if(session != null && session.isConnected())
			{ 
				session.write(message);
				
				if(logger.isInfoEnabled())
				{
					logger.info(Resource.getMessage("game", "GAME_DROP_INFO_3") + ":roleId=" 
							+ roleId + ", " + dropBag.getClass().getSimpleName() + ", x=" + dropBag.getX() + 
							", y=" + dropBag.getY() + ", z=" + dropBag.getZ());
				}
	 
			}
		}
	}

	/**
	 * 给复活玩家周围的玩家发送该玩家复活的消息
	 * @param roleInfo
	 * @author wangxf
	 * @param resp 
	 * @date 2012-11-23
	 */
	public static void sendRoleBirthInfo(RoleInfo roleInfo, RebirthResp resp) {
		// 附近的玩家
		List<Long> roleIds = roleInfo.getOtherRoleIds();
		if (roleIds != null && roleIds.size() > 0) {
			RoleInfo otherRoleInfo = null;
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			for(long otherRoleId : roleIds){
				otherRoleInfo = RoleInfoMap.getRoleInfo(otherRoleId);
				if (otherRoleInfo == null) {
					continue;
				}
				header.setMsgType(0xB030);
				header.setUserID0((int)otherRoleId);
				message.setHeader(header);
				message.setBody(resp);
				
				IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + "-" + 
						otherRoleInfo.getGateServerId());
				if(session != null && session.isConnected())
				{ 
					session.write(message);
				}
			}
		}
	}
}
