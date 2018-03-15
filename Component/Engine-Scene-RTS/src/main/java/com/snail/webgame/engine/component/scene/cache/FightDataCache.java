package com.snail.webgame.engine.component.scene.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.snail.webgame.engine.component.scene.common.RoleFight;

public class FightDataCache {
	
	
	private static  ConcurrentHashMap<Long,RoleFight > map = new ConcurrentHashMap<Long,RoleFight>();

	public static void addRoleFight(long fightId,RoleFight roleFight)
	{
		 
		map.put(fightId, roleFight);
	}
 
	public static RoleFight getRoleFight(long fightId)
	{
		return map.get(fightId);
	}

	public static int  getSize()
	{
		return map.size();
	}
	
	public static void removeRoleFight(long fightId)
	{
		 map.remove(fightId);
	}
	
	public static boolean isExistRoleFight(long fightId)
	{
		return map.containsKey(fightId);
	}
}
