package com.snail.webgame.engine.component.scene.main;

 

import org.epilot.ccf.server.acceptor.Server;

import com.snail.webgame.engine.component.scene.config.FightConfig;
import com.snail.webgame.engine.component.scene.config.FightOtherConfig;

public class GameASMain {
 
	     
	 
	 
	public static void main(String[] args)
	{
		
		FightConfig.getInstance();
		FightOtherConfig.getInstance();
		if(GameInit.init())
		{
			CheckGameThread thread = new CheckGameThread();
			thread.start();
	
			Server server=new Server();
			server.start();
		} 
	}
}
