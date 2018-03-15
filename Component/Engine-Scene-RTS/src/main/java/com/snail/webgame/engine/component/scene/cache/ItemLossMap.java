package com.snail.webgame.engine.component.scene.cache;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

 



public class ItemLossMap {
	
	//<roleId, <itemNo, itemNum>>
	public   ConcurrentHashMap<Integer,HashMap<Integer,Integer>> map =new ConcurrentHashMap<Integer,HashMap<Integer,Integer>>();


	public synchronized void addItemLoss(int roleId,int itemNo,int itemNum)
	{
		
		if(!map.containsKey(roleId))
		{
			HashMap<Integer,Integer> itemMap = new HashMap<Integer,Integer>();
			itemMap.put(itemNo, itemNum);
			map.put(roleId, itemMap);
		}
		else
		{
			HashMap<Integer,Integer> itemMap = map.get(roleId) ;
			if(itemMap.containsKey(itemNo))
			{
				int num = itemMap.get(itemNo)+itemNum;
				
				itemMap.put(itemNo, num);
			}
			else
			{
				itemMap.put(itemNo, itemNum);
			}
		}
	}
	
	public void clear()
	{
		map.clear();
	}
 
	public HashMap<Integer,Integer> getMap(int roleId)
	{
		
		return map.get(roleId);
	}
	
 
 
}
