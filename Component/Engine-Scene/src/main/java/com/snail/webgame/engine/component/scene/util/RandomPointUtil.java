package com.snail.webgame.engine.component.scene.util;

import com.snail.webgame.engine.common.info.Point2D;

/**
 * @author wangxf
 * @date 2012-8-5
 * 随机点生成工具类
 */
public class RandomPointUtil {
	/**
	 * 在当前点范围内随机生成一个点
	 * @param x
	 * @param y
	 * @param refreshRange
	 * @param maxX
	 * @param maxY
	 * @return
	 * @author wangxf
	 * @date 2012-8-5
	 */
	public static Point2D getRandomPointInRound(float x, float y, float refreshRange, float maxX, float maxY) {
		float tmpX, tmpY;
		Point2D point = null;
		// 表示全地图刷新
		if (refreshRange == 0) {
			tmpX = getRandomXY(x, maxX, maxX);
			tmpY = getRandomXY(y, maxY, maxY);
			point = new Point2D(tmpX, tmpY);
		}
		// 某点的半径范围内刷新
		else {
			tmpX = getRandomXY(x, refreshRange, maxX);
			tmpY = getRandomXY(y, refreshRange, maxY);
			point = new Point2D(tmpX, tmpY);
		}
		
		return point;
	}
	
	/**
	 * 获取x点refreshRange范围内（大于等于0小于等于maxX）的随机点，
	 * @param x
	 * @param refreshRange
	 * @param maxX
	 * @return
	 * @author wangxf
	 * 2012-8-2
	 */
	private static float getRandomXY(float x, float refreshRange, float maxX) {
		float lower, upper, tmpX;
		lower = x - refreshRange;
		upper = x + refreshRange;
		if (lower < 0) {
			lower = 0;
		}
		if (upper > maxX) {
			upper = maxX;
		}
		tmpX = RandomUtil.getRandomDouble(lower, upper, 1);
		// 先变成整数的double类型，因为调用算法生成出来的路径是整数型的，在判断npc移动停止时有问题
		tmpX = Float.valueOf((int)tmpX);
		return tmpX;
	}

	/**
	 * 随机x轴和z轴给定范围内的随机点
	 * @param minX
	 * @param maxX
	 * @return
	 * @author wangxf
	 * @param maxZ 
	 * @param minZ 
	 * @date 2012-8-20
	 */
	public static Point2D getRandomPoint(float minX, float maxX, float minZ, float maxZ) {
		float x = (float) (Math.random()*(maxX - minX) + minX);
		float z = (float) (Math.random()*(maxZ - minZ) + minZ);
		return new Point2D(x, z);
	}
}
