package com.snail.webgame.engine.gate.config;

public class Command {

 
	
	public static int GAME_SERVER_ACTIVE_REQ= 0xfffE;//游戏服务器端定时激活连接
	
	public static int REPORT_SERVER_NUM = 0xfffD;//报告当前通讯服务器在线人数
	
	public static int USER_VERIFY_ROLE_REQ = 0xA001;	   
    public static int USER_VERIFY_ROLE_RESP = 0xA002; 
    
	//游戏客户端验证角色名是否重复
	public static int USER_CHECK_ROLE_REQ = 0xA003;	   
    public static int USER_CHECK_ROLE_RESP = 0xA004;   
    
    public static int USER_CREATE_ROLE_REQ = 0xA005;   //游戏客户端创建角色
    public static int USER_CREATE_ROLE_RESP = 0xA006;   
    
	public static int USER_LOGIN_REQ = 0xA007;	   //游戏客户端用户登录请求
    public static int USER_LOGIN_RESP = 0xA006;	   //游戏客户端用户登录应答
    public static int USER_ROLE_RESP = 0xA008;	   //通知客户端进入创建角色的帐号
    public static int USER_LOGOUT_RESP= 0xA010;//游客客户端注销请求
    
    public static int GAME_CLIENT_ACTIVE_REQ= 0xffff;//游戏客户端定时激活连接
    
    
    public static int USER_LOGIN_QUEUE_REQ = 0xA011;//登录队列查询请求
    public static int USER_LOGIN_QUEUE_RESP = 0xA012;//返回队列查询信息
    
    public static int CHECK_LOGIN_QUEUE_RESP = 0xA014; //通知游戏服务器检查登录排队状况
    

}
