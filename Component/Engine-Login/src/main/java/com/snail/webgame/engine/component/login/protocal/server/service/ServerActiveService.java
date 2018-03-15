package com.snail.webgame.engine.component.login.protocal.server.service;


import org.apache.mina.common.IoSession;

import com.snail.webgame.engine.component.login.cache.ServerMap;
import com.snail.webgame.engine.component.login.protocal.server.active.ServerActiveReq;
import com.snail.webgame.engine.component.login.protocal.server.time.SysTimeResp;

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
	
	/**
	 * 获得系统当前时间
	 * @return
	 */
	public SysTimeResp getSysTime()
	{
		SysTimeResp resp = new SysTimeResp();
		resp.setResult(1);
		resp.setCurrTime(System.currentTimeMillis());
		
		
		return resp;
	}
	
}
