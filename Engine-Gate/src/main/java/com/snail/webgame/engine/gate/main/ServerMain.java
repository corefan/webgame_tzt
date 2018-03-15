package com.snail.webgame.engine.gate.main;

import com.snail.webgame.engine.gate.config.CertConfig;
import com.snail.webgame.engine.gate.config.WebGameConfig;
import com.snail.webgame.engine.gate.receive.manage.ConnectProtocolHandler;
import com.snail.webgame.engine.gate.receive.manage.Listener;
import com.snail.webgame.engine.gate.send.manage.SendProtocolHandler;
import com.snail.webgame.engine.gate.util.CertThread;


 


public class ServerMain {

	public static void main(String args[])
	{
 
		
		
		//服务器启动监听
		if(CertConfig.getInstance(WebGameConfig.getInstance().getLicensePath()).isPass()&&Listener.listener(new ConnectProtocolHandler()))
		{
			/**
			 * 检查连接线程，断线自动连接并且自动注册
			 */
			CheckConnectThread checkThread = new CheckConnectThread(new SendProtocolHandler());
			checkThread.start();
	 
//			ListServerThread listThread = new ListServerThread();
//			listThread.start();
			
			CertThread certThread = new CertThread();
			
			certThread.start();
			
			Runtime.getRuntime().addShutdownHook(new ShutDownThread());
		}
		
		
		
		
	}
}
