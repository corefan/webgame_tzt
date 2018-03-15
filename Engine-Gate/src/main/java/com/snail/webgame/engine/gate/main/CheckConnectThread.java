package com.snail.webgame.engine.gate.main;

import java.util.HashMap;
import java.util.Set;

import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.gate.cache.ServerMap;
import com.snail.webgame.engine.gate.common.ConnectConfig;
import com.snail.webgame.engine.gate.config.GlobalServer;
import com.snail.webgame.engine.gate.config.WebGameConfig;
import com.snail.webgame.engine.gate.send.connect.Connect;
import com.snail.webgame.engine.gate.util.MessageServiceManage;

/**
 * 维护连接 如果连接中断重新连接
 * @author cici
 *
 */
public class CheckConnectThread extends Thread{
	private MessageServiceManage messagemgt = new MessageServiceManage();
	private static final Logger log =LoggerFactory.getLogger("logs");
	private IoHandlerAdapter handlerAdapter;
	
	public CheckConnectThread(IoHandlerAdapter handlerAdapter){
		this.handlerAdapter = handlerAdapter;
	}
	public void run()
	{
		 while(true)
		 {
  
			 HashMap<String, ConnectConfig> map =  WebGameConfig.getInstance().getConnectConfig();
			 
			 Set<String> set = map.keySet();
			 
			 for(String key :set)
			 {
//				 if(key.startsWith(ServerName.GAME_SCENE_SERVER))
//				 {
					 checkConnect(key);
//				 }
				 
			 }
			 
			 
			 if(GlobalServer.GAME_IS_REGISTER&&GlobalServer.CHAT_IS_REGISTER)
			 {
				 try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						log.error("",e);
					}
			 }
			 else
			 {
				 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						log.error("",e);
					}
			 }
			
		 }
	}
	
	public void checkConnect(String serverName)
	{
		IoSession session = ServerMap.getSession(serverName);
		if(session==null||!session.isConnected())
		{
			
			session =  Connect.connectServer(serverName, handlerAdapter);
			if(session!=null)
			{
				ServerMap.addSession(serverName, session);
			}
		}
		if(session!=null&&session.isConnected())
		{
			if(serverName.startsWith(ServerName.GAME_SCENE_SERVER))
			{
				messagemgt.sendActiveServerMessage(session, 1);
				
			}
			else if(serverName.equalsIgnoreCase(ServerName.GAME_SERVER_NAME)){
				if(GlobalServer.GAME_IS_REGISTER )
				{
					messagemgt.sendActiveServerMessage(session, 1);
				}
				else
				{
					messagemgt.sendActiveServerMessage(session, 0);
					session.setAttribute("serverName",ServerName.GAME_SERVER_NAME);
				 
				}
				GlobalServer.GAME_IS_REGISTER = true;
			}
			else if(serverName.equalsIgnoreCase(ServerName.MAIL_SERVER_NAME))
			{
				
				if(GlobalServer.CHAT_IS_REGISTER )
				{
					messagemgt.sendActiveServerMessage(session, 1);
				}
				else
				{
					messagemgt.sendActiveServerMessage(session, 0);
					session.setAttribute("serverName",ServerName.MAIL_SERVER_NAME);
				}
				GlobalServer.CHAT_IS_REGISTER = true;
			}
			// add by xfwang 给前置服务器发送网关服务器信息
			else if (serverName.equalsIgnoreCase(ServerName.LIST_SEREVER_NAME)) {
				messagemgt.setActiveListServerMessage(session, 1);
			}
			
		}
	}
}
