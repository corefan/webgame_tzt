package com.snail.webgame.engine.component.login.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.snail.webgame.engine.common.Flag;
import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.component.login.info.RoleLoginQueueInfo;
import com.snail.webgame.engine.component.login.thread.RoleLoginQueueThread;

public class RoleLoginQueueInfoMap 
{
	private static List<String> list = new ArrayList<String>();
	
	//<account, RoleLoginQueueInfo>
	private static ConcurrentHashMap<String,RoleLoginQueueInfo> map = new ConcurrentHashMap<String, RoleLoginQueueInfo>();
	
	private static List<String> loginList = new ArrayList<String>();
	
	/**
	 * 添加队列信息
	 * @param info
	 */
	public static void addQueueInfo(RoleLoginQueueInfo info)
	{
		synchronized (Flag.OBJ_LOGIN_QUEUE) 
		{
			if(!list.contains(info.getAccount()))
			{
				list.add(info.getAccount());
			}
		}

		map.put(info.getAccount(), info);
		
		GameValue.CHECK_LOGIN_QUEUE = true;
		RoleLoginQueueThread.setI();
	}
	
	/**
	 * 获取自己的队列位置
	 * @param info
	 * @return
	 */
	public static int getIndex(RoleLoginQueueInfo info)
	{
		if(list.contains(info.getAccount()))
		{
			
		}
		else
		{
			addQueueInfo(info);
		}
		
		return list.indexOf(info.getAccount()) + 1;
	}
	
	/**
	 * 获取自己的队列位置
	 * @param account
	 * @return
	 */
	public static int getIndex(String account)
	{
		if(list.contains(account))
		{
			return list.indexOf(account) + 1;
		}
		
		return -1;
	}
	
	/**
	 * 移除排队信息
	 * @param account
	 */
	public static void removeQueueInfo(String account)
	{
		synchronized (Flag.OBJ_LOGIN_QUEUE)
		{
			list.remove(account);
			GameValue.CHECK_LOGIN_QUEUE = true;
			RoleLoginQueueThread.setI();
		}
		
		map.remove(account);
	}
	
	/**
	 * 获取排队信息
	 * @param account
	 * @return
	 */
	public static RoleLoginQueueInfo getRoleLoginQueueInfo(String account)
	{
		return map.get(account);
	}
	
	/**
	 * 所有正在排队的人数
	 * @return
	 */
	public static int getAllNum()
	{
		return map.size();
	}
	
	public static Set<String> getKeySet()
	{
		return map.keySet();
	}
	
	public static List<String> getList()
	{
		return list;
	}
	
	public static void removeAll()
	{
		list.clear();
		map.clear();
	}
	
	public static String getRoleName(String account)
	{
		String roleName = null;
		if(map.containsKey(account))
		{
			roleName = map.get(account).getRoleName();
		}
		return roleName;
	}
	
	public static void addLoginList(String account)
	{
		loginList.add(account);
	}
	
	public static boolean isMessageLogin(String account)
	{
		return loginList.contains(account);
	}
	
	public static void removeMessageAccount(String account)
	{
		loginList.remove(account);
	}
}
