package com.snail.webgame.engine.component.scene.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.client.Client;
import org.epilot.ccf.core.protocol.Message;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.common.info.BuffInfo;
import com.snail.webgame.engine.common.info.SkillInfo;
import com.snail.webgame.engine.common.pathfinding.astar.GameMap;
import com.snail.webgame.engine.common.pathfinding.astar.Path;
import com.snail.webgame.engine.common.pathfinding.astar.Step;
import com.snail.webgame.engine.component.scene.cache.FightDataCache;
import com.snail.webgame.engine.component.scene.cache.RoleInFightCache;
import com.snail.webgame.engine.component.scene.cache.ServerMap;
import com.snail.webgame.engine.component.scene.common.ArmyInfo;
import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.GameMessageHead;
import com.snail.webgame.engine.component.scene.common.InSceneRoleInfo;
import com.snail.webgame.engine.component.scene.config.FightConfig;
import com.snail.webgame.engine.component.scene.protocal.fight.addphalanx.AddPhalanxResp;
import com.snail.webgame.engine.component.scene.protocal.fight.attack.PhalanxAttackReq;
import com.snail.webgame.engine.component.scene.protocal.fight.buff.BuffReq;
import com.snail.webgame.engine.component.scene.protocal.fight.control.ControlResp;
import com.snail.webgame.engine.component.scene.protocal.fight.defend.PhalanxDefendReq;
import com.snail.webgame.engine.component.scene.protocal.fight.delphalanx.DelPhalanxResp;
import com.snail.webgame.engine.component.scene.protocal.fight.end.EndFightReq;
import com.snail.webgame.engine.component.scene.protocal.fight.in.RoleInFightArmy;
import com.snail.webgame.engine.component.scene.protocal.fight.in.RoleInFightRe;
import com.snail.webgame.engine.component.scene.protocal.fight.move.PhalanxMoveReq;
import com.snail.webgame.engine.component.scene.protocal.fight.propeffect.PropEffectReq;
import com.snail.webgame.engine.component.scene.protocal.fight.stop.PhalanxStopReq;
import com.snail.webgame.engine.component.scene.protocal.fight.tire.RefreshMoraleReq;
import com.snail.webgame.engine.component.scene.protocal.fight.tire.RefreshRe;

public class FightCmdSend {

	
	
	public static void sendBeSkillCmd(long fightId,int roleId,ControlResp resp)
	{
		if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
		{
			InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
			int gateServerId = roleInfo.getGateServerId();
			
			IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
			
			if(session!=null&& session.isConnected())
			{
				Message message  = new Message();
				
				GameMessageHead head = new GameMessageHead();
				
				head.setMsgType(0xB060);
				head.setUserID0(roleId);
				
				message.setHeader(head);
				message.setBody(resp);
				session.write(message);
			} 
			
		}
	}
	
	private static String convertGridPathToWorldPath(ArrayList<Step> steps, int gridNum, GameMap map){
		String str = "";
		for(Step step : steps){
			if(gridNum==1)
			{
				Step point = map.gridToWorld(step.getX(), step.getY());
				str = str + point.getX()+","+point.getY()+",";
			}
			else if(gridNum==4)
			{
				float x = step.getX()*map.getGridTitleX()+map.getGridTitleX();
				float y = step.getY()*map.getGridTitleY()+map.getGridTitleY();
				str = str + x+","+y+",";
			 
			}
			else if(gridNum==9)
			{
				Step point = map.gridToWorld(step.getX(), step.getY());
				str = str + point.getX()+","+point.getY()+",";
				
			}
		}
		return str;
	}
	
	/**
	 * 发送方阵移动给客户端
	 */
	public static void sendRoleMoveCmd(long fightId,List<Integer> roleList,int moveX,int moveY,
			ArmyPhalanx armyPhalanx,String slogan,int moveType)
	{		
		
 
		IoSession session  = null;
	 
		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
			
			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId)&&FightDataCache.isExistRoleFight(fightId))
			{
				InSceneRoleInfo roleInfo =  RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB056);
					head.setUserID0(roleId);
					
					PhalanxMoveReq req = new PhalanxMoveReq();
					
					req.setFightId(fightId);
					req.setPhalanxId(armyPhalanx.getPhalanxId());
					 
					req.setMoveTime(armyPhalanx.getCurrMoveMaxTime());
					 
