package com.snail.webgame.engine.component.scene.protocal.server.service;

import org.apache.mina.common.IoSession;

import com.snail.webgame.engine.component.scene.cache.ServerMap;
import com.snail.webgame.engine.component.scene.protocal.server.active.ServerActiveReq;

public class ServerActiveService {

	
	public void activeConnect(ServerActiveReq req ,IoSession session)
	{
		
		String serverName = req.getServerName();
		int flag = req.getFlag();
		
		if(serverName==null||serverName.trim().length()==0)
		{
			return;
		}
		if(flag ==0)
		{
			//通讯服务器重启，对应用户下线
		 
 
		}
		
		if(session.getAttribute("serverName")==null)
		{
			ServerMap.addServer(serverName,session);
			
			session.setAttribute("serverName", serverName);
		}
	}
}
