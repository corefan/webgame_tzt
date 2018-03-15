package com.snail.webgame.engine.gate.cache;

 
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.common.IoSession;

 
/**
 * 连接服务器的Session的map
 * @author cici
 *
 */
public class ServerMap {
	
	public static  Hashtable<String,IoSession> map = new Hashtable<String,IoSession>();

	public static IoSession getSession(String serverName)
	{
		return map.get(serverName);
	}
	public static void addSession(String serverName,IoSession session)
	{
		map.put(serverName, session);
	}
	public static void removeSession(String serverName)
	{
		map.remove(serverName);
	}
 
}
