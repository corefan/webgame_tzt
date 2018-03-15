package com.snail.webgame.engine.component.scene.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.scene.core.ISceneCalculate;

/**
 * 场景主线程
 * @author zenggy
 *
 */
public class SceneThread extends Thread {
	private volatile boolean cancel = false;
	private static final Logger log =LoggerFactory.getLogger("logs");
	private ISceneCalculate sceneCalculate;
	private String mapId;
	private int battleStatus;
	/**
	 * 构造体
	 * @param sceneCalculate 场景计算类
	 * @param mapId 地图ID
	 * @param battleStatus 战斗状态：0-非战斗 -1战斗状态
	 */
	public SceneThread(ISceneCalculate sceneCalculate, String mapId, int battleStatus){
		this.sceneCalculate = sceneCalculate;
		this.mapId = mapId;
		this.battleStatus = battleStatus;
	}
	
	@Override
	public void run() {
		
		while(!cancel){
			
			long begTime = System.currentTimeMillis();
			int num = sceneCalculate.calAllRolePolicy(mapId, battleStatus);
			int time = (int)(System.currentTimeMillis()-begTime);
			if(time/1000 > 0){
				if(log.isWarnEnabled())
				{
					log.warn("[SYSTEM] Engine-Scene-Server SceneThread mapId=" + mapId + " battleStatus=" + battleStatus + " cost time : "+time + ", num=" + num);
				}
			}
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 停止线程
	 * @return
	 */
	public void cancel(){
		this.cancel = true;
	}
	
}
