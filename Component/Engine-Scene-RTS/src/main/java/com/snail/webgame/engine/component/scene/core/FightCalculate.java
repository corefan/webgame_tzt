package com.snail.webgame.engine.component.scene.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.critterai.math.Vector2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.info.BuffInfo;
import com.snail.webgame.engine.common.pathfinding.astar.GameMap;
import com.snail.webgame.engine.common.pathfinding.astar.Mover;
import com.snail.webgame.engine.common.pathfinding.astar.Path;
import com.snail.webgame.engine.common.pathfinding.astar.Step;
import com.snail.webgame.engine.common.pathfinding.astar.UnitMover;
import com.snail.webgame.engine.common.util.CommUtil;
import com.snail.webgame.engine.component.scene.cache.FightDataCache;
import com.snail.webgame.engine.component.scene.common.ArmyInfo;
import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.DefendInfo;
import com.snail.webgame.engine.component.scene.common.RoleFight;
import com.snail.webgame.engine.component.scene.core.policy.MoveAttackPolicyListener;
import com.snail.webgame.engine.component.scene.core.policy.MovePolicyListener;
import com.snail.webgame.engine.component.scene.core.policy.Policy;
import com.snail.webgame.engine.component.scene.core.policy.PolicyListener;
import com.snail.webgame.engine.component.scene.core.policy.PursueAttackPolicyListener;
import com.snail.webgame.engine.component.scene.core.policy.PursueBackAttackPolicyListener;
import com.snail.webgame.engine.component.scene.core.policy.StopAttackPolicyListener;

public class FightCalculate {

	protected final Logger logger = LoggerFactory.getLogger("logs");
	protected IFightCore fightCore;
	protected IHeroSkillService heroSkillService;
	protected INpcAIService npcAIService;
	protected HashMap<Integer, PolicyListener> policyMap = new HashMap<Integer, PolicyListener>();
	/**
	 * 人物是否阻挡
	 */
	protected boolean roleBlock = false;

	public FightCalculate(IFightCore fightCore, IHeroSkillService heroSkillService, INpcAIService npcAIService) {
		this.fightCore = fightCore;
		this.heroSkillService = heroSkillService;
		this.npcAIService = npcAIService;
		addPolicyListener(Policy.STOP_AUTO_ATTACK, new StopAttackPolicyListener());
		addPolicyListener(Policy.MOVE, new MovePolicyListener());
		addPolicyListener(Policy.MOVE_AUTO_ATTACK, new MoveAttackPolicyListener());
		addPolicyListener(Policy.PURSUE_AUTO_ATTACK, new PursueAttackPolicyListener());
		addPolicyListener(Policy.PURSUE_BACK_AUTO_ATTACK, new PursueBackAttackPolicyListener());
		
	}
	public FightCalculate(IFightCore fightCore, IHeroSkillService heroSkillService, INpcAIService npcAIService, boolean roleBlock) {
		this.fightCore = fightCore;
		this.heroSkillService = heroSkillService;
		this.npcAIService = npcAIService;
		this.roleBlock = roleBlock;
		addPolicyListener(Policy.STOP_AUTO_ATTACK, new StopAttackPolicyListener());
		addPolicyListener(Policy.MOVE, new MovePolicyListener());
		addPolicyListener(Policy.MOVE_AUTO_ATTACK, new MoveAttackPolicyListener());
		addPolicyListener(Policy.PURSUE_AUTO_ATTACK, new PursueAttackPolicyListener());
		addPolicyListener(Policy.PURSUE_BACK_AUTO_ATTACK, new PursueBackAttackPolicyListener());
		
	}
	
	/**
	 * 添加策略事件
	 * @param policy 
	 * @param listener
	 */
	public void addPolicyListener(int policy, PolicyListener listener){
		this.policyMap.put(policy, listener);
	}
	/**
	 * 移除策略事件
	 * @param policy
	 */
	public void removePolicyListener(int policy){
		this.policyMap.remove(policy);
	}

	public IFightCore getFightCore() {
		return fightCore;
	}

	public IHeroSkillService getHeroSkillService() {
		return heroSkillService;
	}

	/**
	 * 计算战斗
	 */
	public int calculateFight(RoleFight roleFight, ArmyPhalanx armyPhalanx, ArmyPhalanx[] armyPhalanxList,
			GameMap gameMap, int errorTime) {
		long fightId = roleFight.getFightId();
		ArrayList<Integer> roleList = roleFight.getRoleList();

		int side = -1;

		heroSkillService.checkPhalanxBuff(fightId, roleList, armyPhalanx, errorTime);

		boolean bool = true;
		List<BuffInfo> list = armyPhalanx.getBuffList();

		if (list != null && list.size() > 0) {
			for (int k = 0; k < list.size(); k++) {
				BuffInfo buff = list.get(k);
				if (buff != null && buff.getType() == 1) {
					bool = false;
					break;
				}
			}

		}
		
		if (bool) {
			mgtPhalanxPolicy(roleFight, armyPhalanx, armyPhalanxList, gameMap, errorTime);
		}

		side = mgtPhalanxActionResult(roleFight, armyPhalanx, armyPhalanxList, gameMap, errorTime);
		return side;
	}

	/**
	 * 处理方阵的各种行为 如移动 攻击 停止 转向 等
	 * 
	 * @param roleFight
	 * @param armyPhalanx
	 * @param armyPhalanxList
	 * @param gameMap
	 */
	protected void mgtPhalanxPolicy(RoleFight roleFight, ArmyPhalanx armyPhalanx, ArmyPhalanx[] armyPhalanxList,
			GameMap gameMap, int errorTime) {
		long fightId = roleFight.getFightId();
		ArrayList<Integer> roleList = roleFight.getRoleList();

		if (armyPhalanx.getCurrMoveMaxTime() > 0) {
//			if (armyPhalanx.getCurrMoveTime() < 1) {
				armyPhalanx.setCurrMoveTime(errorTime);//armyPhalanx.getCurrMoveTime() + errorTime);
//			}
		}
		if (armyPhalanx.getAttackMaxTime() > 0) {
			if (armyPhalanx.getCurrAttackTime() < armyPhalanx.getAttackMaxTime()) {
				armyPhalanx.setCurrAttackTime(armyPhalanx.getCurrAttackTime() + errorTime);
			}
		}
		
		PolicyListener listener = this.policyMap.get(armyPhalanx.getPolicy());
		if(listener != null){
			listener.execute(roleFight, armyPhalanx, armyPhalanxList, errorTime);
		}

	}

