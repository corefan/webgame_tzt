package com.snail.webgame.engine.component.scene.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.snail.webgame.engine.component.scene.info.MapDoorXMLInfo;

public class MapDoorXMlInfoMap {
	private static HashMap<String, List<MapDoorXMLInfo>> map = new HashMap<String, List<MapDoorXMLInfo>>();
	
	public static void addMapDoor(MapDoorXMLInfo info){
		String key = info.getSceneId() + "&" + info.getMapId();
		List<MapDoorXMLInfo> list = map.get(key);
		if(list == null){
			list = new ArrayList<MapDoorXMLInfo>();
			map.put(key, list);
		}
		list.add(info);
	}
	
	public static MapDoorXMLInfo getMapDoor(int sceneId, String mapId, float x, float y, float z){
		String key = sceneId + "&" + mapId;
		if(map.containsKey(key)){
			List<MapDoorXMLInfo> list = map.get(key);
			if(list != null){
				for(MapDoorXMLInfo info : list){
					if(x >= info.getMinX() && x <= info.getMaxX()
							&& y >= info.getMinY() && y <= info.getMaxX()
							&& z >= info.getMinZ() && z <= info.getMaxZ()){
						return info;
					}
				}
			}
		}
		return null;
	}
}
