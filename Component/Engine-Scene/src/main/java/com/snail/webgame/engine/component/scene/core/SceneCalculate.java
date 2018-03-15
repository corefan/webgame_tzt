package com.snail.webgame.engine.component.scene.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.critterai.math.Vector3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.common.info.AIInfo;
import com.snail.webgame.engine.common.info.BuffInfo;
import com.snail.webgame.engine.common.info.DefendInfo;
import com.snail.webgame.engine.common.info.NPCInfo;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.common.info.ViewChangeInfo;
import com.snail.webgame.engine.common.pathfinding.astar.GameMap;
import com.snail.webgame.engine.common.pathfinding.astar.Mover;
import com.snail.webgame.engine.common.pathfinding.astar.Path;
import com.snail.webgame.engine.common.pathfinding.astar.Step;
import com.snail.webgame.engine.common.pathfinding.mesh.GameMap3D;
import com.snail.webgame.engine.common.pathfinding.mesh.Path3D;
import com.snail.webgame.engine.common.pathfinding.mesh.Step3D;
import com.snail.webgame.engine.component.scene.cache.EventChannel;
import com.snail.webgame.engine.component.scene.cache.NPCInfoMap;
import com.snail.webgame.engine.component.scene.config.GameConfig;
import com.snail.webgame.engine.component.scene.event.AttackEvent;
import com.snail.webgame.engine.component.scene.event.Event;
import com.snail.webgame.engine.component.scene.event.EventActor;
import com.snail.webgame.engine.component.scene.event.MoveEndEvent;
import com.snail.webgame.engine.component.scene.event.MoveEvent;
import com.snail.webgame.engine.component.scene.event.SkillAttackEvent;
import com.snail.webgame.engine.component.scene.protocal.datasyn.DataSynService;

/**
 * 场景计算
 * 
 * @author zenggy
 * 
 */
public class SceneCalculate implements ISceneCalculate{
	private static final Logger logger = LoggerFactory.getLogger("logs");

	/**
	 * 处理各种行为事件 如移动 攻击 停止 等
	 * 
	 */
	public void mgtPhalanxPolicy(Event event, AIInfo aiInfo) {
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(aiInfo.getMapId());
		if (gameMap == null)
			return;
		BuffService.checkPhalanxBuff(aiInfo);
		if (event instanceof MoveEvent)// 移动
		{
			boolean bool = BuffService.existBuff(aiInfo, 1);
			if(!bool){
				if (aiInfo.getCurrMoveMaxTime() > 0) {
					int time = (int) (System.currentTimeMillis() - event
							.getTime());
					aiInfo.setCurrMoveTime(aiInfo.getCurrMoveTime() + time);
	
				}
				phalanxMove(aiInfo, gameMap, (MoveEvent) event);
			}
		}
		else if (event instanceof SkillAttackEvent)// 技能攻击
		{
			// 判定有没有不能攻击的buff
			boolean bool = BuffService.existBuff(aiInfo, 2);
			if (!bool) {
				SkillAttackEvent skillAttackEvent = (SkillAttackEvent) event;
				phalanxReleaseSkill(aiInfo, skillAttackEvent);
			}
			
		}
		else if (event instanceof AttackEvent)// 攻击
		{
			// 判定有没有不能攻击的buff
			boolean bool = BuffService.existBuff(aiInfo, 2);
			if (!bool) {
				AttackEvent attackEvent = (AttackEvent) event;
				if (aiInfo.getCurrAttackMaxTime() > 0) {
					int time = (int) (System.currentTimeMillis() - event
							.getTime());
					aiInfo.setCurrAttackTime(aiInfo.getCurrAttackTime() + time);
				}
				if(aiInfo.getAttackAim() != attackEvent.getAttackAim() || aiInfo.getAttackAimType() != attackEvent.getAttackAimType())
				{
					aiInfo.setCurrAttackTime(aiInfo.getCurrAttackMaxTime());
				}
				aiInfo.setAttackAim(attackEvent.getAttackAim());
				aiInfo.setAttackAimType(attackEvent.getAttackAimType());
				phalanxMoveAttack(aiInfo, gameMap, attackEvent);
			}
			// 如果有不能攻击的buff，则继续添加攻击事件
			else {
				if (!EventChannel.existEvent(event, aiInfo.getMapId(), aiInfo.getBattleStatus())) {
					event.setTime(System.currentTimeMillis());
					EventChannel.addEvent(aiInfo.getMapId(), event, aiInfo.getBattleStatus());
				}
			}
		}
		else if (event instanceof MoveEndEvent) {
			MoveEndEvent moveEndEvent = (MoveEndEvent)event;
			// 两点之间距离
			double space = SceneService.getLine(new Step3D(aiInfo.getCurrX(),
					aiInfo.getCurrY(), aiInfo.getCurrZ()),
					new Step3D(moveEndEvent.getEndX(), moveEndEvent.getEndY(), moveEndEvent.getEndZ()));
			// 如果客户端停止点与服务端当前坐标点相差距离大于最大误差，则通知客户端移到服务端坐标
			if (Math.abs(space) > GameValue.CURR_POINT_CORRECT_SPACE) {
				aiInfo.resetAimPoint();
				aiInfo.setAction("0");
				aiInfo.setStatus(0);// 待机
				aiInfo.setPath(null);
				aiInfo.setCurrMoveTime(0);
				if (logger.isWarnEnabled()) {
					logger.warn("Force the role back to the server's curr point, " +
							"because the custom's aim point(x = " + moveEndEvent.getEndX() + 
							", y = " + moveEndEvent.getEndY() + ", z = " + moveEndEvent.getEndZ() + 
							") diff to the server's aim point (x = " + aiInfo.getCurrX() + 
							", y = " + aiInfo.getCurrY() + ", z = " + aiInfo.getCurrZ() + 
							") : roleId = " + aiInfo.getId() + ", battleStatus=" + aiInfo.getBattleStatus());
				}
				SceneService.sendMoveEndMsg(aiInfo);
			}
			else {
				if (aiInfo instanceof RoleInfo)
					gameMap.delRole((int) aiInfo.getCurrX(), (int) aiInfo.getCurrY(),
							(int) aiInfo.getCurrZ(), aiInfo.getId());

				aiInfo.setCurrX(moveEndEvent.getEndX());
				aiInfo.setCurrY(moveEndEvent.getEndY());
				aiInfo.setCurrZ(moveEndEvent.getEndZ());
				aiInfo.resetAimPoint();
				aiInfo.setPath(null);
				aiInfo.setCurrMoveTime(0);
				// add by wangxf 2013-0114 更新停止时人物或者NPC的状态
				/*// 状态为移动时更新状态为待机
				if (aiInfo.getStatus() == 1) {
					aiInfo.setStatus(0);// 待机
				}*/
				aiInfo.setStatus(0);// 待机
				// end by wangxf
				
				if (aiInfo instanceof RoleInfo)
					gameMap.setRole((int) aiInfo.getCurrX(), (int) aiInfo.getCurrY(),
							(int) aiInfo.getCurrZ(), aiInfo.getId());

				SceneService.sendMoveEndMsgExcludeSelf(aiInfo);
			}

		}
		
	}

