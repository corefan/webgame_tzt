package com.snail.webgame.engine.component.scene.cache;

import java.util.HashMap;

import com.snail.webgame.engine.component.scene.info.BuffXMlInfo;

public class BuffXMlInfoMap {
	
	private static HashMap<Integer,BuffXMlInfo> wmap = 
		new HashMap<Integer,BuffXMlInfo>();
	
	public static void addBuffXMLInfo(BuffXMlInfo info){
		wmap.put(info.getBuffNo(), info);
	}
	
	public static BuffXMlInfo getBuffXMLInfo(int no){
		return wmap.get(no);
	}
	
	
}