//					if(armyPhalanx.getCurrMoveTime()<0)
//					{
//						req.setMoveTime(armyPhalanx.getMoveMaxTime()-armyPhalanx.getCurrMoveTime());
//					}
//					else
//					{
//						req.setMoveTime(armyPhalanx.getMoveMaxTime());
//					}
					
					req.setMoveType(moveType);
					
					req.setItemNum(armyPhalanx.getItemNum());
					req.setCurrX(armyPhalanx.getCurrX());
					req.setCurrY(armyPhalanx.getCurrY());
					req.setMoveX(moveX);
					req.setMoveY(moveY);
		
					req.setAimX(armyPhalanx.getAimX());
					req.setAimY(armyPhalanx.getAimY());
					
					
					Path path = armyPhalanx.getPath();
					ArrayList<Step> list = path.getSmoothPaths();
//					RoleFight roleFight = FightDataCache.getRoleFight(fightId);
//					String str = convertGridPathToWorldPath(list, armyPhalanx.getGridNum(), roleFight.getMap());
					String str = "";
					for(int k=0;k<list.size();k++)
					{
						Step step = list.get(k);
						str = str + step.getX()+","+step.getY()+",";
					}
					
					req.setMoveList(str);
					
					message.setHeader(head);
					message.setBody(req);
					
					session.write(message);
					System.out.println("153====sendRoleMoveCmd " + armyPhalanx.getPhalanxId() + ", " + str);
					 
				}
				 
			}
			 
			
			
		}
	
	}
	
	/**
	 * 发送方阵进攻给客户端
	 */
	public static void sendRoleAttackCmd(long fightId,List<Integer> roleList,
			ArmyPhalanx attackPhalanx,ArmyPhalanx defendPhalanx,String attackSlogan,
			int face,int time,int attackType, int skillLevel)
	{
		
		IoSession session  = null;
		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
			{
				InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB058);
					head.setUserID0(roleId);
					
					PhalanxAttackReq req = new PhalanxAttackReq();
					
					req.setFightId(fightId);
					
					req.setPhalanxId(attackPhalanx.getPhalanxId());
					req.setAttackCurrX(attackPhalanx.getCurrX());
					req.setAttackCurrY(attackPhalanx.getCurrY());
					req.setCurrAttackNum(attackPhalanx.getItemNum());
					req.setCurrAttackType(attackType);
					req.setCurrSkillLevel(skillLevel);
					HashMap<Integer, SkillInfo> skillMap = attackPhalanx.getSkillMap();
					SkillInfo skillInfo = skillMap.get(attackType);
					if(skillInfo != null)
					{
						req.setLastReleaseTime(skillInfo.getLastReleaseTime());
					}
					
					req.setCurrAttackHP((long) Math.ceil(attackPhalanx.getCurrHP()));
					req.setCurrAttackMP(attackPhalanx.getCurrMP());
	
					if(defendPhalanx != null)
						req.setAimPhalanxId(defendPhalanx.getPhalanxId());
				
				
					req.setAttackTime(time);
					req.setAimX(attackPhalanx.getAttackAimX());
					req.setAimY(attackPhalanx.getAttackAimY());
					req.setFace(face);
					
					message.setHeader(head);
					message.setBody(req);
					session.write(message);
					System.out.println("231====sendRoleAttackCmd " + attackPhalanx.getPhalanxId() + ", aim=" + attackPhalanx.getAimPhalanxId());
			
			 
				}
				
			}
		}
		
		
	 
	}
	
	/**
	 * 发送方阵受击给客户端
	 */
	public static void sendDamageCmd(long fightId,List<Integer> roleList,String attackPhalanxId,int aimLoss,long aimLossHP,
			ArmyPhalanx armyPhalanx ,String defendSlogan,int face, byte flag)
	{
		
		IoSession session  = null;
		
		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
	
			
			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
			{
				InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB065);
					head.setUserID0(roleId);
					
					PhalanxDefendReq req = new PhalanxDefendReq();
					
					req.setFightId(fightId);
					
					req.setAttackPhalanxId(attackPhalanxId);
					req.setAimPhalanxId(armyPhalanx.getPhalanxId());
					req.setDefendSlogan(defendSlogan);
					 
					req.setAimCurrHP((long) Math.ceil(armyPhalanx.getCurrHP()));
					req.setAimCurrNum(armyPhalanx.getItemNum());
					req.setAimLoss(aimLoss);
					req.setAimLossHP(aimLossHP);
					req.setFace(face);
					req.setFlag(flag);
					
					message.setHeader(head);
					message.setBody(req);
					session.write(message);
					System.out.println("290====sendRoleDamageCmd " + req.getAimPhalanxId() + ", " + req.getAimLossHP());
					
			 
				}
			}
		}
		
		
	}
	
	
	/**
	 * 发送方阵停止给客户端
	 */
	public static void sendRoleStopCmd(long fightId,List<Integer> roleList,ArmyPhalanx armyPhalanx)
	{
		
		IoSession session  = null;
		
		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
	
			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
			{
				InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB067);
					head.setUserID0(roleId);
					PhalanxStopReq req = new PhalanxStopReq();
					req.setFightId(fightId);
					req.setPhalanxId(armyPhalanx.getPhalanxId());
					req.setCurrX(armyPhalanx.getCurrX());
					req.setCurrY(armyPhalanx.getCurrY());
					message.setHeader(head);
					message.setBody(req);
					session.write(message);
					System.out.println("335====sendRoleStopCmd " + armyPhalanx.getPhalanxId());
				}
			}
			
		}
		
		
		
	}
 
	/**
	 * 发送士气刷新
	 * @param roleId
	 * @param fightId
	 * @param phalanxId
	 * @param morale
	 */
	public static void sendRefreshMorale(long fightId,List<Integer> roleList,List<RefreshRe> refreshReList,int status)
	{
		
		IoSession session  = null;
		

		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
	
			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
			{
				InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB066);
					head.setUserID0(roleId);
					
					RefreshMoraleReq req = new RefreshMoraleReq();
					
					req.setFightId(fightId);
					req.setStatus(status);
					req.setCount(refreshReList.size());
					req.setList(refreshReList);
					
					message.setHeader(head);
					message.setBody(req);
					session.write(message);
					
					
			 
				}
			}
			
		}
		
		
	}
	
 /**
  * 发送部队buff
  * @param fightId
  * @param roleList
  * @param armyPhalanx
  * @param buffFlag 0-添加buff 1-删除buff
  */
	public static void sendPhalanxBuff(long fightId,List<Integer> roleList, ArmyPhalanx armyPhalanx,byte buffFlag)
	{
		

		long time = System.currentTimeMillis();
		List<BuffInfo> list = armyPhalanx.getBuffList();
		String str = "" ; 
		for(int k=0;k<list.size();k++)
		{
			BuffInfo buff = list.get(k);
			if(buff!=null)
			{
				str = str + buff.getBuffNo() +","
				+ buff.getValue()+","+(buff.getBuffTime() - (time-buff.getCreateTime()))/1000+","+buff.getReserve()+",";
 
			}
		}
		BuffReq req = new BuffReq();
		req.setBuff(str);
		req.setFightId(fightId);
		req.setPhalanxId(armyPhalanx.getPhalanxId());
		req.setBuffFlag(buffFlag);
		
		IoSession session  = null;
		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
	
			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
			{
				InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB069);
					head.setUserID0(roleId);
					
					message.setHeader(head);
					message.setBody(req);
					session.write(message);
				}
			}
			
		}
		
		
	}
	
	/**
	 * 战斗结束指令
	 */
	public static void sendFightExistCmd(long fightId,List<Integer> roleList,int victorySide)
	{
		
		IoSession session  = null;
		

		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
	
			
			if(RoleInFightCache.isExistRoleInFight(fightId,roleId))
			{
				InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB061);
					head.setUserID0(roleId);
					
					EndFightReq req  = new EndFightReq();
					
					req.setFightId(fightId);
					req.setVictorySide(victorySide);
					message.setHeader(head);
					message.setBody(req);
					session.write(message);
				}
			}
			
		}
		
		
	}

	
	/**
	 * 发送道具使用效果
	 * @param roleId
	 * @param fightId
	 * @param phalanxId
	 * @param morale
	 */
	public static void sendPropUseEffect(long fightId,List<Integer> roleList,PropEffectReq propEffectReq)
	{
		
		IoSession session  = null;
		

		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
	
			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
			{
				InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB073);
					head.setUserID0(roleId);
					
 
					message.setHeader(head);
					message.setBody(propEffectReq);
					session.write(message);
					
					
			 
				}
			}
			
		}
	}
	
	
