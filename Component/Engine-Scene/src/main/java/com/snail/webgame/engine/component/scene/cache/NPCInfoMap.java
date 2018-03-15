package com.snail.webgame.engine.component.scene.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.snail.webgame.engine.common.info.NPCInfo;

/**
 * @author wangxf
 * @date 2012-7-27
 * 缓存npc实例对象
 */
public class NPCInfoMap {
	//private final static Logger logger = LoggerFactory.getLogger("logs");

	/**
	 * 缓存每个地图中的npc信息
	 * key : mapId
	 */
	public static Map<String, Map<Long, NPCInfo>> map = new HashMap<String, Map<Long,NPCInfo>>();
	
	public static NPCInfo getNPCInfo(String mapId, long id)
	{
		NPCInfo npcInfo = null;
		if (map.containsKey(mapId)) {
			npcInfo = map.get(mapId).get(id);
		}
		return npcInfo;
	}
	
	public static void addNPCInfo(String mapId, NPCInfo info)
	{
		if (map.containsKey(mapId)) {
			map.get(mapId).put(info.getId(), info);
		}
		else {
			Map<Long, NPCInfo> intMap = new ConcurrentHashMap<Long, NPCInfo>();
			intMap.put(info.getId(), info);
			map.put(mapId, intMap);
		}
		
	}
	
	public static Map<Long, NPCInfo> getNPCInfo(String mapId) {
		Map<Long, NPCInfo> tmpMap = null;
		if (map.containsKey(mapId)) {
			tmpMap = map.get(mapId);
		}
		return tmpMap;
	}
	
}
