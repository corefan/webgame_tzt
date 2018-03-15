package com.snail.webgame.engine.component.scene.thread;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.common.info.NPCInfo;
import com.snail.webgame.engine.common.info.NPCModelInfo;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.common.pathfinding.mesh.GameMap3D;
import com.snail.webgame.engine.component.scene.cache.NPCDeadMap;
import com.snail.webgame.engine.component.scene.cache.NPCModelMap;
import com.snail.webgame.engine.component.scene.config.GameConfig;
import com.snail.webgame.engine.component.scene.core.SceneGameMapFactory;
import com.snail.webgame.engine.component.scene.core.SceneService;

/**
 * @author wangxf
 * @date 2012-8-10
 * 复活死亡的npc线程类
 */
public class RebirthNPCThread extends Thread {
	private static final Logger logger = LoggerFactory.getLogger("logs");
	private String mapId;
	private volatile boolean cancel = false;
	
	public RebirthNPCThread(String mapId) {
		this.mapId = mapId;
	}

	@Override
	public void run() {
		while(!cancel) {
			if (logger.isDebugEnabled()) {
				logger.debug("Start to scan NPCDead Map...");
			}
			long t1 = System.currentTimeMillis();
			
			// 获取该地图上死亡的npc实例
			Map<Long, NPCInfo> deadNPCMap = NPCDeadMap.map.get(mapId);
			if (deadNPCMap != null && deadNPCMap.size() > 0) {
				// 循环处理每个npc
				for (Map.Entry<Long, NPCInfo> entry : deadNPCMap.entrySet()) {
					NPCInfo npcInfo = entry.getValue();
					if (npcInfo == null) {
						continue;
					}
					
					// 触发NPC复活动作
					calNPCRebirthAction(npcInfo);
				}
			}
			
			long t2 = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug("Scan DeadNPC success, cost time :" + (t2 - t1) + "ms");
			}
			
			// 间隔1秒循环一次
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 判断NPC是否可以复活，并进行相应的处理
	 * @param npcInfo
	 * @author wangxf
	 * @date 2012-8-7
	 */
	private void calNPCRebirthAction(NPCInfo npcInfo) {
		// 获取npc死亡的时间
		long time = npcInfo.getTime();
		// 获取该类型npc的刷新间隔
		int refreshTimePeriod = npcInfo.getRefreshTimePeriod();
		if (time + refreshTimePeriod * 1000 < System.currentTimeMillis()) {
			rebirthNPC(npcInfo);
			// 给可见范围内的玩家发送NPC复活的消息
			sendNPCRebirthToRole(npcInfo);
		}
	}
	
	/**
	 * 给可见范围内的玩家发送NPC复活的消息
	 * @param npcInfo
	 * @author wangxf
	 * @date 2012-8-2
	 */
	private void sendNPCRebirthToRole(NPCInfo npcInfo) {
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(npcInfo.getMapId());
		// 获取npc复活坐标点方格范围内的玩家
		List<Long> roleIds = gameMap.getAreaAllRole((int)npcInfo.getCurrX(), (int)npcInfo.getCurrY(), (int)npcInfo.getCurrZ(), 
				refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		if (roleIds != null && roleIds.size() > 0) {
			// 设置npc附近的玩家集合
			npcInfo.setOtherRoleIds(roleIds);
			// 给视野范围内的每个玩家发送增加单个npc的协议
			for(long roleId : roleIds) {
				RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
				if (roleInfo != null) {
					//System.out.println("========wangxf====给玩家 ：" + roleId + "发送NPC：" + npcInfo.getId() + "增加的协议");
					SceneService.sendAddNPCInfoMsg(roleInfo, npcInfo, npcInfo.getPath());
					roleInfo.addNpcId(npcInfo.getId());
				}
			}
		}
		
	}

	/**
	 * 复活NPC
	 * @param npcInfo
	 * @author wangxf
	 * @date 2012-8-2
	 */
	private void rebirthNPC(NPCInfo npcInfo) {
		// 将npc从在地图上原来的位置删除
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(npcInfo.getMapId());
		gameMap.delNPC((int)npcInfo.getCurrX(), (int)npcInfo.getCurrY(), (int)npcInfo.getCurrZ(), npcInfo.getId());
		/*System.out.println(Thread.currentThread().getName() + 
				"======RebirthNPCThread.rebirthNPC===(" + (int) npcInfo.getCurrX() + 
				"," + (int) npcInfo.getCurrY() + "," + (int) npcInfo.getCurrZ() + ")删除");*/
		
		// 给npc死亡位置的玩家发送npc消失的协议
		sendNPCClearToRole(npcInfo);
		
		// 获取该类npc的基本信息对象
		NPCModelInfo npcModelInfo = NPCModelMap.getNPCModel(npcInfo.getNo());
		// 设置npc的状态为静止
		npcInfo.setStatus(0);
		npcInfo.setAttackAim(0);
		// 设置npc的当前坐标为出生坐标
		npcInfo.setCurrX(npcInfo.getBornX());
		npcInfo.setCurrY(npcInfo.getBornY());
		npcInfo.setCurrZ(npcInfo.getBornZ());
		// 设置npc的当前血量和魔法值
		npcInfo.setCurrHP(npcModelInfo.getHp());
		npcInfo.setCurrMP(npcModelInfo.getMp());
		
		// 将该npc从死亡位置附近玩家对象的npc集合中删除
		List<Long> otherRoleIds = npcInfo.getOtherRoleIds();
		for(long roleId : otherRoleIds){
			RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
			if(roleInfo != null){
				roleInfo.getNpcIds().remove(npcInfo.getId());
			}
		}
		// 附近玩家集合清空
		npcInfo.getOtherRoleIds().clear();
		npcInfo.setTime(System.currentTimeMillis());
		
		// 在地图上的出生点位置增加该npc
		/*System.out.println(Thread.currentThread().getName() + 
				"======RebirthNPCThread.rebirthNPC===(" + (int) npcInfo.getCurrX() + 
				"," + (int) npcInfo.getCurrY() + "," + (int) npcInfo.getCurrZ() + ")增加");*/
		gameMap.setNPC((int)npcInfo.getCurrX(), (int)npcInfo.getCurrY(), (int)npcInfo.getCurrZ(), npcInfo.getId());
		
		// 从死亡的map中删除该npc
		NPCDeadMap.removeNPC(npcInfo.getMapId(), npcInfo.getId());
		
	}

	/**
	 * 给npc尸体所在位置可见范围的玩家发送npc尸体消失的协议
	 * @param npcInfo
	 * @author wangxf
	 * @date 2012-8-17
	 */
	private void sendNPCClearToRole(NPCInfo npcInfo) {
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		GameMap3D gameMap = SceneGameMapFactory.getGameMap(npcInfo.getMapId());
		// 获取npc尸体坐标点方格范围内的玩家
		List<Long> roleIds = gameMap.getAreaAllRole((int)npcInfo.getCurrX(), 
				(int)npcInfo.getCurrY(), (int)npcInfo.getCurrZ(), 
				refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		if (roleIds != null && roleIds.size() > 0) {
			// 给视野范围内的每个玩家发送增加单个npc的协议
			for(long roleId : roleIds) {
				RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
				if (roleInfo != null) {
					//System.out.println("========wangxf====给玩家 ：" + roleId + "发送NPC：" + npcInfo.getId() + "消失的协议");
					SceneService.sendDelNPCInfoMsg(roleInfo, npcInfo.getId());
					// 从该玩家的npc集合中删除该npc
					roleInfo.getNpcIds().remove(npcInfo.getId());
				}
			}
		}
	}
	
	public void cancel(){
		this.cancel = true;
	}
	
}
