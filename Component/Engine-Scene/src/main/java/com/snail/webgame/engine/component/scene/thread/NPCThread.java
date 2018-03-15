package com.snail.webgame.engine.component.scene.thread;

import java.util.List;
import java.util.Map;

import org.critterai.math.Vector3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.info.NPCInfo;
import com.snail.webgame.engine.common.info.NPCState;
import com.snail.webgame.engine.common.info.Point2D;
import com.snail.webgame.engine.common.info.Point3D;
import com.snail.webgame.engine.common.pathfinding.mesh.GameMap3D;
import com.snail.webgame.engine.common.pathfinding.mesh.Path3D;
import com.snail.webgame.engine.common.pathfinding.mesh.Step3D;
import com.snail.webgame.engine.component.scene.cache.EventChannel;
import com.snail.webgame.engine.component.scene.cache.NPCInfoMap;
import com.snail.webgame.engine.component.scene.config.GameConfig;
import com.snail.webgame.engine.component.scene.core.ISceneCalculate;
import com.snail.webgame.engine.component.scene.core.SceneGameMapFactory;
import com.snail.webgame.engine.component.scene.core.SceneService;
import com.snail.webgame.engine.component.scene.event.AttackEvent;
import com.snail.webgame.engine.component.scene.event.Event;
import com.snail.webgame.engine.component.scene.event.EventActor;
import com.snail.webgame.engine.component.scene.event.MoveEvent;
import com.snail.webgame.engine.component.scene.util.RandomPointUtil;

/**
 * @author wangxf
 * @date 2012-8-2 NPC线程处理类，主要处理NPC移动
 */
public class NPCThread extends Thread {
	private static final Logger logger = LoggerFactory.getLogger("logs");
	private String mapId;
	private ISceneCalculate sceneCalculate;
	private volatile boolean cancel = false;

