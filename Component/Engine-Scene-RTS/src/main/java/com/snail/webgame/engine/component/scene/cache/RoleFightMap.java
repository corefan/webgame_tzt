package com.snail.webgame.engine.component.scene.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
 

public class RoleFightMap {
	
	public static  ConcurrentHashMap<Integer,List<Long>> map 
	= new ConcurrentHashMap<Integer,List<Long>>();

	private static Object obj = new Object();
	
	public static void addRoleInFight(int roleId,long fightId )
	{
		
		if(map.containsKey(roleId))
		{
			List<Long> list =  map.get(roleId) ;
			if(!list.contains(fightId))
			{
				list.add(fightId);
			}
		}
		else 
		{
			synchronized(obj)
			{
				List<Long> list = Collections.synchronizedList( new ArrayList<Long>());
				
				list.add(fightId);
				
				map.put(roleId, list);
			}
		}
	}
	
	
	public static List<Long> getRoleInfo(int roleId)
	{
		if(map.containsKey(roleId))
		{
			return map.get(roleId);
		}
		return null;
	}
	public static void removeRoleFight(int roleId,long fightId)
	{
		if(map.containsKey(roleId))
		{
			map.get(roleId).remove((Object)fightId);
		}
	}
 
	public static void removeRoleFight(int roleId)
	{
		 map.remove(roleId);
	}
	
	public static void clear()
	{
		map.clear();
	}
}
