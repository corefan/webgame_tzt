package com.snail.webgame.game.cache;

 
import java.util.Hashtable;
 

import org.apache.mina.common.IoSession;

 
/**
 * 连接服务器的Session的map
 * @author cici
 *
 */
public class ChargeServerMap {
	
	public static Hashtable<String,IoSession> map = new Hashtable<String,IoSession>();

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