//	public static void sendArmyAdd(long fightId,RoleFight roleFight ,List<ArmyInfo> armyInfos  )
//	{
//		
//		IoSession session  = null;
//		RoleInFightResp resp = new RoleInFightResp();
//		resp.setFightId(fightId);
//		resp.setFightMapType(roleFight.getFightMapType());
//		resp.setFightType(roleFight.getFightType());
//		resp.setEndTime1(roleFight.getEndTime1());
//		resp.setEndTime2(roleFight.getEndTime2());
// 
//		resp.setLandForm(roleFight.getLandForm());
//		resp.setOffX(roleFight.getOffX());
//		resp.setOffY(roleFight.getOffY());
//		FightMgtService service = new FightMgtService();
//		List armyList = new ArrayList();
//		
//	
//		for(int k =0;k<armyInfos.size();k++)
//		{
//			ArmyInfo armyInfo = armyInfos.get(k);
//			if(armyInfo==null)
//			{
//				continue;
//			}
//			armyList.add(service.getRoleInFightArmy(armyInfo, roleFight));
//			
//			
//			
//		}
//		
//		resp.setResult(1);
//		resp.setCount(armyList.size());
//		resp.setList(armyList);
//		
//		
//		List<Integer> roleList = roleFight.getRoleList();
//		
//		for(int i=0;i<roleList.size();i++)
//		{
//			int roleId = roleList.get(i);
//	
//			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
//			{
//				RoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
//				int gateServerId = roleInfo.getGateServerId();
//				
//				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
//				
//				if(session!=null&& session.isConnected())
//				{
//					Message message  = new Message();
//					
//					GameMessageHead head = new GameMessageHead();
//					
//					head.setMsgType(0xB075);
//					head.setUserID0(roleId);
//					
// 
//					message.setHeader(head);
//					message.setBody(resp);
//					session.write(message);
//					
//					
//			 
//				}
//			}
//			
//		}
//	}
	
