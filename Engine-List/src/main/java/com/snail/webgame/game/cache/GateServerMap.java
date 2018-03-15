package com.snail.webgame.game.cache;


import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.snail.webgame.game.common.GateServerInfo;

/**
 * 缓存游戏服务器的信息
 * @author cici
 *
 */
public class GateServerMap {
	
	
	private static  ConcurrentHashMap<Integer,HashMap<Integer,GateServerInfo>> map = new ConcurrentHashMap<Integer,HashMap<Integer,GateServerInfo>>();


	
	public static void addGateServer(GateServerInfo serverInfo)
	{
		int groupServerId = serverInfo.getGroupServerId();
		int gateServerId = serverInfo.getGateServerId();
	
		
		if(map.containsKey(groupServerId))
		{
			HashMap<Integer, GateServerInfo> gateMap  = map.get(groupServerId);
			gateMap.put(gateServerId, serverInfo);
		}
		else
		{
			HashMap<Integer, GateServerInfo> gateMap  = new HashMap<Integer, GateServerInfo>();
			gateMap.put(gateServerId, serverInfo);
			map.put(groupServerId, gateMap);
		}
		
	}
	
	public static GateServerInfo getGateServerId(int groupServerId ,int gateServerId)
	{
		if(map.containsKey(groupServerId))
		{
			return map.get(groupServerId).get(gateServerId);
		}
		else
		{
			return null;
		}
	}
	
	public static HashMap<Integer,GateServerInfo> getGateMap (int groupServerId)
	{
		return map.get(groupServerId);
	}
	
	public static void removeGateServer(int groupServerId,int gateServerId)
	{
		if(map.containsKey(groupServerId))
		{
			map.get(groupServerId).remove(gateServerId);
		}
	}
}
