package com.snail.webgame.engine.component.scene.protocal.service;

import java.util.ArrayList;

import org.critterai.math.Vector3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.common.info.AIInfo;
import com.snail.webgame.engine.common.info.NPCInfo;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.common.pathfinding.mesh.GameMap3D;
import com.snail.webgame.engine.common.pathfinding.mesh.Path3D;
import com.snail.webgame.engine.common.pathfinding.mesh.Step3D;
import com.snail.webgame.engine.component.scene.cache.EventChannel;
import com.snail.webgame.engine.component.scene.cache.MapDoorXMlInfoMap;
import com.snail.webgame.engine.component.scene.cache.NPCInfoMap;
import com.snail.webgame.engine.component.scene.core.ISceneMgtService;
import com.snail.webgame.engine.component.scene.core.SceneGameMapFactory;
import com.snail.webgame.engine.component.scene.core.SceneService;
import com.snail.webgame.engine.component.scene.event.AttackEvent;
import com.snail.webgame.engine.component.scene.event.Event;
import com.snail.webgame.engine.component.scene.event.EventActor;
import com.snail.webgame.engine.component.scene.event.MoveEndEvent;
import com.snail.webgame.engine.component.scene.event.MoveEvent;
import com.snail.webgame.engine.component.scene.event.SkillAttackEvent;
import com.snail.webgame.engine.component.scene.info.MapDoorXMLInfo;
import com.snail.webgame.engine.component.scene.protocal.changescene.ChangeSceneReq;
import com.snail.webgame.engine.component.scene.protocal.changescene.ChangeSceneResp;
import com.snail.webgame.engine.component.scene.protocal.fight.attack.AttackReq;
import com.snail.webgame.engine.component.scene.protocal.fight.skill.SkillAttackReq;
import com.snail.webgame.engine.component.scene.protocal.in.InSceneReq;
import com.snail.webgame.engine.component.scene.protocal.in.InSceneResp;
import com.snail.webgame.engine.component.scene.protocal.move.MoveEndValidateReq;
import com.snail.webgame.engine.component.scene.protocal.move.MoveReq;
import com.snail.webgame.engine.component.scene.protocal.out.OutSceneReq;
import com.snail.webgame.engine.component.scene.protocal.rebirth.RebirthResp;

public class MMOSceneMgtService implements ISceneMgtService {
	private static final Logger logger = LoggerFactory.getLogger("logs");
	
	/**
	 * 进入场景
	 * 
	 * @param roleId
	 * @return
	 */
	public InSceneResp inScene(long roleId, InSceneReq req, int gateServerId) {

		return null;
	}

	/**
	 * 退出场景
	 * 
	 * @param roleId
	 */
	public boolean outScene(long roleId, OutSceneReq req) {
		return true;
	}

	/**
	 * 人物移动
	 */
	public void roleMove(long roleId, MoveReq req) {
		RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
		if (roleInfo != null && roleInfo.getStatus() != 2) {

			//roleInfo.setStatus(1);
			String move = req.getMoveList();
			if (move != null && !"".equals(move)) {
				String[] moveList = move.split(",");
				if (moveList != null && moveList.length > 0) {
					Path3D path = new Path3D();
					for (int i = 0; i < moveList.length; i += 3) {
						Step3D step = new Step3D(Float.parseFloat(moveList[i]), Float.parseFloat(moveList[i + 1]),
								Float.parseFloat(moveList[i + 2]));
						path.getSteps().add(step);

					}

					ArrayList<Step3D> steps = path.getSteps();
					if (steps.size() > 1) {
						Step3D aimStep = steps.get(steps.size() - 1);
						if (roleInfo.getAimX() != aimStep.getX() || roleInfo.getAimY() != aimStep.getY()
								|| roleInfo.getAimZ() != aimStep.getZ()) {

							Event event = new MoveEvent(roleInfo.getId(), EventActor.EVENT_ACTOR_PLAYER, path);
							// 添加移动事件前移除该玩家的攻击事件
							EventChannel.removeEvent(new AttackEvent(roleInfo.getId(), EventActor.EVENT_ACTOR_PLAYER, roleInfo.getAttackAim(), roleInfo.getAttackAimType()), roleInfo.getMapId());
							EventChannel.addEvent(roleInfo.getMapId(), event, roleInfo.getBattleStatus());

							SceneService.sendMoveMsgExcludeSelf(roleInfo, path);

						}
					}
				}
			}

		}

	}

