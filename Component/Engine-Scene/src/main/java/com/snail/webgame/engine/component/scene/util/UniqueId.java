package com.snail.webgame.engine.component.scene.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangxf
 * @date 2012-8-1
 * 
 */
public class UniqueId {
	private static long tmpID = 0;
	private static volatile long dropInfoId = 1;
	private static volatile long dropBagId = 1;
	
	// 掉落物品ID
	private static String DROPINFOID = "DROPINFOID";
	// 掉落包ID
	private static String DROPBAGID = "DROPBAGID";

	private static boolean tmpIDlocked = false;

	public static long getUniqueId() {
		long ltime = 0;
		while (true) {
			if (tmpIDlocked == false) {
				tmpIDlocked = true;
				ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS")
						.format(new Date()).toString());
				if (tmpID < ltime) {
					tmpID = ltime;
				} else {
					tmpID = tmpID + 1;
					ltime = tmpID;
				}
				tmpIDlocked = false;
				return ltime;
			}
		}
	}
	
	public static long getUniqueId(String type) {
		long id = 0;
		if (DROPINFOID.equals(type)) {
			id = dropInfoId++;
		}
		else if (DROPBAGID.equals(type)) {
			id = dropBagId++;
		}
		return id;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			long id = getUniqueId();
			System.out.println(id);
		}
		System.out.println(Long.MAX_VALUE);
	}
}
