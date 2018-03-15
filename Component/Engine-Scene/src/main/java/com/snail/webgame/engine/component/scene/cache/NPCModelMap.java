package com.snail.webgame.engine.component.scene.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.info.NPCModelInfo;

/**
 * @author wangxf
 * @date 2012-8-1
 * 
 */
public class NPCModelMap {
	private final static Logger logger = LoggerFactory.getLogger("logs");
	
	/**
	 * 缓存NPC模型信息
	 * key : no 模型id
	 */
	public static Map<Integer, NPCModelInfo> npcModelMap = new HashMap<Integer, NPCModelInfo>();
	
	public static void addNPCModel(int modelNo, NPCModelInfo npcModelInfo) {
		if (npcModelMap.containsKey(modelNo)) {
			if(logger.isWarnEnabled()){
				logger.warn("The NPCModel.xml config a same NPCModel, no = " + modelNo);
			}
		}
		else {
			npcModelMap.put(modelNo, npcModelInfo);
		}
	}
	
	public static NPCModelInfo getNPCModel(int modelNo) {
		NPCModelInfo npcModelInfo = null;
		if (!npcModelMap.containsKey(modelNo)) {
			if(logger.isWarnEnabled()){
				logger.warn("The NPCModel.xml config don't have the NPCModel, no = " + modelNo);
			}
		}
		else {
			npcModelInfo = npcModelMap.get(modelNo);
		}
		return npcModelInfo;
	}
}
