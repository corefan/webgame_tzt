package com.snail.webgame.engine.component.scene.cache;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.snail.webgame.engine.component.scene.info.LandMapData;

public class SceneMapCache {
	
	
	public static ConcurrentHashMap<String, LandMapData> map = new ConcurrentHashMap<String, LandMapData>();

	public static void addMap(String mapId, LandMapData mapData)
	{
		map.put(mapId, mapData);
	}
 
	public static LandMapData getMapData(String mapId)
	{
		if(map.containsKey(mapId))
		{
			return map.get(mapId);
		}
		return null;
	}
	
	public static Set<String> keySet(){
		return map.keySet();
	}
 
}