//	public static boolean sendGetArmyReq(long fightId,String mapId,List<String> attackList,List<String> defendList,
//			int attackNeedNum,int defendNeedNum,int type)
//	{
//		IoSession session  = Client.getInstance().getSession(ServerName.GAME_SERVER_NAME);
//		GetArmyQueueReq req = new GetArmyQueueReq();
//		req.setFightServerId(FightConfig.getInstance().getFightServerId());
//		req.setFightId(fightId);
//		req.setMapId(mapId);
//		String str1 = "";
//		String str2 = "";
//		
//		if(attackList!=null)
//		{
//			for(int i=0;i<attackList.size();i++)
//			{
//				str1 = str1 +attackList.get(i)+",";
//				
//			}
//		}
//		req.setDeadAttackArmyId(str1);
//		
//		
//		if(defendList!=null)
//		{
//			for(int i=0;i<defendList.size();i++)
//			{
//				str2 = str2+defendList.get(i)+",";
//			}
//		}
//		req.setDeadDefendArmyId(str2);
//		req.setAttackNum(attackNeedNum);
//		req.setDefendNum(defendNeedNum);
//		
//		
//		if(session!=null&& session.isConnected())
//		{
//			Message message  = new Message();
//			
//			GameMessageHead head = new GameMessageHead();
//			
//			if(type==4)
//			{
//				head.setMsgType(0xff09);
//			}
//			else if(type==7)
//			{
//				head.setMsgType(0xff11);
//			}
//			else if(type==11)
//			{
//				head.setMsgType(0xff12);
//			}
// 
//			message.setHeader(head);
//			message.setBody(req);
//			session.write(message);
//			
//			
//			return true;
//		}
//		return false;
//		
//	}
	
	
	/**
	 * 发送添加方阵给客户端
	 */
	public static void sendAddPhalanxCmd(long fightId,List<Integer> roleList,ArmyPhalanx armyPhalanx, ArmyInfo armyInfo)
	{
		
		IoSession session  = null;
		
		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
	
			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
			{
				InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB062);
					head.setUserID0(roleId);
					AddPhalanxResp resp = new AddPhalanxResp();
					resp.setFightId(fightId);
					
					List<RoleInFightArmy> armyList = new ArrayList<RoleInFightArmy>();
					
					RoleInFightArmy fightArmy = new RoleInFightArmy();
					
					fightArmy.setRoleId(armyInfo.getRoleId());
					fightArmy.setHeroNo(armyInfo.getHeroNo());
					fightArmy.setRolePic(armyInfo.getRolePic());
					fightArmy.setRoleName(armyInfo.getRoleName());
					fightArmy.setRoleLevel(armyInfo.getRoleLevel());
					fightArmy.setRoleRace(armyInfo.getRoleRace());
					fightArmy.setArmyId(armyInfo.getArmyId());
					fightArmy.setSide(armyInfo.getSide());
					
				
					
					List<RoleInFightRe> reList = new ArrayList<RoleInFightRe>();
					
					RoleInFightRe re = new RoleInFightRe();
					re.setId(armyPhalanx.getPhalanxId());
					re.setAimX(armyPhalanx.getAimX());
					re.setAimY(armyPhalanx.getAimY());
					re.setCurrX(armyPhalanx.getCurrX());
					re.setCurrY(armyPhalanx.getCurrY());
					re.setBuildPointList(armyPhalanx.getBuildPointListToString());
					re.setStatus(armyPhalanx.getPolicy());
					Path path = armyPhalanx.getPath();
					if(path!=null)
					{
						ArrayList<Step> list = path.getSmoothPaths();
						String str = "";
						
						for(int k=0;k<list.size();k++)
						{
							Step step = list.get(k);
							str = str + step.getX()+","+step.getY()+",";
						}
						re.setMoveList(str);
					}
					re.setCurrMoveMaxTime(armyPhalanx.getCurrMoveMaxTime());
					
					re.setItemNum(armyPhalanx.getItemNum());
					re.setMaxItemNum(armyPhalanx.getMaxItemNum());
					re.setItemNo(armyPhalanx.getItemNo());
					re.setGridNum(armyPhalanx.getGridNum());
					re.setMaxHP((long) Math.ceil(armyPhalanx.getMaxHP()));
					re.setCurrHP((long) Math.ceil(armyPhalanx.getCurrHP()));
					re.setMaxMP(armyPhalanx.getMaxMP());
					re.setCurrMP(armyPhalanx.getCurrMP());
					re.setItemSort(armyPhalanx.getItemSort());
					re.setAttackValue((int) armyPhalanx.getMaxAttackValue());
					re.setDefendValue((int) armyPhalanx.getDefendValue());
					re.setMinGrid(armyPhalanx.getMinAttackGrid());
					re.setMaxGrid(armyPhalanx.getMaxAttackGrid());
					re.setIsShow(0);
					re.setMorale(armyPhalanx.getMorale());
					re.setSide(armyPhalanx.getSide());
					
					
					
					re.setCurrAttackMaxTime(armyPhalanx.getAttackMaxTime());
					
					String heroSkill = "";
					List<String>  skillShowList= armyPhalanx.getSkillShowMap();
					if(skillShowList!=null&&skillShowList.size()>0)
					{
						for(String skillNo:skillShowList)
						{
							int skillType = Integer.valueOf(skillNo.split("-")[0]);
							
							SkillInfo skillInfo = armyPhalanx.getSkillMap().get(skillType);
							
//							HeroSkillXMLInfo heroSkillXMLInfo = HeroSkillXMLInfoMap.getHeroSkillXMLInfo1(skillType);
//							if(heroSkillXMLInfo != null)
//							{
//								HeroSkillUpgradeXMLInfo skillUpgradeXMLInfo = HeroSkillXMLInfoMap.getHeroSkillXMLInfo(skillType, skillInfo.getPhalanxSkillLevel());
//								if(skillUpgradeXMLInfo != null)
//								{
//									if(heroSkillXMLInfo.getType() == 5 && skillUpgradeXMLInfo.getBurstType() != 0)
//									{
//										continue;
//									}
									
									heroSkill = heroSkill +skillType+","+skillInfo.getSkillLevel()
									+","+skillInfo.getLastReleaseTime()+",";//+skillInfo.getType()+",";
//								}
//							}
						}
					}
					
					re.setSkillType(heroSkill);
					
//					re.setBuff(roleFight.getFightCalculate().getHeroSkillService().getPhalanxBuff(armyPhalanx));
					reList.add(re);
					
					fightArmy.setCount(reList.size());
					fightArmy.setList(reList);
					
					armyList.add(fightArmy);
				
					resp.setCount(armyList.size());
					resp.setList(armyList);
					
					message.setHeader(head);
					message.setBody(resp);
					session.write(message);
					System.out.println(Thread.currentThread().getName() + "====sendAddPhalanxCmd " + armyPhalanx.getPhalanxId());
				}
			}
			
		}
		
		
		
	}
	
	/**
	 * 发送添加方阵给客户端
	 */
	public static void sendAddPhalanxCmd(long fightId,List<Integer> roleList, List<ArmyPhalanx> armyPhalanxs, ArmyInfo armyInfo)
	{
		
		IoSession session  = null;
		
		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
	
			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
			{
				InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB062);
					head.setUserID0(roleId);
					AddPhalanxResp resp = new AddPhalanxResp();
					resp.setFightId(fightId);
					
					List<RoleInFightArmy> armyList = new ArrayList<RoleInFightArmy>();
					
					RoleInFightArmy fightArmy = new RoleInFightArmy();
					
					fightArmy.setRoleId(armyInfo.getRoleId());
					fightArmy.setHeroNo(armyInfo.getHeroNo());
					fightArmy.setRolePic(armyInfo.getRolePic());
					fightArmy.setRoleName(armyInfo.getRoleName());
					fightArmy.setRoleLevel(armyInfo.getRoleLevel());
					fightArmy.setRoleRace(armyInfo.getRoleRace());
					fightArmy.setArmyId(armyInfo.getArmyId());
					fightArmy.setSide(armyInfo.getSide());
					
					
					List<RoleInFightRe> reList = new ArrayList<RoleInFightRe>();
					
					for(ArmyPhalanx armyPhalanx : armyPhalanxs){
						RoleInFightRe re = new RoleInFightRe();
						re.setId(armyPhalanx.getPhalanxId());
						re.setAimX(armyPhalanx.getAimX());
						re.setAimY(armyPhalanx.getAimY());
						re.setCurrX(armyPhalanx.getCurrX());
						re.setCurrY(armyPhalanx.getCurrY());
						re.setBuildPointList(armyPhalanx.getBuildPointListToString());
						re.setStatus(armyPhalanx.getPolicy());
						Path path = armyPhalanx.getPath();
						if(path!=null)
						{
							ArrayList<Step> list = path.getSmoothPaths();
							String str = "";
							
							for(int k=0;k<list.size();k++)
							{
								Step step = list.get(k);
								str = str + step.getX()+","+step.getY()+",";
							}
							re.setMoveList(str);
						}
						re.setCurrMoveMaxTime(armyPhalanx.getCurrMoveMaxTime());
						
						re.setItemNum(armyPhalanx.getItemNum());
						re.setMaxItemNum(armyPhalanx.getMaxItemNum());
						re.setItemNo(armyPhalanx.getItemNo());
						re.setGridNum(armyPhalanx.getGridNum());
						re.setMaxHP((long) Math.ceil(armyPhalanx.getMaxHP()));
						re.setCurrHP((long) Math.ceil(armyPhalanx.getCurrHP()));
						re.setMaxMP(armyPhalanx.getMaxMP());
						re.setCurrMP(armyPhalanx.getCurrMP());
						re.setItemSort(armyPhalanx.getItemSort());
						re.setAttackValue((int) armyPhalanx.getMaxAttackValue());
						re.setDefendValue((int) armyPhalanx.getDefendValue());
						re.setMinGrid(armyPhalanx.getMinAttackGrid());
						re.setMaxGrid(armyPhalanx.getMaxAttackGrid());
						re.setIsShow(0);
						re.setMorale(armyPhalanx.getMorale());
						re.setSide(armyPhalanx.getSide());
						
						
						
						re.setCurrAttackMaxTime(armyPhalanx.getAttackMaxTime());
						
						String heroSkill = "";
						List<String>  skillShowList= armyPhalanx.getSkillShowMap();
						if(skillShowList!=null&&skillShowList.size()>0)
						{
							for(String skillNo:skillShowList)
							{
								int skillType = Integer.valueOf(skillNo.split("-")[0]);
								
								SkillInfo skillInfo = armyPhalanx.getSkillMap().get(skillType);
								
	//							HeroSkillXMLInfo heroSkillXMLInfo = HeroSkillXMLInfoMap.getHeroSkillXMLInfo1(skillType);
	//							if(heroSkillXMLInfo != null)
	//							{
	//								HeroSkillUpgradeXMLInfo skillUpgradeXMLInfo = HeroSkillXMLInfoMap.getHeroSkillXMLInfo(skillType, skillInfo.getPhalanxSkillLevel());
	//								if(skillUpgradeXMLInfo != null)
	//								{
	//									if(heroSkillXMLInfo.getType() == 5 && skillUpgradeXMLInfo.getBurstType() != 0)
	//									{
	//										continue;
	//									}
										
										heroSkill = heroSkill +skillType+","+skillInfo.getSkillLevel()
										+","+skillInfo.getLastReleaseTime()+",";//+skillInfo.getType()+",";
	//								}
	//							}
							}
						}
						
						re.setSkillType(heroSkill);
						
	//					re.setBuff(roleFight.getFightCalculate().getHeroSkillService().getPhalanxBuff(armyPhalanx));
						reList.add(re);
					}
					
					fightArmy.setCount(reList.size());
					fightArmy.setList(reList);
					
					armyList.add(fightArmy);
				
					resp.setCount(armyList.size());
					resp.setList(armyList);
					
					message.setHeader(head);
					message.setBody(resp);
					session.write(message);
				}
			}
			
		}
		
		
		
	}
	
	/**
	 * 发送移除方阵给客户端
	 */
	public static void sendDelPhalanxCmd(long fightId,List<Integer> roleList, String phalanxId)
	{
		
		IoSession session  = null;
		
		for(int i=0;i<roleList.size();i++)
		{
			int roleId = roleList.get(i);
	
			if(roleId>0&&RoleInFightCache.isExistRoleInFight(fightId,roleId))
			{
				InSceneRoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId,roleId);
				int gateServerId = roleInfo.getGateServerId();
				
				session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+gateServerId);
				
				if(session!=null&& session.isConnected())
				{
					Message message  = new Message();
					
					GameMessageHead head = new GameMessageHead();
					
					head.setMsgType(0xB063);
					head.setUserID0(roleId);
					DelPhalanxResp resp = new DelPhalanxResp();
					resp.setFightId(fightId);
					resp.setPhalanxId(phalanxId);
					
					
					
					message.setHeader(head);
					message.setBody(resp);
					session.write(message);
					System.out.println(Thread.currentThread().getName() + "====sendDelPhalanxCmd " + phalanxId);
				}
			}
			
		}
		
		
		
	}
	
}
