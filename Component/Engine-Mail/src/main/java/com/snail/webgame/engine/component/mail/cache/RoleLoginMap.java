package com.snail.webgame.engine.component.mail.cache;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.snail.webgame.engine.component.mail.common.RoleInfo;




/**
 * 用户信息缓存
 * @author tangjie
 *
 */
public class RoleLoginMap {
	
	public static  ConcurrentHashMap<Long,RoleInfo> map = new ConcurrentHashMap<Long,RoleInfo>();
	
	public static RoleInfo getRoleInfo(long  roleId)
	{
		return map.get(roleId);
	}
	public static void addRoleInfo(long roleId,RoleInfo info)
	{
		map.put(roleId, info);
	}
	public static void removeRoleInfo(long roleId)
	{
		map.remove(roleId);
	}
	public static int getSize()
	{
		return map.size();
	}
	public static void clear()
	{
		map.clear();
	}
	public static boolean isExitRoleInfo(long roleId)
	{
		return map.containsKey(roleId);
	}
	
	public static Set<Long> getSet()
	{
		return map.keySet();
	}
	 
}