	/**
	 * 构造体
	 * 
	 * @param sceneCalculate
	 * @param mapId
	 */
	public NPCThread(ISceneCalculate sceneCalculate, String mapId) {
		this.sceneCalculate = sceneCalculate;
		this.mapId = mapId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (!cancel) {
			if (logger.isDebugEnabled()) {
				logger.debug("Start to scan NPC Map...");
			}
			long t1 = System.currentTimeMillis();

			// 获取该地图上所有npc实例
			Map<Long, NPCInfo> npcInfoMap = NPCInfoMap.getNPCInfo(mapId);
			if (npcInfoMap != null && npcInfoMap.size() > 0) {
				// 循环处理每个npc
				for (Map.Entry<Long, NPCInfo> entry : npcInfoMap.entrySet()) {
					NPCInfo npcInfo = entry.getValue();
					if (npcInfo == null) {
						continue;
					}
					// 主动攻击型
					if (npcInfo.getNpcType() == 2 && npcInfo.getAttackAim() <= 0 && npcInfo.getStatus() != 2) {
						GameMap3D gameMap = SceneGameMapFactory.getGameMap(npcInfo.getMapId());
						if (gameMap == null)
							continue;
						int initAttackGrid = npcInfo.getInitiativeAttackGrid();
						List<Long> roleIds = gameMap.getAreaAllRole((int) npcInfo.getCurrX(), (int) npcInfo.getCurrY(),
								(int) npcInfo.getCurrZ(), initAttackGrid, initAttackGrid, initAttackGrid);
						if (roleIds != null && !roleIds.isEmpty()) {
							long roleId = roleIds.get(0);
							//npcInfo.setStatus(4);
							npcInfo.setPursueRevertX(npcInfo.getBornX());
							npcInfo.setPursueRevertY(npcInfo.getBornY());
							npcInfo.setPursueRevertZ(npcInfo.getBornZ());
							AttackEvent e = new AttackEvent(npcInfo.getId(), EventActor.EVENT_ACTOR_NPC, roleId,
									EventActor.EVENT_ACTOR_PLAYER);
							// 去除NPC的移动事件，添加攻击事件(在NPC返回原地点的途中，又判断到有可攻击目标的情况)
							EventChannel.removeEvent(new MoveEvent(npcInfo.getId(), EventActor.EVENT_ACTOR_NPC, null), mapId);
							EventChannel.addEvent(npcInfo.getMapId(), e, npcInfo.getBattleStatus());
							continue;
						}
					}
					// 触发NPC移动动作
					calNPCMoveAction(npcInfo);

					// 触发NPC复活动作
					// calNPCRebirthAction(npcInfo);

					// 使每个npc不在同一时刻移动，之间休眠100豪秒
					// try {
					// Thread.sleep(100);
					// } catch (InterruptedException e) {
					// e.printStackTrace();
					// }

				}
			}

			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug("Scan NPC success, cost time :" + (t2 - t1) + "ms");
			}

			// 间隔1秒循环一次
			try {
				Thread.sleep(300);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 判断NPC是否可以移动
	 * 
	 * @param npcInfo
	 * @author wangxf
	 * @date 2012-8-6
	 */
	private void calNPCMoveAction(NPCInfo npcInfo) {
		// npc的当前状态
		int status = npcInfo.getStatus();
		// 如果不是正常状态则不处理
		if (status != NPCState.STATE_NORMAL || npcInfo.getBattleStatus() == 1) {
			return;
		}
		// 如果npc是不巡逻的模式，则不需要巡逻
		if (npcInfo.getPatrolMode() != 0) {
			// 判断npc当前所在区域范围内有没有玩家
			boolean isHavePlayer = isHavaPalyerInRound(npcInfo);
			// 如果该区域内没有玩家，则不巡逻
			if (isHavePlayer == false) {
				return;
			}

			// 上次巡逻的时间
			long lastPatrolTime = npcInfo.getTime();
			// 巡逻时间间隔
			int patrolTimePeriod = npcInfo.getPatrolTimePeriod();
			// 如果还没到达再次巡逻的时间，则不巡逻
			if (lastPatrolTime + (patrolTimePeriod * 1000) > System.currentTimeMillis()) {
				return;
			}
			GameMap3D gameMap = SceneGameMapFactory.getGameMap(npcInfo.getMapId());

			Path3D path = null;
			Point3D point = null;
			float currX = npcInfo.getCurrX();
			float currY = npcInfo.getCurrY();
			float currZ = npcInfo.getCurrZ();
			// 随机巡逻的方式
			if (npcInfo.getPatrolMode() == 2) {
				// 巡逻半径
				double patrolRange = npcInfo.getPatrolRange();
				// 获取一个出生点范围内的随机点
				Point2D point2D = RandomPointUtil.getRandomPointInRound(npcInfo.getBornX(), npcInfo.getBornZ(),
						(float) patrolRange, (float) gameMap.getMaxX(), (float) gameMap.getMaxZ());
				Vector3 v = gameMap.getHelper().getClosetPoint(point2D.getX(), npcInfo.getBornY(), point2D.getZ());
				point = new Point3D(v.x, v.y, v.z);
			}
			// 固定点巡逻的方式
			else if (npcInfo.getPatrolMode() == 1) {
				// 固定巡逻点
				Point3D[] patrolPoints = npcInfo.getPatrolPoints();
				// 上次在巡逻到第几个巡逻点
				int currPatrolPointPos = npcInfo.getCurrPatrolPointPos();
				// 如果是最后一个点，则本次巡逻回到第一个点
				if (currPatrolPointPos == patrolPoints.length - 1) {
					currPatrolPointPos = 0;
				}
				// 否则巡逻下个点
				else {
					currPatrolPointPos++;
				}
				npcInfo.setCurrPatrolPointPos(currPatrolPointPos);
				point = patrolPoints[currPatrolPointPos];
			}
			// 判断是否是阻挡点
			/*
			 * while (gameMap.blocked(null, (int)point.getX(),
			 * (int)point.getY())) { point =
			 * getRandomPoint(gameMap.getWidthInTiles(),
			 * gameMap.getHeightInTiles(), 0); }
			 */
			if (point == null) {
				return;
			}
			// 调用算法生成一个路径
			path = sceneCalculate
					.getPhalanxPath(gameMap, currX, currY, currZ, point.getX(), point.getY(), point.getZ());
			if (path == null || path.getSteps() == null || path.getSteps().size() < 2) {
				return;
			}
			Step3D endStep = path.getSteps().get(path.getSteps().size() - 1);
			// 设置X坐标终点
			npcInfo.setAimX(endStep.getX());
			// 设置Y坐标终点
			npcInfo.setAimY(endStep.getY());
			// 设置Z坐标点
			npcInfo.setAimZ(endStep.getZ());
			// 设置路径
			npcInfo.setPath(path);
			// 设置状态为移动
			//npcInfo.setStatus(NPCState.STATE_MOVE);
			// 设置本次巡逻时间
			npcInfo.setTime(System.currentTimeMillis());
			// 生成一个NPC移动的事件
			Event event = new MoveEvent(npcInfo.getId(), EventActor.EVENT_ACTOR_NPC, path);
			EventChannel.addEvent(npcInfo.getMapId(), event, npcInfo.getBattleStatus());

			SceneService.sendMoveMsg(npcInfo, path);
		}

	}

	/**
	 * 判断npc当前所在区域范围内有没有玩家
	 * 
	 * @param npcInfo
	 * @return
	 * @author wangxf
	 * @date 2012-8-5
	 */
	private boolean isHavaPalyerInRound(NPCInfo npcInfo) {
		boolean flag = false;
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(npcInfo.getMapId());
		if (gameMap == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("The scene does not hava the map, mapId = " + npcInfo.getMapId());
			}
			return flag;
		}
		// 获取npc复活坐标点方格范围内的玩家
		List<Long> roleIds = gameMap.getAreaAllRole((int) npcInfo.getCurrX(), (int) npcInfo.getCurrY(),
				(int) npcInfo.getCurrZ(), refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		if (roleIds.size() > 0) {
			flag = true;
		}
		return flag;
	}

	public void cancel() {
		this.cancel = true;
	}
}
