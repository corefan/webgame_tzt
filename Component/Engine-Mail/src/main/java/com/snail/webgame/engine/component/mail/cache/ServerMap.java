package com.snail.webgame.engine.component.mail.cache;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.common.IoSession;

/**
 * 游戏服务器上的各种子系统
 * @author cici
 *
 */
public class ServerMap {
	
	
	public static  Hashtable<String,IoSession> map = new Hashtable<String,IoSession>();

	public static void addServer(String serverName ,IoSession session)
	{
		 
		map.put(serverName, session);
	}
 
	public static IoSession getServerSession(String serverName)
	{
		return map.get(serverName);
	}

	
	
	public static void removeServer(String serverName)
	{
		 map.remove(serverName);
	}
}