	/**
	 * 寻找攻击目标
	 * 
	 * @param armyPhalanx
	 * @param armyPhalanxList
	 * @param roleFight
	 */
	public void searchAimPhalanx(ArmyPhalanx armyPhalanx, ArmyPhalanx[] armyPhalanxList, RoleFight roleFight) {
		if (roleFight.getControlMap().get(armyPhalanx.getRoleId()) == 0) {
			// 获取最高仇恨方阵
			String aimPhalanxId = npcAIService.getMaxHatredPhalanxId(armyPhalanx.getHatredList(), armyPhalanxList);
			if (aimPhalanxId == null) {
				aimPhalanxId = restPhalanxAim(roleFight, armyPhalanx, armyPhalanxList, roleFight.getMap());
			}
			if (aimPhalanxId != null) {
				armyPhalanx.setAimPhalanxId(aimPhalanxId);
			}
		}
		else {
			String aimPhalanxId = getKenGridAim(armyPhalanx, armyPhalanxList, roleFight.getMap());
			if (aimPhalanxId != null) {
				armyPhalanx.setAimPhalanxId(aimPhalanxId);
			}
		}
	}

	/**
	 * 处理方阵遭受攻击
	 * 
	 * @param roleFight
	 * @param armyPhalanx
	 * @param armyPhalanxList
	 * @param gameMap
	 * @return
	 */
	protected int mgtPhalanxActionResult(RoleFight roleFight, ArmyPhalanx armyPhalanx, ArmyPhalanx[] armyPhalanxList,
			GameMap gameMap, int errorTime) {
		long fightId = roleFight.getFightId();
		ArrayList<Integer> roleList = roleFight.getRoleList();
		// 遭受攻击
		List<DefendInfo> defendList = armyPhalanx.getDefendList();
		for (int p = 0; p < defendList.size(); p++) {

			if (armyPhalanx.getItemNum() == 0 || armyPhalanx.getCurrHP() == 0) {
				break;
			}

			DefendInfo defendInfo = defendList.get(p);
			if (defendInfo.getTime() <= 0) {
				// 仇恨累积计算
				npcAIService.accumulateHatredValue(armyPhalanx, defendInfo);
				if(defendInfo.getBuff() != null)		
				{
					
					heroSkillService.addPhalanxBuff(fightId, roleList, armyPhalanx, defendInfo.getBuff());
					
				 
				}
				
					// 触发被动buff
					heroSkillService.addArmyPhalanxsBeBuff(roleFight, armyPhalanx, armyPhalanxList, null, 2, defendInfo);

					long lossHP = defendInfo.getAimLossHP();
					long lossMP = defendInfo.getAimLossMP();
					int aimLoss = 0;
					double defendCurrHP = armyPhalanx.getCurrHP();
					long defendCurrMP = armyPhalanx.getCurrMP();
					int defendItemNum = armyPhalanx.getItemNum();

					boolean invincibleFlag = false;// 是否无敌
					List<BuffInfo> defendBuffList = armyPhalanx.getBuffList();
					if (defendBuffList != null && defendBuffList.size() > 0) {
						for (int i = 0; i < defendBuffList.size(); i++) {
							BuffInfo buff = defendBuffList.get(i);
							if (buff != null && buff.getType() == 5) {
								invincibleFlag = true;
								break;
							}
						}
					}

					// 无敌状态
					if (invincibleFlag && armyPhalanx.getCurrHP() <= lossHP) {
						lossHP = (long) Math.ceil(armyPhalanx.getCurrHP() - 1);
					}

					if (armyPhalanx.getCurrHP() > lossHP) {
						defendCurrHP = armyPhalanx.getCurrHP() - lossHP;

						if (defendCurrHP > armyPhalanx.getMaxHP()) {
							defendCurrHP = armyPhalanx.getMaxHP();
						}

						if (defendCurrHP % armyPhalanx.getEveHP() == 0) {
							defendItemNum = (int) (defendCurrHP / armyPhalanx.getEveHP());
						}
						else {
							defendItemNum = (int) (defendCurrHP / armyPhalanx.getEveHP()) + 1;
						}

						aimLoss = armyPhalanx.getItemNum() - defendItemNum;

					}
					else {
						aimLoss = armyPhalanx.getItemNum();
						defendCurrHP = 0;
						defendItemNum = 0;
					}

					if (armyPhalanx.getCurrMP() > lossMP) {
						defendCurrMP = armyPhalanx.getCurrMP() - lossMP;

						if (defendCurrMP > armyPhalanx.getMaxMP()) {
							defendCurrMP = armyPhalanx.getMaxMP();
						}
					}
					else {
						defendCurrMP = 0;
					}

					armyPhalanx.setCurrHP(defendCurrHP);
					armyPhalanx.setCurrMP(defendCurrMP);
					armyPhalanx.setItemNum(defendItemNum);
					String defendSlogan = fightCore.getSlogan(armyPhalanx, 2);

					FightCmdSend.sendDamageCmd(fightId, roleList, defendInfo.getAttackPhalanxId(), aimLoss, lossHP,
							armyPhalanx, defendSlogan, defendInfo.getFace(), defendInfo.getFlag());

					ArmyPhalanx attackPhalanx = null;// 进攻方阵

					for (int q = 0; q < armyPhalanxList.length; q++) {
						if (armyPhalanxList[q] != null
								&& armyPhalanxList[q].getPhalanxId().equals(defendInfo.getAttackPhalanxId())) {
							attackPhalanx = armyPhalanxList[q];

							int roleId = armyPhalanxList[q].getRoleId();
							int itemNo = armyPhalanx.getItemNo();
							int itemNum = aimLoss;
							if (roleId > 0) {

								ArmyInfo armyInfos[] = roleFight.getArmyInfo();

								ArmyInfo armyInfo = null;
								for (int n = 0; n < armyInfos.length; n++) {
									if (armyInfos[n].getArmyId().equals(armyPhalanxList[q].getArmyId())) {
										armyInfo = armyInfos[n];
										break;
									}
								}

								armyInfo.getItemMap().addItemLoss(roleId, itemNo, itemNum);
							}

							// 士气变化
							fightCore.changeFightMorale(roleFight, armyPhalanx, armyPhalanxList[q],
									defendInfo.getFace(), armyPhalanxList, roleList);

							// 反弹伤害
							if (defendInfo.getType() != 3 && lossHP > 0) {
								double buffVal2 = 0;
								double buffVal23 = 0;
								List<BuffInfo> buffList = armyPhalanx.getBuffList();
								if (buffList != null) {
									for (int m = 0; m < buffList.size(); m++) {
										BuffInfo buff = buffList.get(m);
										if (buff != null) {
											if (buff.getType() == 2)// 反伤
											{
												buffVal2 = buffVal2 + buff.getValue();
											}
											else if (buff.getType() == 23)// 减反伤
											{
												buffVal23 = buffVal23 + buff.getValue();
											}
										}
									}

									if (buffVal2 > 0) {
										double aimLossHP = lossHP * buffVal2 * (1 + buffVal23);
										if (aimLossHP < 1) {
											aimLossHP = 1;
										}

										DefendInfo dInfo = new DefendInfo();
										dInfo.setAimLossHP((long) aimLossHP);
										dInfo.setAttackPhalanxId(armyPhalanx.getPhalanxId());
										dInfo.setDefendPhalanxId(armyPhalanxList[q].getPhalanxId());
										dInfo.setTime(0);
										dInfo.setType(3);
										armyPhalanxList[q].getDefendList().add(dInfo);
									}
								}
							}

							break;
						}
					}

					if (armyPhalanx.getItemNum() == 0 || armyPhalanx.getCurrHP() == 0) {
						// 死亡时触发技能效果
						heroSkillService.triggerEffectOfDead(roleFight, attackPhalanx, armyPhalanx, armyPhalanxList,
								defendInfo);
					}
					else {
						// 产生混乱
						// FightCore.chaosPhalanx(fightId,armyPhalanx,roleList);
					}
//				}

				if (armyPhalanx.getPolicy() == Policy.PURSUE_AUTO_ATTACK && armyPhalanx.getAimPhalanxId() == null) {
					// 被打了 追击
					armyPhalanx.setAimPhalanxId(defendInfo.getAttackPhalanxId());
					armyPhalanx.setPolicy(Policy.MOVE_AUTO_ATTACK);
					// 重新根据仇恨值设置攻击目标
					String aimPhalanxId = npcAIService.getMaxHatredPhalanxId(armyPhalanx.getHatredList(),
							armyPhalanxList);
					if (aimPhalanxId != null) {
						armyPhalanx.setPolicy(Policy.PURSUE_AUTO_ATTACK);
						armyPhalanx.setAimPhalanxId(aimPhalanxId);
					}
				}

				defendList.remove(p);
				p--;

			}
			else {
				defendInfo.setTime(defendInfo.getTime() - errorTime);
			}

		}

		if (armyPhalanx.getItemNum() == 0 || armyPhalanx.getCurrHP() == 0) {
			
			FightCmdSend.sendDelPhalanxCmd(fightId, roleList, armyPhalanx.getPhalanxId());
			if (armyPhalanx.getItemSort() == 3) {
				List<Point> pointList1 = armyPhalanx.getBuildPointList();

				for (int p = 0; p < pointList1.size(); p++) {
					// 此建筑物所占的格子
					boolean flag = true;
					int x1 = pointList1.get(p).x;
					int y1 = pointList1.get(p).y;

					for (int l = 0; l < armyPhalanxList.length; l++) {
						if (armyPhalanxList[l] == null) {
							continue;
						}

						if (armyPhalanxList[l].getItemSort() == 3) {
							if (!armyPhalanxList[l].getPhalanxId().equals(armyPhalanx.getPhalanxId())) {
								List<Point> pointList2 = armyPhalanxList[l].getBuildPointList();

								for (int q = 0; q < pointList2.size(); q++) {
									int x2 = pointList2.get(q).x;
									int y2 = pointList2.get(q).y;

									if (x1 == x2 && y1 == y2) {
										flag = false;
									}
								}
							}

						}
					}
					if (flag) {
						// 释放格子

						gameMap.setUnit(x1, y1, null);
					}
				}
			}
			else {
				// 释放格子
				gameMap.setUnit(armyPhalanx.getCurrX(), armyPhalanx.getCurrY(), null, armyPhalanx.getGridNum());
				clearPhalanxPoint(armyPhalanx);
			}
			int side = armyPhalanx.getSide();
			armyPhalanx = null;

			return side;
		}

		return -1;
	}


