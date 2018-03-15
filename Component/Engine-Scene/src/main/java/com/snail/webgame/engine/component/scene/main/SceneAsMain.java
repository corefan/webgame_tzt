package com.snail.webgame.engine.component.scene.main;

import java.util.Set;

import org.critterai.nav.ThreadedNavigator;
import org.epilot.ccf.server.acceptor.Server;

import com.snail.webgame.engine.component.scene.cache.SceneMapCache;
import com.snail.webgame.engine.component.scene.config.GameConfig;
import com.snail.webgame.engine.component.scene.config.LoadMapData;
import com.snail.webgame.engine.component.scene.config.LoadMapDoorData;
import com.snail.webgame.engine.component.scene.core.ISceneCalculate;
import com.snail.webgame.engine.component.scene.thread.NPCThread;
import com.snail.webgame.engine.component.scene.thread.RebirthNPCThread;
import com.snail.webgame.engine.component.scene.thread.SceneThread;
import com.snail.webgame.engine.component.scene.thread.ShutDownThread;

public class SceneAsMain {
	public static void start(ISceneCalculate sceneCalculate) {
		initData();

		Set<String> sets = SceneMapCache.keySet();
		SceneThread[] sceneThreads = new SceneThread[sets.size()];// * 2];
		NPCThread[] npcThreads = new NPCThread[sets.size()];
		RebirthNPCThread[] rebirthNPCThreads = new RebirthNPCThread[sets.size()];

		int sceneThreadCount = 0;
		int i = 0;
		for (String mapId : sets) {
			// 初始化场景主线程
			SceneThread sceneThread = new SceneThread(sceneCalculate, mapId, 0);
			sceneThread.setName("SceneThread: " + mapId);
			sceneThread.start();
			sceneThreads[sceneThreadCount] = sceneThread;
			sceneThreadCount++;

//			SceneThread sceneThread1 = new SceneThread(sceneCalculate, mapId, 1);
//			sceneThread1.setName("Battle SceneThread: " + mapId);
//			sceneThread1.start();
//			sceneThreads[sceneThreadCount] = sceneThread1;
//			sceneThreadCount++;

			// 启动NPC扫描线程
			NPCThread npcThread = new NPCThread(sceneCalculate, mapId);
			npcThread.setName("NPCThread: " + mapId);
			npcThread.start();
			npcThreads[i] = npcThread;

			// 启动NPC复活线程
			RebirthNPCThread rebirthNPCThread = new RebirthNPCThread(mapId);
			rebirthNPCThread.setName("RebirthNPCThread: " + mapId);
			rebirthNPCThread.start();
			rebirthNPCThreads[i] = rebirthNPCThread;

			i++;

		}

		Server server = new Server();
		server.start();

		Runtime.getRuntime().addShutdownHook(
				new ShutDownThread(server, sceneThreads, npcThreads, rebirthNPCThreads));
	}

	private static void initData() {
		GameConfig.getInstance();
		LoadMapData.init();
		LoadMapDoorData.load();
	}
}