	/**
	 * 验证移动停止坐标
	 * 
	 * @param roleId
	 * @param req
	 */
	public void validateEndPoint(int roleId, MoveEndValidateReq req) {
		RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
		if (roleInfo != null && roleInfo.getStatus() != 2) {

			float currX = req.getCurrX();
			float currY = req.getCurrY();
			float currZ = req.getCurrZ();

			Event event = new MoveEndEvent(roleInfo.getId(), EventActor.EVENT_ACTOR_PLAYER, currX, currY, currZ);
			EventChannel.addEvent(roleInfo.getMapId(), event, roleInfo.getBattleStatus());
		}

	}

	/**
	 * 攻击目标
	 */
	public void attack(int roleId, AttackReq req) {
		RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
		if (roleInfo != null && roleInfo.getStatus() != 2) {
			byte aimType = req.getAttackAimType();
			AIInfo attackAimInfo = null;
			if (aimType == 0)// NPC
			{
				NPCInfo npcInfo = NPCInfoMap.getNPCInfo(roleInfo.getMapId(), req.getAttackAim());
				if(npcInfo != null && npcInfo.getNpcType() != 0)
					attackAimInfo = npcInfo;
			}
			else if (aimType == 1)// 玩家
			{
				attackAimInfo = RoleInfoMap.getRoleInfo(req.getAttackAim());
			}
			if (attackAimInfo != null) {
				// 普通攻击
				/*if (roleInfo.getAttackAim() != req.getAttackAim()
						|| roleInfo.getAttackAimType() != req.getAttackAimType()) {
					Event e = new AttackEvent(roleInfo.getId(), EventActor.EVENT_ACTOR_PLAYER, attackAimInfo.getId(),
							aimType, req.getAttackAimCurrX(), req.getAttackAimCurrY(), req.getAttackAimCurrZ());
					EventChannel.addEvent(roleInfo.getMapId(), e, roleInfo.getBattleStatus());
				}*/
				Event event = new AttackEvent(roleInfo.getId(), EventActor.EVENT_ACTOR_PLAYER, attackAimInfo.getId(),
						aimType, req.getAttackAimCurrX(), req.getAttackAimCurrY(), req.getAttackAimCurrZ());
				// 剔除已有的攻击事件，添加新的攻击事件
				EventChannel.removeEvent(event, roleInfo.getMapId());
				EventChannel.addEvent(roleInfo.getMapId(), event, roleInfo.getBattleStatus());
				

			}

		}
	}

	/**
	 * 技能攻击
	 */
	public void skillAttack(int roleId, SkillAttackReq req) {

		RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
		if (roleInfo != null && roleInfo.getStatus() != 2) {
			byte aimType = req.getAttackAimType();
			AIInfo attackAimInfo = null;
			if (aimType == 0)// NPC
			{
				NPCInfo npcInfo = NPCInfoMap.getNPCInfo(roleInfo.getMapId(), req.getAttackAim());
				if(npcInfo != null && npcInfo.getNpcType() != 0)
					attackAimInfo = npcInfo;
			}
			else if (aimType == 1)// 玩家
			{
				attackAimInfo = RoleInfoMap.getRoleInfo(req.getAttackAim());
			}
			if (attackAimInfo != null && req.getSkillType() > 0) {
				// 技能攻击
				Event e = new SkillAttackEvent(roleInfo.getId(), EventActor.EVENT_ACTOR_PLAYER, attackAimInfo.getId(),
						aimType, req.getSkillType(), req.getSkillLevel());
				EventChannel.addEvent(roleInfo.getMapId(), e, roleInfo.getBattleStatus());

			}
			else if (req.getSkillType() > 0 && req.getAttackAimCurrX() != -1 && req.getAttackAimCurrY() != -1
					&& req.getAttackAimCurrZ() != -1) {
				Event e = new SkillAttackEvent(roleInfo.getId(), EventActor.EVENT_ACTOR_PLAYER,
						req.getAttackAimCurrX(), req.getAttackAimCurrY(), req.getAttackAimCurrZ(), req.getSkillType(),
						req.getSkillLevel());
				EventChannel.addEvent(roleInfo.getMapId(), e, roleInfo.getBattleStatus());
			}

		}

	}