	/**
	 * 执行部队移动
	 */
	public void move(long fightId, GameMap gameMap, ArmyPhalanx armyPhalanx, List<Integer> roleList, Path path,
			int control, int moveType) {
		if (path != null) {

			// 像素坐标
			Step step1 = getCurrPoint(armyPhalanx, path, true);// path.getSmoothPaths().get(1);

			// 格子坐标
			Point directPoint = gameMap.worldToGrid(step1.getX(), step1.getY());// CommUtil.StageToDirect(step1.getX(),
																				// step1.getY(),
																				// gameMap.getMapLow(),
																				// gameMap.getGridTitleX(),
																				// gameMap.getGridTitleY());

			String str = fightCore.getSlogan(armyPhalanx, 0);

			if (armyPhalanx.isMoveSendFlag()) {
				// 进行移动，发送消息给客户端
				FightCmdSend.sendRoleMoveCmd(fightId, roleList, directPoint.x, directPoint.y, armyPhalanx, str,
						moveType);
			}

			if (gameMap.blocked(new UnitMover(gameMap.getUnit(armyPhalanx.getCurrX(), armyPhalanx.getCurrY()),
					armyPhalanx.getGridNum(), false), directPoint.x, directPoint.y)
					&& (step1.getX() % gameMap.getGridTitleX() == 0 && step1.getY() % gameMap.getGridTitleY() == 0)) {

				// 走到顶点上了

				System.out
						.println("-----move------ 走在格子的顶点上了----" + armyPhalanx.getPhalanxId() + ", 世界坐标："
								+ step1.getX() + "," + step1.getY() + ", 格子坐标" + directPoint.getX() + ", "
								+ directPoint.getY());
			}
			else {
				if(roleBlock){
					gameMap.setUnit(armyPhalanx.getCurrX(), armyPhalanx.getCurrY(), null, armyPhalanx.getGridNum());
					gameMap.setUnit(directPoint.x, directPoint.y, armyPhalanx.getPhalanxId(), armyPhalanx.getGridNum());
				}
				resetPhalanxPoint(directPoint.x, directPoint.y, armyPhalanx, gameMap);

				armyPhalanx.setBeforeX(armyPhalanx.getCurrX());
				armyPhalanx.setBeforeY(armyPhalanx.getCurrY());

				armyPhalanx.setCurrX(directPoint.x);
				armyPhalanx.setCurrY(directPoint.y);

			}

			armyPhalanx.setCurrStageX(step1.getX());
			armyPhalanx.setCurrStageY(step1.getY());

			armyPhalanx.setStatus(2);// 移动中

		}
		else {
			int status = armyPhalanx.getStatus();
			// 移动中，暂时等待，并没有真正停止
			if (status == 3) {

			}
			else {

				if(armyPhalanx.getPolicy() == Policy.MOVE){
					if (control == 0) {
						armyPhalanx.setCurrMoveTime(armyPhalanx.getCurrMoveMaxTime());
						armyPhalanx.setPolicy(Policy.MOVE_AUTO_ATTACK);
						
					}
					else {
						armyPhalanx.setPolicy(Policy.PURSUE_AUTO_ATTACK);
					}
				}
				armyPhalanx.setAimX(-1);
				armyPhalanx.setAimY(-1);
				armyPhalanx.setStatus(0);
				armyPhalanx.setAimPhalanxId(null);
				setArmyPhlanxStagePoint(armyPhalanx, fightId);
			}
			if(status == 2 || status == 3){
				// 发送停止指令
				FightCmdSend.sendRoleStopCmd(fightId, roleList, armyPhalanx);
			}
		}

	}


