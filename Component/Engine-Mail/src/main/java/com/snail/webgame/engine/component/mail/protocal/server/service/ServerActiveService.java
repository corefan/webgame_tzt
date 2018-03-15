package com.snail.webgame.engine.component.mail.protocal.server.service;

import org.apache.mina.common.IoSession;

import com.snail.webgame.engine.component.mail.cache.RoleLoginMap;
import com.snail.webgame.engine.component.mail.cache.RoleNameMap;
import com.snail.webgame.engine.component.mail.cache.ServerMap;
import com.snail.webgame.engine.component.mail.protocal.server.active.ServerActiveReq;

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
			
			RoleLoginMap.clear();
			RoleNameMap.clear();
		}
		
		if(session.getAttribute("serverName")==null)
		{
			ServerMap.addServer(serverName,session);
			
			session.setAttribute("serverName", serverName);
		}
	}
}
