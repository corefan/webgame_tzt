package com.snail.webgame.engine.component.scene.util;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author wangxf
 * @date 2012-8-1
 * 随机数工具类
 */
public class RandomUtil {
	
	public static float getRandomDouble(double lower, double upper, int digit) {
		String formatStr = "###.";
		for (int i = 0; i < digit; i++) {
			formatStr += "0";
		}
		DecimalFormat format = new DecimalFormat(formatStr);
		float d = (float) (Math.random()*(upper - lower) + lower);
		d = Float.valueOf(format.format(d));
		return d;
		
	}
	
	/**
	 * 获取min和max之间的一个随机数
	 * @param min
	 * @param max
	 * @return
	 * @author wangxf
	 * @date 2012-9-5
	 */
	public static int getRandomInt(int min, int max) {
		Random random = new Random();
		int i = min + random.nextInt(max - min + 1);
		return i;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.println(getRandomDouble(5.1, 10.5, 1));
		}
	}
}