	/**
	 * 获得移动路径
	 * 
	 * @return
	 */
	public Path getPhalanxPath(GameMap map, int srcX, int srcY, int endX, int endY, Mover mover, String fightMapType) {
		long time = System.currentTimeMillis();
		Path path = null;
		// 如果两点之间存在障碍物才用A星寻路
		if (map.hasBarrier(srcX, srcY, endX, endY, mover)) {
			path = map.getFinder().findPath(mover, srcX, srcY, endX, endY);
			floyd(map, path, mover);
		}
		else {
			path = new Path();
			path.appendStep(srcX, srcY);
			path.appendStep(endX, endY);
			path.setSmoothPaths(path.getSteps());
		}
		if (path == null) {
			System.out.println("-------寻不到路：" + ((UnitMover) mover).getType() + ", (" + srcX + "," + srcY + "), ("
					+ endX + "," + endY + ")");
			return null;
		}
		List<Step> smoothPath = path.getSmoothPaths();
		for (int i = 0; i < smoothPath.size(); i++) {
			Step step = smoothPath.get(i);
			Step point = map.gridToWorld(step.getX(), step.getY());// CommUtil.DirectToStageCenter(step.getX(),
																	// step.getY(),
																	// map.getMapLow(),
																	// map.getGridTitleX(),
																	// map.getGridTitleY());
			smoothPath.set(i, point);
		}
		long time1 = System.currentTimeMillis();

		if (time1 - time > 100) {
			logger.error("A* cal exception:scrX=" + srcX + ",srcY=" + srcY + ",endX=" + endX + ",endY=" + endY
					+ ",time=" + (time1 - time) + ",mapType=" + fightMapType + ", phalanxId: " + ((UnitMover) mover).getType());
		}
		return path;
	}


	/**
	 * 得到目前移动后所在的坐标点
	 * 
	 * @param aiInfo
	 * @param path
	 * @return
	 */
	protected Step getCurrPoint(ArmyPhalanx armyPhalanx, Path path, boolean updatePath) {
		Step step1 = path.getSmoothPaths().get(1);
		// 移动距离
		float moveSpace = (float) armyPhalanx.getCurrMoveTime() / armyPhalanx.getCurrMoveMaxTime();
		// 此段路径剩余距离
		float spareSpace = CommUtil.getDistance(new Step(armyPhalanx.getCurrStageX(), armyPhalanx.getCurrStageY()), step1);

		float x = armyPhalanx.getCurrStageX();
		float y = armyPhalanx.getCurrStageY();

		// 如果此次移动距离超过0-1路径段，则根据下一路径段计算移动距离,并移除step0
		if (moveSpace > spareSpace) {
			// 剩余还需要移动的距离
			float space = moveSpace - spareSpace;
			do {
				// 如果路径<=2，说明已经到达终点
				if (path.getSmoothPaths().size() <= 2) {
					x = step1.getX();
					y = step1.getY();
					break;
				}
				step1 = path.getSmoothPaths().get(1);
				Step step2 = path.getSmoothPaths().get(2);
				float newSpareSpace = CommUtil.getDistance(step1, step2);
				// 如果step1至step2的距离，刚好等于剩余距离，则移动至step2
				if (newSpareSpace == space) {
					x = step2.getX();
					y = step2.getY();
					if (updatePath) {
						path.getSmoothPaths().remove(0);
					}
					break;
				}
				// 如果step1至step2的距离大于剩余距离，则以step1,step2方向移动剩余距离
				if (newSpareSpace > space) {
					x = space / newSpareSpace * (step2.getX() - step1.getX()) + step1.getX();
					y = space / newSpareSpace * (step2.getY() - step1.getY()) + step1.getY();

					if (updatePath) {
						path.getSmoothPaths().remove(0);
					}
					break;
				}

				// 如果剩余距离大于step1至step2的距离，则从step2开始重新计算路线距离
				space = space - newSpareSpace;
				if (updatePath) {
					path.getSmoothPaths().remove(0);
				}
			}
			while (path.getSmoothPaths().size() >= 2);
		}
		else if (moveSpace < spareSpace) {
			x = moveSpace / spareSpace * (step1.getX() - armyPhalanx.getCurrStageX()) + armyPhalanx.getCurrStageX();
			y = moveSpace / spareSpace * (step1.getY() - armyPhalanx.getCurrStageY()) + armyPhalanx.getCurrStageY();
		}
		else {
			x = step1.getX();
			y = step1.getY();
		}

		Step step = new Step(x, y);
		return step;
	}

