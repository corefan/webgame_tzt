package com.snail.webgame.unite.util;

import java.util.Random;

/**
 * 参数转换工具
 * @author panxj
 * @version 1.0 2010-7-22
 */

public class ParamUtil2Change {
	
	/**
	 * Str2Int
	 * @param str
	 * @return
	 */
	public static int getInt(String s_param)
	{
		return ParamUtil4Verify.isDecNum(s_param)? Integer.valueOf(s_param) : 0;
	}
	
	/**
	 * Str2Byte
	 * @param s_param
	 * @return
	 */
	public static byte getByte(String s_param)
	{
		return ParamUtil4Verify.isDecNum(s_param) ? Byte.valueOf(s_param) : 0;
	}
	
	/**
	 * Int2HexStr
	 * @param param
	 * @return
	 */
	public static String getHexInt(int n_param)
	{
		return "0x"+Integer.toHexString(n_param).toUpperCase();
	}
	
	/**
	 * A-Z 0-9
	 * @return
	 */
	public static char getRadom4Char()
	{
		if(new Random().nextBoolean())
		{
			return (char) (97 + new Random().nextInt(25));
		}
		else
		{
			return (char) (48 + new Random().nextInt(8));
		}		
	}
	
	/**
	 * 获得颜色 格式:"0xffffff"
	 * 本方法非多线程的
	 * @return
	 */
	public static String getRadom4Color()
	{
		StringBuilder color = new StringBuilder("0x");
		for(int i = 0;i<6;i++)
		{
			if(new Random().nextBoolean())
			{
				color.append((char) (97 + new Random().nextInt(5)));
			}
			else
			{
				color.append((char) (48 + new Random().nextInt(8)));
			}	
		}
		return color.toString();		
	}
}
