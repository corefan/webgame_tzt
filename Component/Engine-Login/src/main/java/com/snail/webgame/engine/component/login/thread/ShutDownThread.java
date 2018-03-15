package com.snail.webgame.engine.component.login.thread;

import org.apache.mina.common.CloseFuture;
import org.apache.mina.common.IoSession;
import org.epilot.ccf.server.acceptor.Server;
import org.epilot.ccf.threadpool.ThreadPoolManager;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.component.login.cache.ServerMap;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;
import com.snail.webgame.engine.component.login.config.GameConfig;
import com.snail.webgame.engine.db.thread.LazyDataThread;

public class ShutDownThread extends Thread{

	private Server server;
	private ChargeThread chargeThread;
	private RoleLoginQueueThread loginQueueThread;
	private PromptParaThread promptParaThread;
	private LazyDataThread lazyDataThread;
	
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	
	public ShutDownThread(Server server,ChargeThread chargeThread, RoleLoginQueueThread loginQueueThread, 
			PromptParaThread promptParaThread, LazyDataThread lazyDataThread)
	{
		this.server = server ; 
		this.chargeThread = chargeThread;
		this.loginQueueThread = loginQueueThread;
		this.promptParaThread = promptParaThread;
		this.lazyDataThread = lazyDataThread;
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
		 //关闭计费链接
		 
		chargeThread.cancel();
		//发送计费注销信息
		
		ChargeMgtService.sendChargeUnRegisterReq();
		
		loginQueueThread.cancel();
		
		lazyDataThread.cancel();
		
		if(promptParaThread != null)
			promptParaThread.cancel();
		
		IoSession session = ServerMap.getServerSession(ServerName.GAME_CHARGE_SERVER);
		 
		if(session !=null&&session.isConnected())
		{
			CloseFuture future = session.close();
			future.join();
		}
		 if(logger.isWarnEnabled())
		 {
			 logger.warn("Server close links with'"+ServerName.GAME_CHARGE_SERVER+"' 。。。。");
		 }
		 ServerMap.removeServer(ServerName.GAME_CHARGE_SERVER);
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
		 //关闭数据库连接
		 ProxoolFacade.shutdown();
		 if(logger.isWarnEnabled())
		 {
			 logger.warn("System is shut down the database connection.....");
		 }
		 
		 
		 

		 if(logger.isWarnEnabled())
		 {
			 logger.warn("Normal closure of the server!");
		 }
	}
}
