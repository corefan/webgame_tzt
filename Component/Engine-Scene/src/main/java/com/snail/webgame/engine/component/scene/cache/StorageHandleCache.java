package com.snail.webgame.engine.component.scene.cache;

import java.util.HashMap;
import java.util.Map;

import com.snail.webgame.engine.component.scene.protocal.storage.StorageHandle;

public class StorageHandleCache {
	private static Map<Integer, StorageHandle> map = new HashMap<Integer, StorageHandle>();
	
	public static void addHandle(StorageHandle handle){
		if(handle != null)
			map.put(handle.hashCode(), handle);
	}
	
	public static StorageHandle getHandle(int hashcode){
		return map.get(hashcode);
	}
}