	/**
	 * 切换场景
	 */
	public ChangeSceneResp changeScene(int roleId, ChangeSceneReq req) {
		ChangeSceneResp resp = new ChangeSceneResp();
		RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
		if(roleInfo==null)
		{
			resp.setResult(ErrorCode.SYSTEM_ERROR);
			return resp;
		}
		synchronized (roleInfo) {
			
			MapDoorXMLInfo doorInfo = MapDoorXMlInfoMap.getMapDoor(roleInfo.getSceneId(), roleInfo.getMapId(), req.getCurrX(), req.getCurrY(), req.getCurrZ());
			
			if(doorInfo != null){
				// 两点之间距离
				double space = SceneService.getLine(new Step3D(roleInfo.getCurrX(),
						roleInfo.getCurrY(), roleInfo.getCurrZ()),
						new Step3D(req.getCurrX(), req.getCurrY(), req.getCurrZ()));
				// 如果客户端与服务端当前坐标点相差距离大于最大误差，则不能切换
				if (Math.abs(space) > GameValue.CURR_POINT_CORRECT_SPACE) {
					resp.setResult(ErrorCode.CHANGE_SCENE_ERROR_1);
					return resp;
				}
				
				
				if(doorInfo.getSceneId() != doorInfo.getToSceneId()){
					OutSceneReq outScenReq = new OutSceneReq();
					outScene(roleId, outScenReq);
				}
				
				roleInfo.setSceneId(doorInfo.getToSceneId());
				roleInfo.setMapId(doorInfo.getToMapId());
				roleInfo.setCurrX(doorInfo.getToX());
				roleInfo.setCurrY(doorInfo.getToY());
				roleInfo.setCurrZ(doorInfo.getToZ());
				
				saveRoleBase(roleInfo);
				
				resp.setMapId(roleInfo.getMapId());
				resp.setSceneId(roleInfo.getSceneId());
				resp.setResult(1);
			}
			else{
				resp.setResult(ErrorCode.CHANGE_SCENE_ERROR_1);
			}
			return resp;
		}
		
	}

	public void saveRoleBase(RoleInfo roleInfo) {
		
	}

	/* (non-Javadoc)
	 * @see com.snail.webgame.engine.component.scene.core.ISceneMgtService#rebirth(int, int)
	 */
	public RebirthResp rebirth(int roleId, int rebirthType) {
		RebirthResp resp = new RebirthResp();
		RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
		if(roleInfo==null)
		{
			resp.setResult(ErrorCode.SYSTEM_ERROR);
			return resp;
		}
		synchronized (roleInfo) {
			// 角色当前状态
			int status = roleInfo.getStatus();
			// 如果目标已经死亡
			if (status == 2) {
				// 原地复活
				if (rebirthType == 1) {
					// 当前血量和魔法设为最大值一半
					roleInfo.setCurrHP(roleInfo.getMaxHP() / 2);
					roleInfo.setCurrMP(roleInfo.getMaxMP() / 2);
				}
				// 出生点复活
				else if (rebirthType == 2) {
					
				}
				// 角色当前状态设为‘待机’
				roleInfo.setStatus(0);
				
				resp.setResult(1);
				resp.setRoleId(roleInfo.getId());
				resp.setCurrX(roleInfo.getCurrX());
				resp.setCurrY(roleInfo.getCurrY());
				resp.setCurrZ(roleInfo.getCurrZ());
				resp.setCurrHP(roleInfo.getCurrHP());
				resp.setCurrMP(roleInfo.getCurrMP());
				resp.setStatus(roleInfo.getStatus());
				// 给周围其它玩家发送玩家复活的消息
				SceneService.sendRoleBirthInfo(roleInfo, resp);
			}
			else{
				resp.setResult(ErrorCode.ROLE_REBIRTH_ERROR_1);
			}
			
			return resp;
		}
	}

	/* (non-Javadoc)
	 * @see com.snail.webgame.engine.component.scene.core.ISceneMgtService#resetPosition(int)
	 */
	@Override
	public void resetPosition(int roleId) {
		RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
		if(roleInfo != null)
		{
			synchronized (roleInfo) {
				// 重置范围半径
				//int arange = 5;
				GameMap3D gameMap = SceneGameMapFactory.getGameMap(roleInfo.getMapId());
				// 获取当前点范围内的可行走点
				Vector3 v = gameMap.getHelper().getClosetPoint(roleInfo.getCurrX(), roleInfo.getCurrY(), roleInfo.getCurrZ());
				logger.info("角色：" + roleId + "重置坐标为：" + v.x + "," + v.y + "," + v.z);
				roleInfo.setCurrX(v.x);
				roleInfo.setCurrY(v.y);
				roleInfo.setCurrZ(v.z);
				
				SceneService.sendMoveEndMsg(roleInfo);
			}
			
		}
	}
	
	

}
