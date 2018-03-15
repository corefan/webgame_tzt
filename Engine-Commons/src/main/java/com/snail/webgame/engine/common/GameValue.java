package com.snail.webgame.engine.common;

public class GameValue {

	/**
	 * 检查排队标识
	 */
	public static boolean CHECK_LOGIN_QUEUE = false;
	
	/**
	 * 游戏验证标识
	 */
	public static int GAME_VALIDATEIN_FLAG = 0;
	
	/**
	 * 登录间隔中已经登录的数量
	 */
	public static int LOGIN_FREQ_NUM = 0;
	
	/**
	 * 名字最大数量
	 */
	public static int MAX_NAME_LENGTH = 8;
	
	/**
	 * 玩家离线再登入无需排队允许时间(单位:s)
	 */
	public static int USER_LOGOUT_TIME = 600;
	
	/**
	 * 是否允许登录
	 */
	public static int IS_ALLOW_LOGIN = 1;
	
	public static boolean GAME_INDULGE_ON = false;
	
	public static int GAME_INDULGE_TIME = 0;
	
	/**
	 * 当前坐标纠正需要的距离
	 */
	public static int CURR_POINT_CORRECT_SPACE = 3;
	/**
	 * 玩家最大邮件数
	 */
	public static int ROLE_MAIL_MAX_NUM = 100;
	/**
	 * 玩家邮件空间提醒
	 */
	public static int ROLE_MAIL_NUM_AWAKE = 80;
	
	/**
	 * 可交易的最大距离
	 */
	public static int MAX_TRADE_SPACE = 20;
	
}
