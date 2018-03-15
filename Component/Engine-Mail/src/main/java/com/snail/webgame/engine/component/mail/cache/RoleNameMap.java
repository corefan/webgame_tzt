package com.snail.webgame.engine.component.mail.cache;

import java.util.concurrent.ConcurrentHashMap;




/**
 * 用户名称
 * @author tangjie
 *
 */
public class RoleNameMap {
	
	public static  ConcurrentHashMap<String,Long> map = new ConcurrentHashMap<String,Long>();
	
	public static Long getRoleInfo(String roleName)
	{
		return map.get(roleName);
	}
	public static void addRoleInfo(String roleName,long roleId)
	{
		map.put(roleName, roleId);
	}
	public static void removeRoleInfo(String roleName)
	{
		map.remove(roleName);
	}
	public static int getSize()
	{
		return map.size();
	}
	
	public static boolean isExitRoleInfo(String roleName)
	{
		return map.containsKey(roleName);
	}
 
	public static void clear()
	{
		map.clear();
	}
}
