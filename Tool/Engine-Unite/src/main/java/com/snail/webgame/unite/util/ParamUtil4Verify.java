package com.snail.webgame.unite.util;

/**
 * 验证变量和转换
 * @author panxj
 * @version 1.0 2010-3-24
 */

public class ParamUtil4Verify {

	/**
	 * ip为0.0.0.0-255.255.255.255
	 * @param s_address
	 * @return
	 */	
	public static boolean isAddress(String s_address)
	{
		if(s_address != null)
		{
			return s_address.matches("^((25[0-5])|(2[0-4]\\d)|(1\\d{2})|(\\d{1,2}))(\\.((25[0-5])|(2[0-4]\\d)|(1\\d{2})|(\\d{1,2}))){3}$");
		}
		return false;
	}	
	
	/**
	 * 端口为1-65535
	 * @param s_port
	 * @return
	 */	
	public static boolean isPort(String s_port)
	{
		if(s_port != null)
		{
			return s_port.matches("^([1-9]\\d{0,3})|(6553[0-5])|(655[0-2]\\d)|(65[0-4]\\d{2})|(6[0-4]\\d{3})|([1-5]\\d{4})$");
		}
		return false;
	}

	/**
	 * 非空
	 * @param s_param
	 * @return
	 */
	public static boolean isUnNull(String s_param)
	{
		if(s_param != null)
		{
			return s_param.trim().matches("^.+$");
		}
		return false;
	}	
	
	/**
	 * 是否是十进制数字
	 * @param s_param
	 * @return
	 */
	public static boolean isDecNum(String s_param)
	{
		if(s_param != null)
		{
			return s_param.matches("^\\d+$");
		}
		return false;
	}
	
	/**
	 * 是否是十六进制数字
	 * @param s_param
	 * @return
	 */
	public static boolean isHexNum(String s_param)
	{
		if(s_param != null)
		{			
			return s_param.matches("^0[Xx][a-fA-F\\d]+$");
		}
		return false;
	}
}