	/**
	 * 获得移动目标的路径
	 * 
	 * @param armyPhalanx
	 * @param flag
	 *            true 强制寻路
	 * @return
	 */
	public Path getAimRankList(GameMap gameMap, ArmyPhalanx armyPhalanx, int currX, int currY, int moveX, int moveY,
			boolean flag, String fightMapType, long fightId) {

		RoleFight roleFight = FightDataCache.getRoleFight(fightId);
		if (roleFight == null) {
			armyPhalanx.setPath(null);
			return null;
		}
		armyPhalanx.setMoveSendFlag(true);
		Path path = armyPhalanx.getPath();
		if (currX == moveX && currY == moveY) {
			if (path != null && path.getSmoothPaths().size() > 1) {
				Step endStep = path.getSmoothPaths().get(path.getSmoothPaths().size() - 1);
				if (endStep != null && endStep.getX() == armyPhalanx.getCurrStageX()
						&& endStep.getY() == armyPhalanx.getCurrStageY()) {
					armyPhalanx.setPath(null);
					return null;// 到达目的地
				}
			}
			else{
				armyPhalanx.setPath(null);
				return null;
			}
		}
		else{
			if (path != null && path.getSmoothPaths().size() == 2) {
				Step endStep = path.getSmoothPaths().get(1);
				if(endStep.getX() == armyPhalanx.getCurrStageX() && endStep.getY() == armyPhalanx.getCurrStageY())
					path = null;
			}
		}
		if (!flag) {
			if (path != null) {

				if (path.getSmoothPaths().size() > 1) {

					Step step = getCurrPoint(armyPhalanx, path, false);// path.getSmoothPaths().get(2);
					Point directPoint = gameMap.worldToGrid(step.getX(), step.getY());// CommUtil.StageToDirect(step.getX(),
																						// step.getY(),
																						// gameMap.getMapLow(),
																						// gameMap.getGridTitleX(),
																						// gameMap.getGridTitleY());

					if (!gameMap.blocked(new UnitMover(gameMap.getUnit(armyPhalanx.getCurrX(), armyPhalanx.getCurrY()),
							armyPhalanx.getGridNum(), false), directPoint.x, directPoint.y)) {

						if (armyPhalanx.getStatus() != 3) {
							armyPhalanx.setMoveSendFlag(false);
						}
						return path;
					}

					else {
						if (step.getX() % gameMap.getGridTitleX() == 0 && step.getY() % gameMap.getGridTitleY() == 0) {
							System.out.println("---getpath-------- 走在格子的顶点上了----" + armyPhalanx.getPhalanxId()
									+ ", 世界坐标：" + step.getX() + "," + step.getY() + ", 格子坐标" + directPoint.getX()
									+ ", " + directPoint.getY());
							armyPhalanx.setMoveSendFlag(false);
							return path;
						}
						else {
							if (isWait(armyPhalanx, roleFight.getArmyPhalanxs(), directPoint.x, directPoint.y, gameMap)) {
								path.getSmoothPaths().get(0).setX(armyPhalanx.getCurrStageX());
								path.getSmoothPaths().get(0).setY(armyPhalanx.getCurrStageY());
								armyPhalanx.setStatus(3);// 移动过程中停止等待其它单位移动
								return null;
							}
						}
					}

				}

			}
		}
		Mover mover = new UnitMover(armyPhalanx.getPhalanxId(), armyPhalanx.getGridNum(), false);
		setArmyPhlanxStagePoint(armyPhalanx, gameMap);
		path = getPhalanxPath(gameMap, currX, currY, moveX, moveY, mover, fightMapType);
		// 寻路为空，如果寻路的终点正好被其它正在移动或等待移动的单位占领，则把自己设置为等待移动状态，等待目标移开，再进行寻路
		if (path == null) {
			List<String> units = gameMap.getUnit(moveX, moveY, armyPhalanx.getGridNum());
			List<Byte> terrains = gameMap.getTerrain(moveX, moveY, armyPhalanx.getGridNum());
			for (byte terrain : terrains) {
				if (terrain == 1 || terrain == 9) {
					armyPhalanx.setPath(path);
					return path;
				}
			}
			ArmyPhalanx[] armyPhalanxs = roleFight.getArmyPhalanxs();
			boolean bool = false;
			if (units != null && !units.isEmpty()) {
				if (armyPhalanxs != null && armyPhalanxs.length > 0) {
					for (ArmyPhalanx phalanx : armyPhalanxs) {
						if (phalanx == null)
							continue;
						if (units.contains(phalanx.getPhalanxId())
								&& !armyPhalanx.getPhalanxId().equals(phalanx.getPhalanxId())) {
							if (phalanx.getStatus() == 2 || phalanx.getStatus() == 3)
								bool = true;
						}
					}
				}
			}
			if (bool) {
				if (armyPhalanx.getPath() != null && armyPhalanx.getPath().getSmoothPaths().size() > 1) {
					armyPhalanx.getPath().getSmoothPaths().get(0).setX(armyPhalanx.getCurrStageX());
					armyPhalanx.getPath().getSmoothPaths().get(0).setY(armyPhalanx.getCurrStageY());
					armyPhalanx.setStatus(3);// 移动过程中停止等待其它单位移动
					return null;
				}
				else {
					armyPhalanx.setStatus(3);// 移动过程中停止等待其它单位移动
				}
			}
			else {
				if (armyPhalanx.getStatus() == 3) {
					armyPhalanx.setStatus(2);
				}
			}
		}
		armyPhalanx.setPath(path);
		return path;

	}

	/**
	 * 判断是否原地等待
	 * 
	 * @param armyPhalanx
	 * @param phalanxList
	 * @return
	 */
	protected boolean isWait(ArmyPhalanx armyPhalanx, ArmyPhalanx[] phalanxList, int x, int y, GameMap gameMap) {
		// 遇到阻挡是玩家或NPC等动态物,如果此动态物运动方向与我相反，
		// 则判断优先级，如果对方优先级别高，我停止移动，等待它移动开后再继续移动
		List<String> units = gameMap.getUnit(x, y, armyPhalanx.getGridNum());
		Path path = armyPhalanx.getPath();
		if (path == null)
			return false;
		boolean wait = false;
		for (String unit : units) {
			if (unit != null && !unit.equals(armyPhalanx.getPhalanxId())) {
				for (ArmyPhalanx phalanx : phalanxList) {
					if (phalanx == null)
						continue;
					if (phalanx.getPhalanxId().equals(unit)) {
						Path tmpPath = phalanx.getPath();
						if (tmpPath != null) {
							Step tmpStep = tmpPath.getSmoothPaths().get(1);
							Vector2 aimV = new Vector2(tmpStep.getX() - phalanx.getCurrStageX(), tmpStep.getY()
									- phalanx.getCurrStageY());
							Vector2 v = new Vector2(path.getSmoothPaths().get(1).getX() - armyPhalanx.getCurrStageX(),
									path.getSmoothPaths().get(1).getY() - armyPhalanx.getCurrStageY());

							// 反向
							if ((v.dot(aimV) < 0 && armyPhalanx.getMoveLevel() <= phalanx.getMoveLevel() && phalanx
									.getStatus() != 3)) {
								wait = true;
							}
						}
					}
				}
			}
		}
		return wait;
	}

