package com.snail.webgame.engine.component.scene.cache;

import java.util.HashMap;

import com.snail.webgame.engine.component.scene.info.SkillUpgradeXMLInfo;
import com.snail.webgame.engine.component.scene.info.SkillXMLInfo;

public class SkillXMLInfoMap {
	private static HashMap<Integer,SkillXMLInfo> map = 
		new HashMap<Integer,SkillXMLInfo>();
	/**
	 * 添加技能
	 * @param info
	 */
	public static void addSkillXMLInfo(SkillXMLInfo info)
	{
		map.put(info.getNo(), info);
	}
	/**
	 * 获取技能
	 * @param no
	 * @param level
	 * @return
	 */
	public static SkillUpgradeXMLInfo getSkillXMLInfo(int no,int level)
	{
		if(map.containsKey(no))
		{
			SkillXMLInfo info = map.get(no);
			if(info != null)
			{
				HashMap<Integer, SkillUpgradeXMLInfo> m = info.getLevelMap();
				if(m.containsKey(level))
				{
					return m.get(level);
				}
			}
		}
		return null;
	}
}