	/**
	 * 处理普通攻击，如果目标不在可攻击范围内，则先移动
	 * 
	 * @param aiInfo
	 * @param gameMap
	 */
	public void phalanxMoveAttack(AIInfo attack, GameMap3D gameMap, AttackEvent event) {
		//如果没有攻击目标，或者自己已经死亡
		/*if (attack.getAttackAim() <= 0 || attack.getStatus() == 2 || (attack.getStatus() != 4 && attack.getStatus() != 3)) {
			attack.setCurrAttackTime(0);
			attack.setAttackAim(0);
			attack.setAttackAimType(-1);
			attack.setCurrMoveTime(0);
			attack.setCurrPursueTime(0);
			
			// add by wangxf 2012-12-10
			if (attack.getBattleStatus() != 0) {
				// 设置为非战斗状态 
				attack.set("battleStatus", 0);
				// 如果是玩家就同步玩家战斗状态
				if (attack instanceof RoleInfo) {
					//System.out.println("wangxf=============玩家退出战斗状态156");
					// 玩家状态同步给附近的所有玩家
					DataSynService.sendDataSyn(attack, attack, true);
				}
			}
			// end by wangxf
			
			return;
		}*/
		
		if (attack.getAttackAim() <= 0 || attack.getStatus() == 2) {
			attack.setCurrAttackTime(0);
			attack.setAttackAim(0);
			attack.setAttackAimType(-1);
			attack.setCurrMoveTime(0);
			attack.setCurrPursueTime(0);
			
			// add by wangxf 2012-12-10
			if (attack.getBattleStatus() != 0) {
				// 设置为非战斗状态 
				attack.set("battleStatus", 0);
				// 如果是玩家就同步玩家战斗状态
				if (attack instanceof RoleInfo) {
					//System.out.println("wangxf=============玩家退出战斗状态156");
					// 玩家状态同步给附近的所有玩家
					DataSynService.sendDataSyn(attack, attack, true);
				}
			}
			// end by wangxf
			
			return;
		}
		
		AIInfo defend = null;
		if (attack.getAttackAimType() == 0)// NPC
		{
			defend = NPCInfoMap.getNPCInfo(attack.getMapId(), attack.getAttackAim());
		}
		else if (attack.getAttackAimType() == 1)// 玩家
		{
			defend = RoleInfoMap.getRoleInfo(attack.getAttackAim());
		}
		if(defend == null || defend.getStatus() == 2){
			attack.setCurrAttackTime(0);
			attack.setAttackAim(0);
			attack.setAttackAimType(-1);
			attack.setCurrMoveTime(0);
			attack.setCurrPursueTime(0);
			
			// add by wangxf 2012-12-10
			if (attack.getBattleStatus() != 0) {
				// 设置为非战斗状态 
				attack.set("battleStatus", 0);
				// 如果是玩家就同步玩家战斗状态
				if (attack instanceof RoleInfo) {
					//System.out.println("wangxf=============玩家退出战斗状态189");
					// 玩家状态同步给附近的所有玩家
					DataSynService.sendDataSyn(attack, attack, true);
				}
			}
			// end by wangxf
			
			return;
		}
		
		if(defend instanceof NPCInfo){
			float attackAimCurrX = event.getAttackAimCurrX();
			float attackAimCurrY = event.getAttackAimCurrY();
			float attackAimCurrZ = event.getAttackAimCurrZ();
			if(attackAimCurrX != -1 && attackAimCurrY != -1 && attackAimCurrZ != -1){
				// 两点之间距离
				float space = SceneService.getLine(new Step3D(defend.getCurrX(),  defend.getCurrY(),  defend.getCurrZ()), new Step3D(attackAimCurrX, attackAimCurrY, attackAimCurrZ));
				// 如果客户端NPC坐标与服务器相差不大，则以客户端坐标为准
				if (Math.abs(space) <= GameValue.CURR_POINT_CORRECT_SPACE) {
					/*System.out.println("====服务端当前坐标(" + defend.getCurrX() + "," + defend.getCurrY() + "," + defend.getCurrZ() + 
							"; 客户端当前坐标(" + attackAimCurrX + "," + attackAimCurrY + "," + attackAimCurrZ + ")");*/
					// 因为服务端与客户端当前的坐标位置距离可能不大，但是坐标所在MapPoint数组的下标不一样，所以需要下面的操作
					// 先删除服务端npc当前所在MapPoint的位置，然后再在客户端npc当前所在的MapPoint位置增加该npc
					gameMap.delNPC((int) defend.getCurrX(), (int) defend.getCurrY(), 
							(int) defend.getCurrZ(), defend.getId());
					defend.setCurrX(attackAimCurrX);
					defend.setCurrY(attackAimCurrY);
					defend.setCurrZ(attackAimCurrZ);
					gameMap.setNPC((int) defend.getCurrX(), (int) defend.getCurrY(), 
							(int) defend.getCurrZ(), defend.getId());
				}
			}
		}

		if (attack.getCurrMoveMaxTime() >= 0) {
			int aimStatus = judeAppointAim(attack);
			if (aimStatus == 0) {
				attack.setAttackAim(0);
				attack.setAttackAimType(-1);
				attack.setPath(null);
				attack.setCurrMoveTime(0);
				attack.setCurrPursueTime(0);
				// add by wangxf 2012-12-10
				if (attack.getBattleStatus() != 0) {
					// 设置为非战斗状态 
					attack.set("battleStatus", 0);
					// 如果是玩家就同步玩家战斗状态
					if (attack instanceof RoleInfo) {
						//System.out.println("wangxf=============玩家退出战斗状态237");
						// 玩家状态同步给附近的所有玩家
						DataSynService.sendDataSyn(attack, attack, true);
					}
				}
				// end by wangxf
				return;
			}
			else if (aimStatus == 1) {
				byte eventActor = EventActor.EVENT_ACTOR_PLAYER;
				if (attack instanceof NPCInfo) {
					eventActor = EventActor.EVENT_ACTOR_NPC;
				}
				if (eventActor == EventActor.EVENT_ACTOR_NPC && (attack.getStatus() == 1 || attack.getStatus() == 3)) {
					attack.setPath(null);
					attack.resetAimPoint();
					SceneService.sendMoveEndMsg(attack);
				}
				if (attack.getCurrAttackMaxTime() > 0 && attack.getCurrAttackTime() >= attack.getCurrAttackMaxTime()) {
					DefendInfo defendInfo = calAttackDamage(attack, defend, event);
					if (defendInfo != null) {
						SceneService.sendAttackMsg(attack, defend, defendInfo.getAimLossHP());
					}
				}
				// 如果存在移动事件，则不再添加攻击事件
				if (!EventChannel.existEvent(event, attack.getMapId(), attack.getBattleStatus()) && 
						!EventChannel.existEvent(new MoveEvent(attack.getId(), eventActor, attack.getPath()), attack.getMapId(), attack.getBattleStatus())) {
					event.setTime(System.currentTimeMillis());
					EventChannel.addEvent(attack.getMapId(), event, attack.getBattleStatus());
				}
			}
			else if (aimStatus == 2) {
				if (attack instanceof NPCInfo) {
					if (attack.getCurrMoveMaxTime() > 0) {
						int time = (int) (System.currentTimeMillis() - event
								.getTime());
						attack.setCurrMoveTime(attack.getCurrMoveTime() + time);
	
					}
					pursue(attack, defend, gameMap, event);
				}
				else{
					
					defend.getAttackActorList().remove(EventActor.EVENT_ACTOR_PLAYER + "_" + attack.getId());
					
					attack.setPath(null);
					attack.resetAimPoint();
					attack.setAttackAim(0);
					attack.setAttackAimType(-1);
					/*if(attack.getAttackActorList().isEmpty()) {
						if (attack.getBattleStatus() != 0) {
							// add by wangxf 2012-12-10
							attack.set("battleStatus", 0);
							System.out.println("wangxf================玩家退出战斗状态309");
							// 玩家状态同步给附近的所有玩家
							DataSynService.sendDataSyn(attack, attack, true);
							// end by wangxf
						}
					}*/
					attack.setStatus(0);
				}
			}
			
		}

	}
	