	/**
	 * 获得方阵移动到的目的地
	 * 
	 * @param mapData
	 * @param attackPhalanx
	 * @param phalanxList
	 * @return
	 */
	public Path getAimXY(GameMap gameMap, ArmyPhalanx attackPhalanx, ArmyPhalanx[] phalanxList, String fightMapType) {
		for (int i = 0; i < phalanxList.length; i++) {
			if (phalanxList[i] == null) {
				continue;
			}

			if (phalanxList[i].getPhalanxId().equals(attackPhalanx.getAimPhalanxId())) {
				// 判断该建筑物是否能攻击到
				if (phalanxList[i].getItemSort() == 3)
				{
					if (attackPhalanx.getBoycottBuildingIds().length() > 0
							&& attackPhalanx.getBoycottBuildingIds().indexOf("" + phalanxList[i].getPhalanxId()) != -1) {
						return null;
					}
				}

				int currX = phalanxList[i].getCurrX();
				int currY = phalanxList[i].getCurrY();

				Path path = attackPhalanx.getPath();

				if (path != null && path.getSmoothPaths().size() > 1) {
					Step endStep = path.getSmoothPaths().get(path.getSmoothPaths().size() - 1);
					if (endStep != null && endStep.getX() == attackPhalanx.getCurrStageX()
							&& endStep.getY() == attackPhalanx.getCurrStageY()) {
						path = null;
					}
				}
				if (path != null) {
					ArrayList<Step> smoothList = path.getSmoothPaths();

					if (smoothList.size() > 1) {

						float x1 = attackPhalanx.getCurrStageX();
						float y1 = attackPhalanx.getCurrStageY();

						Step step1 = getCurrPoint(attackPhalanx, path, false);
						float x2 = step1.getX();
						float y2 = step1.getY();
						Point directPoint = gameMap.worldToGrid(x2, y2);// CommUtil.StageToDirect(x2,
																		// y2,
																		// gameMap.getMapLow(),
																		// gameMap.getGridTitleX(),
																		// gameMap.getGridTitleY());

						if (!gameMap.blocked(
								new UnitMover(gameMap.getUnit(attackPhalanx.getCurrX(), attackPhalanx.getCurrY()),
										attackPhalanx.getGridNum(), false), directPoint.x, directPoint.y)) {

							double distance1 = CommUtil.getDistance(x1, y1, phalanxList[i].getCurrStageX(), phalanxList[i].getCurrStageY());


							double distance2 = CommUtil.getDistance(x2, y2, phalanxList[i].getCurrStageX(), phalanxList[i].getCurrStageY());
							Step end = smoothList.get(smoothList.size() - 1);
							Point endPoint = gameMap.worldToGrid(end.getX(), end.getY());
							// 判断目前这条路线的终点是否还能攻击到目标
							int jude = judeAppointAim(attackPhalanx, endPoint.x, endPoint.y, phalanxList, gameMap);

							// 目前与当前路线的终点的距离
							float line = CommUtil.getDistance(new Step(attackPhalanx.getCurrX(), attackPhalanx.getCurrY()),
									new Step(endPoint.x, endPoint.y));
							boolean hasBarrier = gameMap.hasBarrier(directPoint.x, directPoint.y, phalanxList[i].getCurrX(), phalanxList[i].getCurrY(), new UnitMover(attackPhalanx.getPhalanxId(), attackPhalanx.getGridNum(), false));
							if ((distance2 <= distance1 && (line > 1 || jude == 1))
									|| (distance2 > distance1 && hasBarrier)) {
								if (attackPhalanx.getStatus() != 3) {
									attackPhalanx.setMoveSendFlag(false);
								}
								return path;
							}
						}
						else {
							if (step1.getX() % gameMap.getGridTitleX() == 0
									&& step1.getY() % gameMap.getGridTitleY() == 0) {
								attackPhalanx.setMoveSendFlag(false);
								return path;
							}
							else {
								if (isWait(attackPhalanx, phalanxList, directPoint.x, directPoint.y, gameMap)) {
									path.getSmoothPaths().get(0).setX(attackPhalanx.getCurrStageX());
									path.getSmoothPaths().get(0).setY(attackPhalanx.getCurrStageY());
									attackPhalanx.setStatus(3);// 移动过程中停止等待其它单位移动
									return null;
								}
							}
						}
					}

				}
				int maxAttackGrid = attackPhalanx.getCurrMaxAttackGrid();

				List<CompPoint> list = new ArrayList<CompPoint>();

				List<Point> buildPointList = phalanxList[i].getBuildPointList();

				for (int h = 0; h < buildPointList.size(); h++) {
					Point point = buildPointList.get(h);

					currX = point.x;
					currY = point.y;

					for (int k = -maxAttackGrid; k <= maxAttackGrid; k++) {
						double px = currX + k;

						if (px >= 0) {
							int length = maxAttackGrid;// 站在最大射程上

							int pyd = length - Math.abs(k);

							list.add(new CompPoint(new Point((int) px, (int) ((int) pyd + currY)), 0));

							if (currY - (int) pyd >= 0) {
								list.add(new CompPoint(new Point((int) px, (int) (currY) - (int) pyd), 0));
							}

						}
					}
				}

				for (int k = 0; k < list.size(); k++) {
					CompPoint compPoint = list.get(k);
					Point point1 = compPoint.getPoint();

					if (point1.x >= 0 && point1.x < gameMap.getWidthInTiles() && point1.y >= 0
							&& point1.y < gameMap.getHeightInTiles()) {

						if (gameMap.blocked(
								new UnitMover(gameMap.getUnit(attackPhalanx.getCurrX(), attackPhalanx.getCurrY()),
										attackPhalanx.getGridNum(), false), point1.x, point1.y)) {
							compPoint.setPoint(null);

							continue;
						}

						int px = (int) (attackPhalanx.getCurrX() - point1.getX());
						int py = (int) (attackPhalanx.getCurrY() - point1.getY());
						double distance = Math.abs(px) + Math.abs(py);

						compPoint.setDistance(distance);

					}
					else {
						compPoint.setPoint(null);
					}

				}
				PointComparator compar = new PointComparator();
				Collections.sort(list, compar);
				boolean tempFlag = false;
				HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();

				for (int j = 0; j < list.size(); j++) {
					CompPoint compPoint = list.get(j);
					if (compPoint.getPoint() != null
							&& !(temp.containsKey(compPoint.getPoint().x) && temp.get(compPoint.getPoint().x) == compPoint
									.getPoint().y)) {
						temp.put(compPoint.getPoint().x, compPoint.getPoint().y);

						Path path1 = getPhalanxPath(gameMap, attackPhalanx.getCurrX(), attackPhalanx.getCurrY(),
								compPoint.getPoint().x, compPoint.getPoint().y,
								new UnitMover(attackPhalanx.getPhalanxId(), attackPhalanx.getGridNum(), false),
								fightMapType);
						if (path1 != null) {
							setArmyPhlanxStagePoint(attackPhalanx, gameMap);
							attackPhalanx.setMoveSendFlag(true);
							attackPhalanx.setPath(path1);
							temp = null;
							return path1;
						}
						else {
							if (phalanxList[i].getItemSort() == 3)
							// ||
							// String.valueOf(phalanxList[i].getItemNo()).startsWith(GameValue.BUILD_KINGDOM_FLAG))
							{
								if (attackPhalanx.getBaseMapBuildingIds().length() > 0
										&& attackPhalanx.getBaseMapBuildingIds().indexOf(
												"" + phalanxList[i].getPhalanxId()) != -1) {

								}
								else {
									Path path2 = getPhalanxPath(gameMap, attackPhalanx.getCurrX(),
											attackPhalanx.getCurrY(), compPoint.getPoint().x, compPoint.getPoint().y,
											new UnitMover(attackPhalanx.getPhalanxId(), attackPhalanx.getGridNum(),
													true), fightMapType);
									if (path2 != null) {
										tempFlag = true;
										// 底图有路径
										attackPhalanx.setBaseMapBuildingIds(attackPhalanx.getBaseMapBuildingIds()
												+ phalanxList[i].getPhalanxId() + ",");
									}
								}
							}
						}
					}
				}
				temp = null;

				if (!tempFlag) {
					if (phalanxList[i].getItemSort() == 3)
					{
						attackPhalanx.setBoycottBuildingIds(attackPhalanx.getBoycottBuildingIds()
								+ phalanxList[i].getPhalanxId() + ",");
					}
				}

			}
		}

		return null;
	}

