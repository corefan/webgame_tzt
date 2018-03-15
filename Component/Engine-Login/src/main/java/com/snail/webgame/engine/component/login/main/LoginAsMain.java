package com.snail.webgame.engine.component.login.main;


import org.epilot.ccf.server.acceptor.Server;
import org.logicalcobwebs.proxool.ProxoolFacade;

import com.snail.webgame.engine.component.login.config.GameConfig;
import com.snail.webgame.engine.component.login.config.GameOtherConfig;
import com.snail.webgame.engine.component.login.config.LoadWordList;
import com.snail.webgame.engine.component.login.thread.ChargeThread;
import com.snail.webgame.engine.component.login.thread.PromptParaThread;
import com.snail.webgame.engine.component.login.thread.RoleLoginQueueThread;
import com.snail.webgame.engine.component.login.thread.ShutDownThread;
import com.snail.webgame.engine.db.thread.LazyDataThread;

public class LoginAsMain {
	public static void start() {
		ProxoolFacade.disableShutdownHook();
		
		GameConfig.getInstance();
		GameOtherConfig.getInstance();
		new LoadWordList().load();
		
		
		Server server = new Server();
		server.start();
		
		LazyDataThread lazyDataThread = new LazyDataThread();
		lazyDataThread.setName("定时存储");
		lazyDataThread.start();
		
		RoleLoginQueueThread loginQueueThread = new RoleLoginQueueThread();
		loginQueueThread.setName("登录排队");
		loginQueueThread.start();
		
		ChargeThread chargeThread = new ChargeThread();
		chargeThread.setName("计费连接");
		chargeThread.start();
		
		PromptParaThread promptParaThread = null;
        if(GameOtherConfig.getInstance().getPromptFlag()==3)
		{
        	promptParaThread = new PromptParaThread();
			promptParaThread.setName("防沉迷");
			promptParaThread.start();
		}
        
        Runtime.getRuntime().addShutdownHook(new ShutDownThread(
				server,chargeThread,loginQueueThread,promptParaThread,lazyDataThread));
	}
}
