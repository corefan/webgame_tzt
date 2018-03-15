package com.snail.webgame.engine.component.login.core;

 
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.component.login.cache.WordListMap;
import com.snail.webgame.engine.component.login.config.GameOtherConfig;

public class RoleService
{

	/**
	 * 判断是否能登录
	 * @param flag 0-重置登录数量 1-登录
	 * @return
	 */
	public synchronized static boolean isCanLogin(int flag)
	{
		 if(flag==0)
		 {
			GameValue.LOGIN_FREQ_NUM = 0;
			return true;
		 }
		 else  if(flag==1)
		 {
			if(GameOtherConfig.getInstance().getLoginFreqNum() > 0
						&&GameOtherConfig.getInstance().getLoginFreq() > 100)
			{
				 if(GameValue.LOGIN_FREQ_NUM>=GameOtherConfig.getInstance().getLoginFreqNum())
				 {
					 return false;
				 }
				 else
				 {
					 GameValue.LOGIN_FREQ_NUM = GameValue.LOGIN_FREQ_NUM +1;
					 return true;
				 }	
			}
			else
			{
				return true;
			}
			
		 }
		 else
		 {
			 return false;
		 }

	}
	
	
	/**
	 * 检查名字是否符合规则
	 * @param name
	 * @return
	 */
	public static boolean checkName(String name)
	{
		if(name!=null&&name.trim().length()!=0&&name.trim().length()<=GameValue.MAX_NAME_LENGTH)
		{
			Document doc = null;
			try {
				//验证名称为乱码，(例: 玩家有英雄名字为"花’落無痕"、"’雲隨風︶ㄣ"，此号长期未上线被暂时删除，英雄信息被保存为XML格式存入ROLE_REBACK_LOG表，
				//当此玩家上线恢复角色时，把ROLE_REBACK_LOG表里英雄信息XML字符串转换成Document对象时异常，导致英雄丢失。)
				doc = DocumentHelper.parseText("<name>" + name + "</name>");
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			if(doc == null||name.indexOf(":")!=-1||
					name.indexOf(";")!=-1||
					name.indexOf("*")!=-1||
					name.indexOf(",")!=-1||
					name.indexOf("@")!=-1||
					name.indexOf("\"")!=-1||
					name.indexOf("'")!=-1||
					name.indexOf("#")!=-1||
					name.indexOf("<")!=-1||
					name.indexOf(">")!=-1||
					name.indexOf("&")!=-1||
					name.indexOf(" ")!=-1||
					name.indexOf("　")!=-1||
					name.indexOf("【")!=-1||
					name.indexOf("Δ")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||
					name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||
					name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||
					name.indexOf("１")!=-1||name.indexOf("２")!=-1||name.indexOf("３")!=-1||name.indexOf("４")!=-1||
					name.indexOf("５")!=-1||name.indexOf("６")!=-1||name.indexOf("７")!=-1||name.indexOf("８")!=-1||
					name.indexOf("９")!=-1||name.indexOf("０")!=-1||name.indexOf("Ａ")!=-1||name.indexOf("Ｂ")!=-1||
					name.indexOf("Ｃ")!=-1||name.indexOf("Ｄ")!=-1||name.indexOf("Ｅ")!=-1||name.indexOf("Ｆ")!=-1||
					name.indexOf("Ｇ")!=-1||name.indexOf("Ｈ")!=-1||name.indexOf("Ｉ")!=-1||name.indexOf("Ｊ")!=-1||
					name.indexOf("Ｋ")!=-1||name.indexOf("Ｌ")!=-1||name.indexOf("Ｍ")!=-1||name.indexOf("Ｎ")!=-1||
					name.indexOf("Ｏ")!=-1||name.indexOf("Ｐ")!=-1||name.indexOf("Ｑ")!=-1||name.indexOf("Ｒ")!=-1||
					name.indexOf("Ｓ")!=-1||name.indexOf("Ｔ")!=-1||name.indexOf("Ｕ")!=-1||name.indexOf("Ｖ")!=-1||
					name.indexOf("Ｗ")!=-1||name.indexOf("Ｘ")!=-1||name.indexOf("Ｙ")!=-1||name.indexOf("Ｚ")!=-1||
					name.indexOf("ａ")!=-1||name.indexOf("ｂ")!=-1||name.indexOf("ｃ")!=-1||name.indexOf("ｄ")!=-1||
					name.indexOf("ｅ")!=-1||name.indexOf("ｆ")!=-1||name.indexOf("ｇ")!=-1||name.indexOf("ｈ")!=-1||
					name.indexOf("ｉ")!=-1||name.indexOf("ｊ")!=-1||name.indexOf("ｋ")!=-1||name.indexOf("ｌ")!=-1||
					name.indexOf("ｍ")!=-1||name.indexOf("ｎ")!=-1||name.indexOf("ｏ")!=-1||name.indexOf("ｐ")!=-1||
					name.indexOf("ｑ")!=-1||name.indexOf("ｒ")!=-1||name.indexOf("ｓ")!=-1||name.indexOf("ｔ")!=-1||
					name.indexOf("ｕ")!=-1||name.indexOf("ｖ")!=-1||name.indexOf("ｗ")!=-1||name.indexOf("ｘ")!=-1||
					name.indexOf("ｙ")!=-1||name.indexOf("ｚ")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||
					name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||
					name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||
					name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||
					name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||
					name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||
					name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||name.indexOf("")!=-1||
					WordListMap.isExistWord(name,GameOtherConfig.getInstance().getWorldType()))
			{
				//屏蔽各种符号避免程序其它地方用该符号组合角色名称字符串（例:运营工具发邮件已出现过问题）
				return false;
			}
			else
			{
				return true;
			}
		}
		
		return false;
	}


	/**
	 * 登录后初始化相关角色信息
	 * @param roleInfo
	 */
	public static void roleLoginDo(RoleInfo roleInfo) 
	{
		
	}
	
	/**
	 * 判断玩家离线再等录时间是否超过规定时间
	 * @param lastLogoutTime
	 * @return
	 */
	public static boolean isOutLogoutTime(long lastLogoutTime)
	{
		boolean flag = true;
		//判断玩家下线时间是否超过6分钟,没超过6分钟则不管是否到达最大在线人数都让其登陆游戏

		long currTime = System.currentTimeMillis();	//	当前时间
		if(currTime - lastLogoutTime >= GameValue.USER_LOGOUT_TIME*1000)//超过600秒加入排队,小于600秒不进行最大人数判断
		{
			flag = false;
		}
		
		return flag;
	
	}
	

}