	/**
	 * 预判断是否可以攻击到 如果攻击目标不存在返回0 ，目标在射程内返回1， 目标不在射程内返回2
	 * 
	 * @return
	 */
	protected int judeAppointAim(ArmyPhalanx attackPhalanx, int currX, int currY, ArmyPhalanx[] phalanxList,
			GameMap gameMap) {

		for (int i = 0; i < phalanxList.length; i++) {

			if (phalanxList[i] == null) {
				continue;
			}
			if (attackPhalanx.getSide() == phalanxList[i].getSide() && attackPhalanx.getSkillType() == 0) {
				continue;
			}
			if (phalanxList[i].getIsShow() == 0) {
				if (phalanxList[i].getPhalanxId().equals(attackPhalanx.getAimPhalanxId())) {

					int minAttackGrid = attackPhalanx.getCurrMinAttackGrid();
					int maxAttackGrid = attackPhalanx.getCurrMaxAttackGrid();

					List<Point> buildPoint = phalanxList[i].getBuildPointList();

					for (int p = 0; p < buildPoint.size(); p++) {
						float ph = gameMap.getElevation(currX, currY);
						float d = CommUtil.getDistance(currX, currY, buildPoint.get(p).x, buildPoint.get(p).y);// Math.abs(px) + Math.abs(py);
						int k = 0;
						if (ph == 0 || maxAttackGrid == 1) {
							k = maxAttackGrid;
						}
						else {
							k = (int) Math.ceil(Math.sqrt(ph * ph + maxAttackGrid * maxAttackGrid));
						}

						if (d <= k && d >= minAttackGrid) {
							attackPhalanx.setAttackAimX(buildPoint.get(p).x);
							attackPhalanx.setAttackAimY(buildPoint.get(p).y);
							return 1;
						}

					}

					return 2;

				}
			}
		}
		return 0;

	}

	/**
	 * 获得指定的攻击目标， 如果攻击目标不存在返回0 ，目标在射程内返回1， 目标不在射程内返回2
	 * 
	 * @return
	 */
	public int judeAppointAim(ArmyPhalanx attackPhalanx, ArmyPhalanx[] phalanxList, int controlType, boolean flag,
			GameMap gameMap) {
		int minAttackGrid = attackPhalanx.getCurrMinAttackGrid();
		int maxAttackGrid = attackPhalanx.getCurrMaxAttackGrid();
		return judeAppointAim(attackPhalanx, phalanxList, controlType, flag, gameMap, minAttackGrid, maxAttackGrid);

	}
	
	/**
	 * 获得指定的攻击目标， 如果攻击目标不存在返回0 ，目标在射程内返回1， 目标不在射程内返回2
	 * 
	 * @return
	 */
	public int judeAppointAim(ArmyPhalanx attackPhalanx, ArmyPhalanx[] phalanxList, int controlType, boolean flag,
			GameMap gameMap, int minAttackGrid, int maxAttackGrid) {

		for (int i = 0; i < phalanxList.length; i++) {

			if (phalanxList[i] == null) {
				continue;
			}
			if (attackPhalanx.getSide() == phalanxList[i].getSide() && attackPhalanx.getSkillType() == 0) {
				continue;
			}
			if (phalanxList[i].getIsShow() == 0) {
				if (phalanxList[i].getPhalanxId().equals(attackPhalanx.getAimPhalanxId())) {


					List<Point> buildPoint = phalanxList[i].getBuildPointList();

					for (int p = 0; p < buildPoint.size(); p++) {
						float ph = gameMap.getElevation(attackPhalanx.getCurrX(), attackPhalanx.getCurrY());

						float d = CommUtil.getDistance(attackPhalanx.getCurrX(), attackPhalanx.getCurrY(), buildPoint.get(p).x, buildPoint.get(p).y);// Math.abs(px) + Math.abs(py);
						int k = 0;
						if (ph == 0 || maxAttackGrid == 1) {
							k = maxAttackGrid;
						}
						else {
							k = (int) Math.ceil(Math.sqrt(ph * ph + maxAttackGrid * maxAttackGrid));
						}

						if (controlType == 1) {
							if (flag) {
								if (d > attackPhalanx.getEyeShot()) {
									return 3;
								}
							}

						}
						if (d <= k && d >= minAttackGrid) {
							attackPhalanx.setAttackAimX(buildPoint.get(p).x);
							attackPhalanx.setAttackAimY(buildPoint.get(p).y);
							return 1;
						}

					}

					return 2;

				}
			}
		}
		return 0;

	}

	/**
	 * 获得视野范围内的攻击目标，优先攻击最近距离的，有优先攻击目标， 优先返回攻击目标,否则自动获得攻击目标
	 * 
	 * @return
	 */
	protected String getKenGridAim(ArmyPhalanx attackPhalanx, ArmyPhalanx[] phalanxList, GameMap gameMap) {

		List<CompPhalanx> list = new ArrayList<CompPhalanx>();
		for (int i = 0; i < phalanxList.length; i++) {
			if (phalanxList[i] == null) {
				continue;
			}
			if (attackPhalanx.getSide() == phalanxList[i].getSide()) {
				continue;
			}
			if (phalanxList[i].getPhalanxId().equals(attackPhalanx.getPhalanxId())) {
				continue;
			}
			if (phalanxList[i].getIsShow() == 1)// 不能攻击隐藏目标，隐藏目标是城防 会自动消耗
			{
				continue;
			}

			if (phalanxList[i].getIsShow() == 0) {
				int eyeShot = attackPhalanx.getEyeShot();

				List<Point> buildPoint = phalanxList[i].getBuildPointList();

				for (int p = 0; p < buildPoint.size(); p++) {
					float num = CommUtil.getDistance(attackPhalanx.getCurrX(), attackPhalanx.getCurrY(), buildPoint.get(p).x, buildPoint.get(p).y);//	Math.abs(px) + Math.abs(py);

					float ph = gameMap.getElevation(attackPhalanx.getCurrX(), attackPhalanx.getCurrY());

					int k = 0;
					if (ph == 0) {
						k = eyeShot;
					}
					else {
						k = (int) Math.ceil(Math.sqrt(ph * ph + eyeShot * eyeShot));
					}

					if (num <= k) {
						list.add(new CompPhalanx(phalanxList[i], num));
					}

				}

			}
		}

		// 挑选list中最合适的攻击目标
		if (list.size() == 0) {
			return null;
		}
		else {
			PhalanxComparator compar = new PhalanxComparator();

			Collections.sort(list, compar);
			ArmyPhalanx aimPhalanx = list.get(0).getArmyPhalanx();
			if (aimPhalanx != null) {

				return aimPhalanx.getPhalanxId();
			}

		}
		return null;

	}

	/**
	 * 获得屏幕内的攻击目标，有优先攻击目标，优先返回攻击目标,否则自动获得攻击目标
	 * 
	 * @return
	 */

