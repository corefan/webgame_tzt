package com.snail.webgame.engine.component.scene.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.snail.webgame.engine.component.scene.common.LandMapData;
 

public class FightMapCache {
	
	
	public static  ConcurrentHashMap<String,LandMapData > map = new ConcurrentHashMap<String,LandMapData>();

	public static void addMap(String mapId,LandMapData mapData)
	{
		 
		map.put(mapId, mapData);
	}
 
	public static byte [][] getMapCopy(String mapId)
	{
		if(map.containsKey(mapId))
		{
			byte [][] mapDate = map.get(mapId).getMapData();
			byte [][] temp  = new byte[mapDate.length][];
			
			for(int i =0;i<mapDate.length;i++)
			{
				temp[i] = new byte[mapDate[i].length];
				System.arraycopy(mapDate[i], 0, temp[i], 0, mapDate[i].length);
			}
			
			return temp;
		}
		return null;
	}
	public static float [][] getMapElevationCopy(String mapId)
	{
		if(map.containsKey(mapId))
		{
			float [][] mapDate = map.get(mapId).getElevation();
			float [][] temp  = new float[mapDate.length][];
			
			for(int i =0;i<mapDate.length;i++)
			{
				temp[i] = new float[mapDate[i].length];
				System.arraycopy(mapDate[i], 0, temp[i], 0, mapDate[i].length);
			}
			
			return temp;
		}
		return null;
	}
	public static LandMapData getLandMapData(String mapId)
	{
		if(map.containsKey(mapId))
		{
			 
			return map.get(mapId);
		}
		return null;
	}
 
}
