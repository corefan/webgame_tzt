package com.snail.webgame.engine.gate.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.snail.webgame.engine.gate.common.RoleReqTime;

public class RoleReqTimeMap {

	public static  ConcurrentHashMap<Integer,ConcurrentHashMap<Integer,RoleReqTime>> map = 
		new ConcurrentHashMap<Integer,ConcurrentHashMap<Integer,RoleReqTime>>();


	public static Object obj = new Object();
	
	
 
	public static RoleReqTime getRoleReqTime(int roleId,int msgType )
	{
		
		if(map.containsKey(roleId))
		{
			return map.get(roleId).get(msgType)	;
		}
		return null;
	}
	
 
 
	public static void addRoleReqTime(int roleId,int msgType, RoleReqTime reqTime)
	{
		if(map.containsKey(roleId))
		{
			map.get(roleId).put(msgType, reqTime);
		}
		else
		{
			synchronized(obj)
			{
				if(map.containsKey(roleId))
				{
					map.get(roleId).put(msgType, reqTime);
				}
				else
				{
					ConcurrentHashMap<Integer,RoleReqTime> timeMap = new ConcurrentHashMap<Integer,RoleReqTime>();
					timeMap.put(msgType, reqTime);
					map.put(roleId, timeMap);
				}
				
			}
		}
	}
	
	 
	
	public static void removeRoleReq(int roleId)
	{
		if(map.containsKey(roleId))
		{
			map.remove(roleId);
		}
	}
	 
}
