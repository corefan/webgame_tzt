package com.snail.webgame.engine.component.scene.cache;

 
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import com.snail.webgame.engine.component.scene.common.InSceneRoleInfo;

 
 

public class RoleInFightCache {
	
	
	public static  ConcurrentHashMap<Long,Hashtable<Integer,InSceneRoleInfo> > map 
	= new ConcurrentHashMap<Long,Hashtable<Integer,InSceneRoleInfo>>();

	private static Object obj = new Object();
	public static void addRoleInFight(long fightId,InSceneRoleInfo roleInfo )
	{
		
		if(map.containsKey(fightId))
		{
			map.get(fightId).put(roleInfo.getRoleId(), roleInfo);
		}
		else 
		{
			synchronized(obj)
			{
				Hashtable<Integer,InSceneRoleInfo> table = new Hashtable<Integer,InSceneRoleInfo>();
				
				table.put(roleInfo.getRoleId(), roleInfo);
				
				map.put(fightId, table);
			}
		}
	}
	
	
	public static InSceneRoleInfo getRoleInfo(long fightId,int roleId)
	{
		if(map.containsKey(fightId))
		{
			return map.get(fightId).get(roleId);
		}
		return null;
	}
	public static Hashtable<Integer,InSceneRoleInfo> getRoleInfoMap(long fightId)
	{
		if(map.containsKey(fightId))
		{
			return map.get(fightId);
		}
		return null;
	}
	
	
	
	public static boolean isExistRoleInFight(long fightId,int roleId)
	{
		if(map.containsKey(fightId))
		{
			 return map.get(fightId).containsKey(roleId);
		}
		return false;
	}

	public static void removeRoleFight(long fightId,int roleId)
	{
		if(map.containsKey(fightId))
		{
			map.get(fightId).remove(roleId);
		}
	}
	
	public static void removeRoleFight(long fightId)
	{
		 map.remove(fightId);
	}
	
	public static void clear()
	{
		map.clear();
	}
	 
}
