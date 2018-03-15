package com.snail.webgame.engine.component.scene.protocal.server.service;


import org.apache.mina.common.IoSession;

import com.snail.webgame.engine.component.scene.cache.ServerMap;
import com.snail.webgame.engine.component.scene.protocal.server.active.ServerActiveReq;

public class ServerActiveService {

	
	public void activeGateConnect(ServerActiveReq req ,IoSession session)
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
//			String str[] = serverName.split("-");
//			
//			if(str!=null&&str.length>0)
//			{
//				int gateServerId = Integer.valueOf(str[1]);
//			
//				Set<Long> set = RoleInfoMap.getRoleInfoSet();
//				for(Long roleId :set)
//				{
//					RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
//					if(roleInfo !=null&&roleInfo.getLoginStatus()==1)
//					{
//						if(roleInfo.getGateServerId()==gateServerId)
//						{
//							roleInfo.setLoginStatus(0);
//							RoleInfoMap.removeOnlineRoleId(roleInfo.getAccountId());
//							
//						}
//						
//					}
//				}
//			 
//			}
//			else
//			{
//				return ;
//			}
		}
		ServerMap.addServer(serverName,session);
		
 
			
		session.setAttribute("serverName", serverName);
	 
	}
	
	
	
}