	/**
	 * 追击
	 * @param attack 攻击方
	 * @param defend 被攻击方
	 */
	public void pursue(AIInfo attack, AIInfo defend, GameMap3D gameMap, AttackEvent event){
			byte eventActor = EventActor.EVENT_ACTOR_NPC;
			boolean bool = BuffService.existBuff(attack, 1);
			if(bool){
				attack.setCurrMoveTime(0);
			}
			if(attack.getCurrMoveMaxTime() >= 0 && attack.getCurrMoveTime() > 0 && !bool)
			{
				//如果NPC不能追击，返回
				if(attack instanceof NPCInfo && attack.getMaxPursueTime() <=0)
					return;
				//如果NPC追击时间大于最大时间，则让NPC移动到初始点
				if (attack instanceof NPCInfo
						&& attack.getCurrPursueTime() >= attack.getMaxPursueTime()) {
					Path3D path = getPhalanxPath(gameMap,
							attack.getCurrX(), attack.getCurrY(), attack.getCurrZ(),
							attack.getPursueRevertX(), attack.getPursueRevertY(), 
							attack.getPursueRevertZ());
					attack.setAttackAim(0);
					attack.setAttackAimType(-1);
					attack.setCurrPursueTime(0);
					attack.setCurrMoveTime(0);
					attack.setCurrAttackTime(0);
					
					defend.getAttackActorList().remove(eventActor + "_" + attack.getId());
					if(defend.getAttackActorList().isEmpty()) {
						// add by wangxf 2012-12-10
						if (defend.getBattleStatus() != 0) {
							// 设置被攻击者为非战斗状态
							defend.set("battleStatus", 0);
							//System.out.println("wangxf===================玩家退出战斗状态339");
							// 玩家状态同步给附近的所有玩家
							DataSynService.sendDataSyn(defend, defend, true);
						}
						// end by xfwang
					}
						
					
					attack.setBattleStatus(0);
					if(path != null){
						List<Step3D> steps = path.getSteps();
						int len = steps.size();
						if(len > 1){
							Step3D endStep = steps.get(len-1);
							attack.setAimX(endStep.getX());
							attack.setAimY(endStep.getY());
							attack.setAimZ(endStep.getZ());
							attack.setPath(path);
							//attack.setStatus(1);
							SceneService.sendMoveMsg(attack, path);
							
							Event e = new MoveEvent(attack.getId(), eventActor, path);
							EventChannel.addEvent(attack.getMapId(), e, attack.getBattleStatus());
						}
					}
					else{
						stopMove(attack);
					}
					return;
				}
				else {
					Path3D path = attack.getPath();
					//当前追击目标点与现在攻击目标位置距离
					float line = SceneService.getLine(
							new Step3D(attack.getAimX(), attack.getAimY(), attack.getAimZ()), 
							new Step3D(defend.getCurrX(), defend.getCurrY(), defend.getCurrZ()));
					boolean isFirstMove = false;
					if(path == null)
						isFirstMove = true;
					if(path == null || line > attack.getCurrMaxAttackGrid()){
						path = getPhalanxPath(gameMap, attack.getCurrX(), 
								attack.getCurrY(), attack.getCurrZ(), 
								defend.getCurrX(), defend.getCurrY(), defend.getCurrZ());
						if(path != null){
							List<Step3D> steps = path.getSteps();
							int len = steps.size();
							if(len > 1){
								//如果目标不在移动或追击状态，则移动到可攻击到目标的范围内
								if(defend.getStatus() != 1 && defend.getStatus() != 3){
									Step3D step1 = steps.get(len-2);
									Step3D step2 = new Step3D(defend.getCurrX(), defend.getCurrY(), defend.getCurrZ());
									
									
									float space = (float)attack.getCurrMaxAttackGrid();
									space -= 0.5;
									float newSpareSpace = (float)SceneService.getLine(step1, step2);
									if(len > 2 && newSpareSpace < space){
										steps.remove(len-1);
										attack.setAimX(step1.getX());
										attack.setAimY(step1.getY());
										attack.setAimZ(step1.getZ());
									}
									else{
										float x = step2.getX() - (space / newSpareSpace * (step2.getX() - step1.getX()));
										float y = step2.getY() - (space / newSpareSpace * (step2.getY() - step1.getY()));
										float z = step2.getZ() - (space / newSpareSpace * (step2.getZ() - step1.getZ()));
										Step3D endStep = new Step3D(x, y, z);
										steps.remove(len-1);
										steps.add(endStep);
										attack.setAimX(x);
										attack.setAimY(y);
										attack.setAimZ(z);
									}
								}
								else{
									attack.setAimX(defend.getCurrX());
									attack.setAimY(defend.getCurrY());
									attack.setAimZ(defend.getCurrZ());
								}
								attack.setPath(path);
								attack.setStatus(3);
								SceneService.sendMoveMsg(attack, path);
							}
						}
					}
					
					if(!isFirstMove){
						move(gameMap, attack, path);
						attack.setCurrPursueTime(attack.getCurrPursueTime()+ attack.getCurrMoveTime());
					}
					attack.setCurrMoveTime(0);
					int aimStatus = judeAppointAim(attack);
					if (aimStatus == 1) {
						if (eventActor == EventActor.EVENT_ACTOR_NPC
								&& (attack.getStatus() == 1 || attack.getStatus() == 3)) {
							attack.setPath(null);
							attack.resetAimPoint();
							attack.setStatus(4);
							SceneService.sendMoveEndMsg(attack);
						}
						if (attack.getCurrAttackMaxTime() > 0
								&& attack.getCurrAttackTime() >= attack.getCurrAttackMaxTime()) {
							DefendInfo defendInfo = calAttackDamage(attack, defend, event);
							if (defendInfo != null)
								SceneService.sendAttackMsg(attack, defend, defendInfo.getAimLossHP());
						}
					}
					
				}
			}
			if (!EventChannel.existEvent(event, attack.getMapId(), attack.getBattleStatus())) {
				event.setTime(System.currentTimeMillis());
				EventChannel.addEvent(attack.getMapId(), event, attack.getBattleStatus());
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
	 * 计算攻击伤害，并通知客户端
	 * @param attack 攻击方
	 * @param defend 被攻击方
	 */
	public DefendInfo calAttackDamage(AIInfo attack, AIInfo defend, Event event) {return null;}

	/**
	 * 执行事件
	 * 
	 * @return 执行事件数
	 */
	public int calAllRolePolicy(String mapId, int battleStatus) {
		Queue<Event> eventQueue = EventChannel.getEvent(mapId, battleStatus);
		if (eventQueue != null && !eventQueue.isEmpty()) {
			int size = 0;
			Event event;
			while ((event = eventQueue.poll()) != null) {
				byte eventActor = event.getEventActor();
				AIInfo aiInfo = null;
				// 事件发起者是玩家
				if (eventActor == EventActor.EVENT_ACTOR_PLAYER) {
					aiInfo = RoleInfoMap.getRoleInfo(event.getTargetId());
				}
				// 事件发起者是NPC
				else if (eventActor == EventActor.EVENT_ACTOR_NPC) {
					aiInfo = NPCInfoMap.getNPCInfo(mapId, event.getTargetId());
				}
				
				if (aiInfo != null){ //&& aiInfo.getBattleStatus() == battleStatus) {
					mgtPhalanxPolicy(event, aiInfo);
				}
				
				size++;
				if (size >= 100)
					break;
			}
			return size;
		}
		return 0;
	}

	/**
	 * 方正移动
	 */
	public void phalanxMove(AIInfo aiInfo, GameMap3D gameMap, MoveEvent event) {
		byte eventActor = EventActor.EVENT_ACTOR_PLAYER;
		if (aiInfo instanceof NPCInfo) {
			eventActor = EventActor.EVENT_ACTOR_NPC;
		}
		//if (aiInfo.getStatus() == 1) {}

		
		Path3D path = ((MoveEvent)event).getPath();
		if(path == null || path.getSteps().size() <= 1)
			return;
		if(path != aiInfo.getPath()){
			ArrayList<Step3D> steps = path.getSteps();
			Step3D aimStep = steps.get(steps.size() - 1);
			
			// 两点之间距离
			float space = SceneService.getLine(
					new Step3D(aiInfo.getCurrX(), aiInfo.getCurrY(), aiInfo.getCurrZ()),
					steps.get(0));
			// 如果客户端新路径起使点与服务端当前坐标点相差距离大于最大误差，则通知客户端移到服务端坐标
			if (Math.abs(space) > GameValue.CURR_POINT_CORRECT_SPACE) {
				aiInfo.resetAimPoint();
				aiInfo.setAction("0");
				aiInfo.setStatus(0);// 待机
				aiInfo.setAttackAim(-1);
				aiInfo.setAttackAimType(-1);
				aiInfo.setCurrPursueTime(0);
				aiInfo.setCurrAttackTime(0);
				aiInfo.setPath(null);
				aiInfo.setCurrMoveTime(0);
				if (logger.isWarnEnabled()) {
					logger.warn("Force the role stop, because the custom's start point(x = " + steps.get(0).getX() + 
							", y = " + steps.get(0).getY() + ", z = " + steps.get(0).getZ() + 
							") diff to the server's start point (x = " + aiInfo.getCurrX() + 
							", y = " + aiInfo.getCurrY() + ", z = " + aiInfo.getCurrZ() + 
							") : roleId = " + aiInfo.getId() + ", battleStatus=" + aiInfo.getBattleStatus());
				}
				SceneService.sendMoveEndMsg(aiInfo);
				return;
			}
			if(aiInfo instanceof RoleInfo)
				gameMap.delRole((int) aiInfo.getCurrX(), (int) aiInfo.getCurrY(), (int) aiInfo.getCurrZ(), aiInfo.getId());
			if(aiInfo instanceof NPCInfo) {
				gameMap.delNPC((int) aiInfo.getCurrX(), (int) aiInfo.getCurrY(), (int) aiInfo.getCurrZ(), aiInfo.getId());
			}
				
			

			aiInfo.setAimX(aimStep.getX());
			aiInfo.setAimY(aimStep.getY());
			aiInfo.setAimZ(aimStep.getZ());
			aiInfo.setCurrX(steps.get(0).getX());
			aiInfo.setCurrY(steps.get(0).getY());
			aiInfo.setCurrZ(steps.get(0).getZ());
			aiInfo.setPath(path);
			if(aiInfo instanceof RoleInfo)
				gameMap.setRole((int) aiInfo.getCurrX(), (int) aiInfo.getCurrY(), (int) aiInfo.getCurrZ(), aiInfo.getId());
			if(aiInfo instanceof NPCInfo) {
				gameMap.setNPC((int) aiInfo.getCurrX(), (int) aiInfo.getCurrY(), (int) aiInfo.getCurrZ(), aiInfo.getId());
			}
				
		}
		
		if (aiInfo.getCurrMoveTime() > 0 && aiInfo.getCurrMoveMaxTime() >= 0) {
			if (aiInfo.getAimX() >= 0 && aiInfo.getAimY() >= 0 && aiInfo.getAimZ() >= 0) {
				
				long begTime = System.currentTimeMillis();
				move(gameMap, aiInfo, path);
				long excuteMoveTime = System.currentTimeMillis() - begTime;
				if (aiInfo.getCurrX() != aiInfo.getAimX()
						|| aiInfo.getCurrY() != aiInfo.getAimY() 
						|| aiInfo.getCurrZ() != aiInfo.getAimZ()) {
					//如果不存在移动停止事件才继续添加移动事件
					if(!EventChannel.existEvent(
							new MoveEndEvent(aiInfo.getId(), eventActor, aiInfo.getAimX(), aiInfo.getAimY(), aiInfo.getAimZ()), 
							aiInfo.getMapId(), aiInfo.getBattleStatus())
							&& !EventChannel.existEvent(event, aiInfo.getMapId(), aiInfo.getBattleStatus()))
					{
						event.setTime(System.currentTimeMillis()-excuteMoveTime);
						EventChannel.addEvent(aiInfo.getMapId(), event,
								aiInfo.getBattleStatus());
					}
				}
				
				// 如果是npc移动的话，判断是否到达终点，并更新它的状态为静止
				if (aiInfo instanceof NPCInfo) {
					if (aiInfo.getCurrX() == aiInfo.getAimX()
							&& aiInfo.getCurrY() == aiInfo.getAimY()
							&& aiInfo.getCurrZ() == aiInfo.getAimZ()) {
						stopMove(aiInfo);
					}
				}

			}
			aiInfo.setCurrMoveTime(0);

		}
		else {
			//如果不存在移动停止事件才继续添加移动事件
			if(!EventChannel.existEvent(
					new MoveEndEvent(aiInfo.getId(), eventActor, aiInfo.getAimX(), aiInfo.getAimY(), aiInfo.getAimZ()), 
					aiInfo.getMapId(), aiInfo.getBattleStatus())
					&& !EventChannel.existEvent(event, aiInfo.getMapId(), aiInfo.getBattleStatus()))
			{
				event.setTime(System.currentTimeMillis());
				EventChannel.addEvent(aiInfo.getMapId(), event,
						aiInfo.getBattleStatus());
			}
		}

	

	}

	/**
	 * 获得移动路径
	 * 
	 * @return
	 */
	public Path3D getPhalanxPath(GameMap3D map, float srcX, float srcY, float srcZ, float endX,
			float endY, float endZ) {
		Path3D path = null;
		long time = System.currentTimeMillis();
		Vector3 start = new Vector3(srcX, srcY, srcZ);
		Vector3 end = new Vector3(endX, endY, endZ);
		
		List<Vector3> listVector3  = map.getHelper().getPathPoint(start, end);
		if (listVector3 != null && listVector3.size() > 1) {
			path = new Path3D();
			for (Vector3 v : listVector3) {
				path.appendStep(v.x, v.y, v.z);
			}
		}
		/*// 如果两点之间存在障碍物才用A星寻路
		if (map.hasBarrier(srcX, srcY, endX, endY)) {
			// true 8方向 false 4方向
			PathFinder finder = new AStarPathFinder(map, 500, true,
					new ManhattanHeuristic(10));

			path = finder.findPath(new UnitMover(type, moveFlag), srcX, srcY,
					endX, endY);
			floyd(map, path);
		}
		else {
			path = new Path();
			path.appendStep(srcX, srcY);
			path.appendStep(endX, endY);
			path.setSmoothPaths(path.getSteps());
		}*/
		long time1 = System.currentTimeMillis();
		if (time1 - time > 2000) {
			logger.error("Nav cal exception:scrX=" + srcX + ",srcY=" + srcY + ",srcZ=" + srcZ
					+ ",endX=" + endX + ",endY=" + endY + ",endZ=" + endZ + ",time="
					+ (time1 - time));
		}

		return path;
	}
	
	/**
	 * 弗洛伊德路径平滑处理
	 * 
	 * @return
	 */
	private void floyd(GameMap gameMap, Path path, Mover mover) {
		if (path == null)
			return;
		ArrayList<Step> smoothPaths = new ArrayList<Step>();
		smoothPaths.addAll(path.getSteps());
		int len = smoothPaths.size();
		if (len > 2) {
			Point vector = new Point(0, 0);
			Point tempVector = new Point(0, 0);

			// 遍历路径数组中全部路径节点，合并在同一直线上的路径节点
			// 假设有1，2，3三点，若2与1的横、纵坐标差值分别与3与2的横、纵坐标差值相等则
			// 判断此三点共线，此时可以删除中间点2
			floydVector(vector, smoothPaths.get(len - 1),
					smoothPaths.get(len - 2));
			for (int i = smoothPaths.size() - 3; i >= 0; i--) {
				floydVector(tempVector, smoothPaths.get(i + 1),
						smoothPaths.get(i));
				if (vector.x == tempVector.x && vector.y == tempVector.y) {
					smoothPaths.remove(i + 1);
				}
				else {
					vector.x = tempVector.x;
					vector.y = tempVector.y;
				}
			}
		}

		// 合并共线节点后进行第二步，消除拐点操作。算法流程如下：
		// 如果一个路径由1-10十个节点组成，那么由节点10从1开始检查
		// 节点间是否存在障碍物，若它们之前不存在障碍物，则直接合并
		// 此两路径节点间所有节点
		len = smoothPaths.size();
		for (int i = len - 1; i >= 0; i--) {
			for (int j = 0; j <= i - 2; j++) {
				Step step1 = smoothPaths.get(i);
				Step step2 = smoothPaths.get(j);
				if (!gameMap.hasBarrier((int) step1.getX(), (int) step1.getY(),
						(int) step2.getX(), (int) step2.getY(), mover)) {
					for (int k = i - 1; k > j; k--) {
						smoothPaths.remove(k);
					}
					i = j;
					len = smoothPaths.size();
					break;
				}
			}
		}
		path.setSmoothPaths(smoothPaths);
	}

	private void floydVector(Point target, Step n1, Step n2) {
		target.x = (int) (n1.getX() - n2.getX());
		target.y = (int) (n1.getY() - n2.getY());
	}


	/**
	 * 执行部队移动
	 */
	public void move(GameMap3D gameMap, AIInfo aiInfo, Path3D path) {
		if (path != null) {
			// 设置为移动状态，是追击状态则不变更
			if (aiInfo.getStatus() != 3) {
				aiInfo.setStatus(1);
			}
			// 获取服务端一次移动后当前所在的坐标点
			Step3D step = getCurrPoint(aiInfo, path);
			// 服务端一次移动后当前的X坐标点
			float x = step.getX();
			// 服务端一次移动后当前的Y坐标点
			float y = step.getY();
			// 服务端一次移动后当前的Z坐标点
			float z = step.getZ();
			long currTime = System.currentTimeMillis();
			// 判断是否可以通行
			if(!gameMap.getHelper().isValidatePoint(x, y, z)){
				long inWalkAreaTime = System.currentTimeMillis() - currTime;
				if(inWalkAreaTime > 300 && logger.isWarnEnabled()){
					logger.warn("moving (x=" + x + ",y=" + y + ",z=" + z + ")" + " inWalkAreaTime=" + inWalkAreaTime);
				}
				// 停止移动
				stopMove(aiInfo);
				return;
			}
			long inWalkAreaTime = System.currentTimeMillis() - currTime;
			if(inWalkAreaTime > 300 && logger.isWarnEnabled()){
				logger.warn("moving (x=" + x + ",y=" + y + ",z=" + z + ")" + " inWalkAreaTime=" + inWalkAreaTime);
			}
			// 变动gameMap中存在该对象的位置等信息
			changePosInGameMap(gameMap, aiInfo, x, y, z);
			// 处理该对象移动对周边玩家的影响
			processRoundEffect(aiInfo, gameMap, path);
		}
		else {
			stopMove(aiInfo);
		}

	}
	
	/**
	 * 处理该对象移动对周边玩家的影响
	 * @param aiInfo
	 * @param gameMap
	 * @param path
	 * @author wangxf
	 * @date 2012-8-7
	 */
	private void processRoundEffect(AIInfo aiInfo, GameMap3D gameMap, Path3D path) {
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		
		ViewChangeInfo viewChangeInfo = gameMap.checkAreaChange(aiInfo, refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		
				
		// 附近新出现的玩家
		List<Long> addRoleIds = viewChangeInfo.getAddRoleIds();
		// 附近消失的玩家
		List<Long> delRoleIds = viewChangeInfo.getDelRoleIds();
		// 附近新出现的NPC
		List<Long> addNPCIds = viewChangeInfo.getAddNPCIds();
		// 附近消失的NPC
		List<Long> delNPCIds = viewChangeInfo.getDelNPCIds();
		// 附近新出现的掉落包
		List<Long> addDropBagIds = viewChangeInfo.getAddDropBagIds();
		// 附近消失的掉落包
		List<Long> delDropBagIds = viewChangeInfo.getDelDropBagIds();
		
		// 如果是玩家移动
		if (aiInfo instanceof RoleInfo) {
			 
			if(addRoleIds.size() > 0 || delRoleIds.size() > 0 || 
					addNPCIds.size() > 0 || delNPCIds.size() > 0 || 
					addDropBagIds.size() > 0 || delDropBagIds.size() > 0) {
				SceneService.sendAreaChangeMsg((RoleInfo)aiInfo, viewChangeInfo);
			}
			
			// 设置玩家对象附近新的npc集合
			aiInfo.setNpcIds(viewChangeInfo.getNewNpcIds());
			// 设置玩家对象附近新的掉落包集合
			aiInfo.setDropBagIds(viewChangeInfo.getNewDropBagIds());
			
			if (addRoleIds.size() > 0) {
				// 2、给附近新出现的玩家发送增加自己
				for (long roleId : addRoleIds) {
					RoleInfo otherRoleInfo = RoleInfoMap.getRoleInfo(roleId);
					if (otherRoleInfo != null) {
						SceneService.sendAddRoleInfoMsg(otherRoleInfo, (RoleInfo)aiInfo, path);
						otherRoleInfo.addOtherRoleId(aiInfo.getId());
					}
				}
			}
			
			if (delRoleIds.size() > 0) {
				// 4、给附近消失的玩家发送删除自己
				for (long roleId : delRoleIds) {
					RoleInfo otherRoleInfo = RoleInfoMap.getRoleInfo(roleId);
					if (otherRoleInfo != null) {
						SceneService.sendRemoveRoleInfoMsg(otherRoleInfo, aiInfo.getId());
						otherRoleInfo.getOtherRoleIds().remove(aiInfo.getId());
					}
				}
			}
			
		}
		// 如果是npc的移动
		else if (aiInfo instanceof NPCInfo) {
			// 1、给附近新出现的玩家增加自己
			if (addRoleIds.size() > 0) {
				for (long roleId : addRoleIds) {
					RoleInfo otherRoleInfo = RoleInfoMap.getRoleInfo(roleId);
					if (otherRoleInfo != null) {
						SceneService.sendAddNPCInfoMsg(otherRoleInfo, (NPCInfo)aiInfo, path);
						otherRoleInfo.addNpcId(aiInfo.getId());
					}
				}
			}
			// 2、给附近消失的玩家发送删除自己
			if (delRoleIds.size() > 0) {
				for (long roleId : delRoleIds) {
					RoleInfo otherRoleInfo = RoleInfoMap.getRoleInfo(roleId);
					if (otherRoleInfo != null) {
						SceneService.sendDelNPCInfoMsg(otherRoleInfo, aiInfo.getId());
						otherRoleInfo.getNpcIds().remove(aiInfo.getId());
					}
				}
			}
		}

		aiInfo.setOtherRoleIds(viewChangeInfo.getNewRoleIds());
	}

	/**
	 * 变动该对象在gameMap中缓存的位置等信息
	 * @param gameMap
	 * @param aiInfo
	 * @param x
	 * @param z
	 * @author wangxf
	 * @param y 
	 * @date 2012-8-6
	 */
	private void changePosInGameMap(GameMap3D gameMap, AIInfo aiInfo, float x,
			float y, float z) {
		// 删除当前坐标点对应的gameMap中玩家集合中的该玩家
		if(aiInfo instanceof RoleInfo) {
			gameMap.delRole((int) aiInfo.getCurrX(), (int) aiInfo.getCurrY(), (int) aiInfo.getCurrZ(), aiInfo.getId());
		}
		// 删除当前坐标点对应的gameMap中NPC集合中的该NPC
		else if (aiInfo instanceof NPCInfo) {
			/*System.out.println(Thread.currentThread().getName() + 
					"=====SceneCalculate.changePosInGameMap===(" + (int) aiInfo.getCurrX() + 
					"," + (int) aiInfo.getCurrY() + "," + (int) aiInfo.getCurrZ() + ")删除");*/
			gameMap.delNPC((int) aiInfo.getCurrX(), (int) aiInfo.getCurrY(), (int) aiInfo.getCurrZ(), aiInfo.getId());
		}
			
		// 增加当前坐标点对应的gameMap中玩家集合中的该玩家
		if(aiInfo instanceof RoleInfo) {
			gameMap.setRole((int) x, (int) y, (int) z, aiInfo.getId());
		}
		// 增加当前坐标点对应的gameMap中NPC集合中的该NPC
		else if (aiInfo instanceof NPCInfo) {
			/*System.out.println(Thread.currentThread().getName() + 
					"=====SceneCalculate.changePosInGameMap===(" + (int) x + 
					"," + (int) y + "," + (int) z + ")增加");*/
			gameMap.setNPC((int) x, (int) y, (int) z, aiInfo.getId());
		}
			
		aiInfo.setCurrX(x);
		aiInfo.setCurrY(y);
		aiInfo.setCurrZ(z);
		
	}

	/**
	 * 停止移动
	 * @param aiInfo
	 * @author wangxf
	 * @date 2012-8-6
	 */
	private void stopMove(AIInfo aiInfo) {
		aiInfo.resetAimPoint();
		aiInfo.setAction("0");
		aiInfo.setStatus(0);// 待机
		aiInfo.setPath(null);
		aiInfo.setCurrMoveTime(0);
		aiInfo.setAttackAim(-1);
		aiInfo.setAttackAimType(-1);
		SceneService.sendMoveEndMsg(aiInfo);
		
	}

	/**
	 * 得到目前移动后所在的坐标点
	 * @param aiInfo
	 * @param path
	 * @return
	 * @author wangxf
	 * @date 2012-8-6
	 */
	private Step3D getCurrPoint(AIInfo aiInfo, Path3D path) {
		Step3D step = new Step3D();
		
		Step3D step1 = path.getSteps().get(1);
		// 移动距离
		float moveSpace = (float) aiInfo.getCurrMoveTime()
				/ aiInfo.getCurrMoveMaxTime();
		// 此段路径剩余距离
		float spareSpace = SceneService.getLine(
				new Step3D(aiInfo.getCurrX(), aiInfo.getCurrY(), aiInfo.getCurrZ()), step1);

		float x = aiInfo.getCurrX();
		float y = aiInfo.getCurrY();
		float z = aiInfo.getCurrZ();
		// 如果此次移动距离超过0-1路径段，则根据下一路径段计算移动距离,并移除step0
		if (moveSpace > spareSpace) {
			//剩余还需要移动的距离
			float space = moveSpace - spareSpace;
			do{
				//如果路径<=2，说明已经到达终点
				if(path.getSteps().size()<=2){
					x = step1.getX();
					y = step1.getY();
					z = step1.getZ();
					break;
				}
				step1 = path.getSteps().get(1);
				Step3D step2 = path.getSteps().get(2);
				float newSpareSpace = SceneService.getLine(step1, step2);
				//如果step1至step2的距离，刚好等于剩余距离，则移动至step2
				if(newSpareSpace == space){
					x = step2.getX();
					y = step2.getY();
					z = step2.getZ();
					path.getSteps().remove(0);
					break;
				}
				//如果step1至step2的距离大于剩余距离，则以step1,step2方向移动剩余距离
				if(newSpareSpace > space){
					x = space / newSpareSpace * (step2.getX() - step1.getX()) + step1.getX();
					y = space / newSpareSpace * (step2.getY() - step1.getY()) + step1.getY();
					z = space / newSpareSpace * (step2.getZ() - step1.getZ()) + step1.getZ();
					path.getSteps().remove(0);
					break;
				}
				
				//如果剩余距离大于step1至step2的距离，则从step2开始重新计算路线距离
				space = space - newSpareSpace;
				path.getSteps().remove(0);
			}
			while(path.getSteps().size() >= 2);
		}
		else if (moveSpace < spareSpace) {
			x = moveSpace / spareSpace
					* (step1.getX() - aiInfo.getCurrX()) + aiInfo.getCurrX();
			y = moveSpace / spareSpace
					* (step1.getY() - aiInfo.getCurrY()) + aiInfo.getCurrY();
			z = moveSpace / spareSpace
					* (step1.getZ() - aiInfo.getCurrZ()) + aiInfo.getCurrZ();
		}
		else {
			x = step1.getX();
			y = step1.getY();
			z = step1.getZ();
		}

		step.setX(x);
		step.setY(y);
		step.setZ(z);
		
		return step;
	}


	/**
	 * 获得指定的攻击目标， 如果攻击目标不存在返回0 ，目标在射程内返回1， 目标不在射程内返回2
	 * 
	 * @return
	 */
	public int judeAppointAim(AIInfo aiInfo) {
		AIInfo attackAim = null;
		if (aiInfo.getAttackAimType() == 0)// NPC
		{
			attackAim = NPCInfoMap.getNPCInfo(aiInfo.getMapId(), aiInfo.getAttackAim());
		}
		else if (aiInfo.getAttackAimType() == 1)// 玩家
		{
			attackAim = RoleInfoMap.getRoleInfo(aiInfo.getAttackAim());
		}

		if (attackAim != null) {

			int maxAttackGrid = aiInfo.getCurrMaxAttackGrid();

			float d = SceneService.getLine(new Step3D(aiInfo.getCurrX(),
					aiInfo.getCurrY(), aiInfo.getCurrZ()),  new Step3D(attackAim.getCurrX(),
					attackAim.getCurrY(), attackAim.getCurrZ()));
			if (d <= maxAttackGrid) {
				return 1;
			}
			return 2;
		}

		return 0;

	}

	/**
	 * 使用技能攻击
	 */
	public void phalanxReleaseSkill(AIInfo attack, SkillAttackEvent event) {}
}
