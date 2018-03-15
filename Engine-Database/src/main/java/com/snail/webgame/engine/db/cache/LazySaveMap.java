package com.snail.webgame.engine.db.cache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.snail.webgame.engine.common.Flag;
import com.snail.webgame.engine.common.to.BaseTO;

/**
 * 定时存储缓存
 * @author zenggy
 *
 */
public class LazySaveMap {
	/**
	 * 缓存数据<数据库名称, <对象类名&主键, 存储对象>>
	 */
	private static Map<String, LinkedHashMap<String, BaseTO>> map = new HashMap<String, LinkedHashMap<String, BaseTO>>();
	public static final String SPLIT_FLAG = "&";

	/**
	 * 得到定时缓存对象
	 * @param to 对象
	 * @param dbName 数据库名称 
	 * @return 缓存对象
	 */
	public static BaseTO getLazyTO(BaseTO to, String dbName) {
		String key = to.getClass().getName() + SPLIT_FLAG + to.getId();

		if (to.getSaveMode() == BaseTO.LAZY && map.containsKey(dbName)) {
			Map<String, BaseTO> map1 = map.get(dbName);
			if (map1 != null && map1.containsKey(key))
				return map1.get(key);
		}
		return null;
	}

	/**
	 * 获得指定数据库的缓存
	 * @param dbName 数据库名称
	 * @return 缓存对象
	 */
	public static Map<String, BaseTO> getLazyMap(String dbName) {
		if (map.containsKey(dbName))
			return map.get(dbName);
		return null;
	}
	
	/**
	 * 增加缓存对象
	 * @param to 缓存对象
	 * @param dbName 数据库名称
	 */
	public static void addLazyTO(BaseTO to, String dbName) {
		if (to.getSaveMode() == BaseTO.LAZY) {
			synchronized (Flag.OBJ_DB) {
				String key = to.getClass().getName() + SPLIT_FLAG
						+ to.getId();

				LinkedHashMap<String, BaseTO> map1 = map.get(dbName);
				if (map1 == null) {
					map1 = new LinkedHashMap<String, BaseTO>();
					map.put(dbName, map1);
				}
				map1.put(key, to);
			}
		}
	}

	/**
	 * 删除缓存对象
	 * @param to 缓存对象
	 * @param dbName 数据库名称
	 */
	public static void removeLazyTO(BaseTO to, String dbName) {
		if (to.getSaveMode() == BaseTO.LAZY) {
			synchronized (Flag.OBJ_DB) {
				String key = to.getClass().getName() + SPLIT_FLAG
						+ to.getId();

				if (map.containsKey(dbName)) {
					Map<String, BaseTO> map1 = map.get(dbName);
					if (map1 != null && map1.containsKey(key))
						map1.remove(key);
				}

			}
		}
	}

	/**
	 * 判断缓存数据是否为空
	 * @return
	 */
	public static boolean isEmpty() {
		return map.isEmpty();
	}

	public static Set<String> keySet() {
		return map.keySet();
	}

	/**
	 * 清除缓存对象
	 */
	public static void clear() {
		map.clear();
	}
}
