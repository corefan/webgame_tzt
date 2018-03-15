package com.snail.webgame.game.main;

 


 
import org.epilot.ccf.server.acceptor.Server;

import com.snail.webgame.game.config.ListConfig;
import com.snail.webgame.game.config.ListOtherConfig;
 




public class ListASMain {

	public static void main(String args[])
	{
		
		//初始化获得游戏服务器列表
		ListConfig.getInstance();
		ListOtherConfig.getInstance();
	    //启动通讯程序
		Server server=new Server();
		server.start();
 
		//启动计费应用
		ChargeThread thread = new ChargeThread();
		thread.start();
 
	}
}
