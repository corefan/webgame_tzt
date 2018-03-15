package com.snail.webgame.engine.component.scene.thread;

import org.epilot.ccf.server.acceptor.Server;
import org.epilot.ccf.threadpool.ThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.scene.config.GameConfig;

public class ShutDownThread extends Thread{

	private Server server;
	private SceneThread[] sceneThread;
	private NPCThread[] npcThread;
	private RebirthNPCThread[] rebirthNPCThreads;
	
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	
	public ShutDownThread(Server server,SceneThread[] sceneThread, 
			NPCThread[] npcThread,RebirthNPCThread[] rebirthNPCThreads)
	{
		this.server = server ; 
		this.sceneThread = sceneThread;
		this.npcThread = npcThread;
		this.rebirthNPCThreads = rebirthNPCThreads;
	}
	public void run()
	{
		
		//游戏服务器关闭时向GMCC系统发送断开消息
		//Gmcc.disconnect();
		
		 //关闭已经连接到监听端口的所有连接
		 
		 server.unbind(GameConfig.getInstance().getServerName());
		 if(logger.isWarnEnabled())
		 {
			 logger.warn("Server unbind'"+GameConfig.getInstance().getServerName()+"' Listener....");
		 }
		 if(sceneThread != null && sceneThread.length > 0){
			 for(SceneThread scene : sceneThread){
				 scene.cancel();
			 }
		 }
		 
		 if(npcThread != null && npcThread.length > 0){
			 for(NPCThread npc : npcThread){
				 npc.cancel();
			 }
		 }
		 
		 if(rebirthNPCThreads != null && rebirthNPCThreads.length > 0){
			 for(RebirthNPCThread rebirthNpc : rebirthNPCThreads){
				 rebirthNpc.cancel();
			 }
		 }
		 
		 
		 //系统保护
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 
		 
		 //关闭线程池
		 ThreadPoolManager.getInstance().close();
		 if(logger.isWarnEnabled())
		 {
			 logger.warn("Server is shut down all processing  thread pool。。。。");
		 }
		 

		 if(logger.isWarnEnabled())
		 {
			 logger.warn("Normal closure of the server!");
		 }
	}
}
