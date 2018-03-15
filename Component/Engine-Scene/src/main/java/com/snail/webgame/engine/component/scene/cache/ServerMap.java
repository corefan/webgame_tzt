package com.snail.webgame.engine.component.scene.cache;

import java.util.Hashtable;
import java.util.Set;

import org.apache.mina.common.IoSession;

/**
 * 场景服务器上的各种子系统
 * @author cici
 *
 */
public class ServerMap {
	
	
	public static  Hashtable<String,IoSession> map = new Hashtable<String,IoSession>();

	public static  void addServer(String serverName ,IoSession session)
	{
		 
		map.put(serverName, session);
	}
 
	public static  IoSession getServerSession(String serverName)
	{
		return map.get(serverName);
	}

	public static Set<String>  getMapSet()
	{
		return map.keySet();
	}
	
	public static void removeServer(String serverName)
	{
		 map.remove(serverName);
	}
}
