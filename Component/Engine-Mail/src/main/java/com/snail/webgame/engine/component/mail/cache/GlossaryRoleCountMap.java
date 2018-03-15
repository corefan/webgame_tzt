package com.snail.webgame.engine.component.mail.cache;

import java.util.ArrayList;
import java.util.List;

public class GlossaryRoleCountMap {
	
	private static List<String> list = new ArrayList<String>();
	
	public static void addRoleCount(String roleCount)
	{
		if(roleCount != null && roleCount.length() > 0)
		{
			if(!list.contains(roleCount))
			{
				list.add(roleCount);
			}
		}
	}
	
	public static boolean isExist(String roleCount)
	{
		return list.contains(roleCount);
	}
	
	public static void removeAll()
	{
		list.clear();
	}

}
