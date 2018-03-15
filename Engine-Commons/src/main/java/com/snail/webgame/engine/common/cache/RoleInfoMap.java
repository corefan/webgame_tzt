package com.snail.webgame.engine.common.cache;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.snail.webgame.engine.common.info.RoleInfo;




/**
 * 用户信息缓存
 * @author cici
 *
 */
public class RoleInfoMap {
	
	private static ConcurrentHashMap<Long, RoleInfo> map = new ConcurrentHashMap<Long, RoleInfo>();
	private static ConcurrentHashMap<Integer, Long> acconlineMap = new ConcurrentHashMap<Integer, Long>();
	private static int maxOnlineNum = 0;
	//保存验证不登陆计费返回的串
	private static  ConcurrentHashMap<String, String> secondStrmap = new ConcurrentHashMap<String, String>();
	
	public static RoleInfo getRoleInfo(long roleId)
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
	
	public static boolean isExitRoleInfo(long roleId)
	{
		return map.containsKey(roleId);
	}
	
	public static Set<Long>  getRoleInfoSet()
	{
		Set<Long> set = map.keySet();
		
		return set;
	}
	
	public static long getOnlineRoleId(int accountId)
	{
		if(acconlineMap.containsKey(accountId))
		{
			return acconlineMap.get(accountId);
		}
		
		return 0;
	}
	
	public static void addOnlineRoleId(int accountId,long roleId)
	{
		//maxOnlineNum可能产生脏数据
		int k = getOnlineSize();
		if(maxOnlineNum<k)
		{
			maxOnlineNum = k;
		}
		
		acconlineMap.put(accountId, roleId);
	}
	
	public static void removeOnlineRoleId(int accountId)
	{
		acconlineMap.remove(accountId);
	}
	
	public static int getOnlineSize()
	{
		return acconlineMap.size();
	}
	
	public static int getMaxOnlineSize()
	{
		return maxOnlineNum;
	}
	
	/**
	 * 获取在线角色信息缓存
	 * @return
	 */
	public static ConcurrentHashMap<Integer,Long> getOnlineRoleInfoMap()
	{
		return acconlineMap;
	}
	
	/**
	 * 保存计费验证返回的串
	 * @param account
	 * @param secondStr
	 */
	public static void addSecondStr(String account,String secondStr)
	{
		secondStrmap.put(account, secondStr);
	}
	
	/**
	 * 获取计费验证返回的串
	 * @param account
	 * @return
	 */
	public static String getSecondStr(String account)
	{
		return secondStrmap.get(account);
	}
	
	/**
	 * 移除计费验证返回的串
	 * @param account
	 */
	public static void removeSecondStr(String account)
	{
		secondStrmap.remove(account);
	}
	
	
	
	/**
	 * 角色名查找角色
	 * @param roleAcount
	 * @return
	 */
	public static RoleInfo getRoleInfoByName(String roleName){
		
		for(long roleId : map.keySet())
		{
			RoleInfo roleInfo = map.get(roleId);
			if(roleInfo != null && roleName.equals(roleInfo.getRoleName()))
			{
				return roleInfo;
				
			}
		}
		
		return null;
		
	}
	public static ConcurrentHashMap<Long, RoleInfo> getMap() {
		return map;
	}
	public static void setMap(ConcurrentHashMap<Long, RoleInfo> map) {
		RoleInfoMap.map = map;
	}
	
}
