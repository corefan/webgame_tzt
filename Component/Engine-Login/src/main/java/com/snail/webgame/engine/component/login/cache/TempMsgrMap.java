package com.snail.webgame.engine.component.login.cache;

 
import java.util.concurrent.ConcurrentHashMap;

import org.epilot.ccf.core.protocol.Message;

 
 
public class TempMsgrMap {
	
	public static  ConcurrentHashMap<Integer,Message> map = new ConcurrentHashMap<Integer,Message>();

	public static Message getMessage(int sequenceId)
	{
		return map.get(sequenceId);
	}
	public static void addMessage(int sequenceId,Message message)
	{
		map.put(sequenceId, message);
	}
	public static void removeMessage(int sequenceId)
	{
		map.remove(sequenceId);
	}
 
}
