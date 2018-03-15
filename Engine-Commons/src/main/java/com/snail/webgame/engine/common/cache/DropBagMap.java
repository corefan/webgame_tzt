package com.snail.webgame.engine.common.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.snail.webgame.engine.common.info.DropBag;

/**
 * @author wangxf
 * @date 2012-9-5
 * 掉落包缓存类
 */
public class DropBagMap {
	public static Map<Long, DropBag> map = new HashMap<Long, DropBag>();
	
	public static DropBag getDropBag(long id)
	{
		DropBag dropBag = map.get(id);
		if (dropBag != null) {
			long dropTime = dropBag.getDropTime();
			int clearTimePeriod = dropBag.getClearTimePeriod();
			long currTime = System.currentTimeMillis();
			// 如果达到掉落包消失的时间，则从缓存中删除
			if (currTime > dropTime + clearTimePeriod * 1000) {
				removeDropBag(id);
				//GameMap3D gameMap = SceneGameMapFactory.getGameMap(dropBag.getMapId());
				// 从地图缓存中删除该掉落物品
				//gameMap.delDropBag((int)dropBag.getX(), (int)dropBag.getY(), (int)dropBag.getZ(), id);
				dropBag = null;
			}
		}
		return dropBag;
	}
	
	public static void addDropBag(long dropId, DropBag info)
	{
		map.put(dropId, info);
	}
	
	public static void removeDropBag(long dropId)
	{
		map.remove(dropId);
	}
	
	public static int getSize()
	{
		return map.size();
	}
	
	public static boolean isExitDropBag(long dropId)
	{
		return map.containsKey(dropId);
	}
	
	public static Set<Long> getDropBagSet()
	{
		Set<Long> set = map.keySet();
		
		return set;
	}
	
}
