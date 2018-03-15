package com.snail.webgame.engine.component.scene.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.info.NPCInfo;

/**
 * @author wangxf
 * @date 2012-8-10
 * 缓存死亡的npc
 */
public class NPCDeadMap {
	private static final Logger logger = LoggerFactory.getLogger("logs");
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
		else {
			if(logger.isWarnEnabled()){
				logger.warn("The scene does not hava map : " + mapId);
			}
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
		else {
			if(logger.isWarnEnabled()){
				logger.warn("The scene does not hava map : " + mapId);
			}
		}
		return tmpMap;
	}
	
	public static void removeNPC(String mapId, long npcId) {
		if (map.containsKey(mapId)) {
			map.get(mapId).remove(npcId);
		}
		else {
			if(logger.isWarnEnabled()){
				logger.warn("The scene does not hava map : " + mapId);
			}
		}
	}
	
}