	public ArmyPhalanx getAttackAim(ArmyPhalanx attackPhalanx, ArmyPhalanx[] phalanxList, GameMap gameMap) {

		List<ArmyPhalanx> list = new ArrayList<ArmyPhalanx>();
		for (int i = 0; i < phalanxList.length; i++) {

			if (phalanxList[i] == null) {
				continue;
			}
			if (attackPhalanx.getSide() == phalanxList[i].getSide()) {
				continue;
			}
			if (phalanxList[i].getPhalanxId().equals(attackPhalanx.getPhalanxId())) {
				continue;
			}

			if (phalanxList[i].getIsShow() == 1)// 不能攻击隐藏目标，隐藏目标是城防 会自动消耗
			{
				continue;
			}
			int minAttackGrid = attackPhalanx.getCurrMinAttackGrid();
			int maxAttackGrid = attackPhalanx.getCurrMaxAttackGrid();

			List<Point> buildPoint = phalanxList[i].getBuildPointList();

			for (int p = 0; p < buildPoint.size(); p++) {
				float num = CommUtil.getDistance(attackPhalanx.getCurrX(), attackPhalanx.getCurrY(), buildPoint.get(p).x, buildPoint.get(p).y);// Math.abs(px) + Math.abs(py);
				float ph = gameMap.getElevation(attackPhalanx.getCurrX(), attackPhalanx.getCurrY());

				int k = 0;
				if (ph == 0 || maxAttackGrid == 1) {
					k = maxAttackGrid;
				}
				else {
					k = (int) Math.ceil(Math.sqrt(ph * ph + maxAttackGrid * maxAttackGrid));
				}

				if (num <= k && num >= minAttackGrid) {
					list.add(phalanxList[i]);
				}

			}

		}

		// 挑选list中最合适的攻击目标
		if (list.size() == 0) {
			return null;
		}
		else {

			return list.get(0);
		}

	}

	/**
	 * 重新设置策略
	 * 
	 * @param attackPhalanx
	 * @param defendPhalanx
	 * @return
	 */
	protected String restPhalanxAim(RoleFight roleFight, ArmyPhalanx attackPhalanx, ArmyPhalanx[] phalanxsList,
			GameMap gameMap) {

		List<CompPhalanx> list1 = new ArrayList<CompPhalanx>();// 评估目标距离
		List<CompPhalanx> list2 = new ArrayList<CompPhalanx>();// 评估当前血
		List<CompPhalanx> list3 = new ArrayList<CompPhalanx>();// 评估最容易死的

		for (int i = 0; i < phalanxsList.length; i++) {
			if (phalanxsList[i] == null) {
				continue;
			}
			if (attackPhalanx.getSide() == phalanxsList[i].getSide()) {
				continue;
			}
			if (phalanxsList[i].getStatus() == 1)// 已死亡方阵
			{
				continue;
			}
			if (phalanxsList[i].getIsShow() == 0) {

				float num = -1;

				List<Point> buildPoint = phalanxsList[i].getBuildPointList();

				for (int p = 0; p < buildPoint.size(); p++) {
					float a = CommUtil.getDistance(attackPhalanx.getCurrX(), attackPhalanx.getCurrY(), buildPoint.get(p).x, buildPoint.get(p).y);//Math.abs(px) + Math.abs(py);
					if (num < 0 || a < num) {
						num = a;
					}

				}

				float ph = gameMap.getElevation(attackPhalanx.getCurrX(), attackPhalanx.getCurrY());

				int k = 0;
				if (ph == 0 || attackPhalanx.getCurrMaxAttackGrid() == 1) {
					k = attackPhalanx.getCurrMaxAttackGrid();
				}
				else {
					k = (int) Math.ceil(Math.sqrt(ph * ph + attackPhalanx.getCurrMaxAttackGrid()
							* attackPhalanx.getCurrMaxAttackGrid()));

				}

				if (num >= 0 && num <= k) {

					DefendInfo defendInfo = null;
					// 进行攻击
					defendInfo = fightCore.calculateCommAttrack(roleFight, attackPhalanx, phalanxsList[i],
								phalanxsList);


					if (defendInfo != null) {
						list2.add(new CompPhalanx(phalanxsList[i], defendInfo.getAimLossHP()));

						int lossNum = (int) (defendInfo.getAimLossHP() / phalanxsList[i].getEveHP());

						if (phalanxsList[i].getItemNum() - lossNum <= 0) {
							list3.add(new CompPhalanx(phalanxsList[i], lossNum));
						}
					}
				}

				list1.add(new CompPhalanx(phalanxsList[i], num));
			}
		}

		// 挑选list中最合适的攻击目标
		if (list1.size() == 0) {

			// attackPhalanx.setMoveAimPhalanx(null);
			return null;
		}
		else {
			PhalanxComparator compar1 = new PhalanxComparator();
			Collections.sort(list1, compar1);
			for (int i = 0; i < list1.size(); i++) {
				return list1.get(i).getArmyPhalanx().getPhalanxId();
			}

			PhalanxComparator compar3 = new PhalanxComparator();
			Collections.sort(list3, compar3);

			for (int i = 0; i < list3.size(); i++) {
				return list3.get(i).getArmyPhalanx().getPhalanxId();

			}

			PhalanxComparator compar2 = new PhalanxComparator();

			Collections.sort(list2, compar2);

			for (int i = 0; i < list2.size(); i++) {
				// 在攻击范围内 受到攻击伤害最大的部队

				return list2.get(i).getArmyPhalanx().getPhalanxId();

			}

			return null;
		}

	}

	/**
	 * 弗洛伊德路径平滑处理
	 * 
	 * @return
	 */
	protected void floyd(GameMap gameMap, Path path, Mover mover) {
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
			floydVector(vector, smoothPaths.get(len - 1), smoothPaths.get(len - 2));
			for (int i = smoothPaths.size() - 3; i >= 0; i--) {
				floydVector(tempVector, smoothPaths.get(i + 1), smoothPaths.get(i));
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
				if (!gameMap.hasBarrier((int) step1.getX(), (int) step1.getY(), (int) step2.getX(), (int) step2.getY(),
						mover)) {
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

	protected void floydVector(Point target, Step n1, Step n2) {
		target.x = (int) (n1.getX() - n2.getX());
		target.y = (int) (n1.getY() - n2.getY());
	}

	public void setArmyPhlanxStagePoint(ArmyPhalanx armyPhalanx, long fightId) {
		RoleFight roleFight = FightDataCache.getRoleFight(fightId);
		if(roleFight != null)	{
			GameMap gameMap = roleFight.getMap();
			setArmyPhlanxStagePoint(armyPhalanx, gameMap);
		}
		
	}

	public void setArmyPhlanxStagePoint(ArmyPhalanx armyPhalanx, GameMap gameMap) {
		Step stageCenter = gameMap.gridToWorld(armyPhalanx.getCurrX(), armyPhalanx.getCurrY());
		armyPhalanx.setCurrStageX(stageCenter.getX());
		armyPhalanx.setCurrStageY(stageCenter.getY());
	}

	public boolean isRoleBlock() {
		return roleBlock;
	}
	public void resetPhalanxPoint(int x, int y, ArmyPhalanx armyPhalanx, GameMap map) {
		clearPhalanxPoint(armyPhalanx);

		List<Point> points = map.getGridPoint(x, y, armyPhalanx.getGridNum());

		armyPhalanx.setBuildPointList(points);

	}
	public void clearPhalanxPoint(ArmyPhalanx armyPhalanx)
	{
		armyPhalanx.getBuildPointList().clear();
	}
}
