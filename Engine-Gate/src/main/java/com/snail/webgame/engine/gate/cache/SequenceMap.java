package com.snail.webgame.engine.gate.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.common.IoSession;

public class SequenceMap {
	
	
public static  ConcurrentHashMap<Integer,IoSession> map = new ConcurrentHashMap<Integer,IoSession>();
	
	public static IoSession getSession(int sequenceId)
	{
		return map.get(sequenceId);
	}
	public static void addSession(int sequenceId,IoSession session)
	{
		map.put(sequenceId, session);
	}
	public static void removeSession(int sequenceId)
	{
		map.remove(sequenceId);
	}
	public static int getSize()
	{
		return map.size();
	}
}
