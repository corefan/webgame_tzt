package com.snail.webgame.engine.common;

public class ErrorCode {

	/**
	 * 系统异常，请重启浏览器，并清空浏览器缓存，如问题还是存在，请联系客服人员。
	 */
	public static int SYSTEM_ERROR = 10000;

	/**
	 * 服务器尚未开放,请稍候登录
	 */
	public static int GAME_LOGIN_ERROR_1 = 10001;
	/**
	 * 登录失败,游戏服务器过于繁忙,请稍候登录
	 */
	public static int GAME_LOGIN_ERROR_2 = 10002;
	/**
	 * 登录失败,游戏服务器已达到最大在线人数
	 */
	public static int GAME_LOGIN_ERROR_3 = 10003;
	/**
	 * 登录失败，登录过于频繁，请稍候登录
	 */
	public static int LOGIN_FREQ_ERROR_1 = 10004;
	/**
	 * 登录失败，服务器处于维护中
	 */
	public static int GATE_SERVER_ERROR_1 = 10005;
	/**
	 * 其他用户登录该帐号，您已被踢出游戏。
	 */
	public static int USER_LOGIN_ERROR_2 = 10006;

	/**
	 * 系统正在维护，请到游戏官网查看维护公告，稍后登录。
	 */
	public static int USER_LOGIN_CHARGE_ERROR_1 = 10007;
	/**
	 * 用户验证失效，请重新登录。
	 */
	public static int USER_LOGIN_ERROR_3 = 10008;

	/**
	 * 当前位置不能切换场景
	 */
	public static int CHANGE_SCENE_ERROR_1 = 10010;
	/**
	 * 当前状态不能复活
	 */
	public static int ROLE_REBIRTH_ERROR_1 = 10011;

	// ===================背包操作错误编码(10101-10200)
	/**
	 * 调整包裹位置失败，不存在相关背包信息
	 */
	public static int OPERATE_ROLE_PACK_ERROR_1 = 10101;
	/**
	 * 拆分背包失败，不存在相关背包信息
	 */
	public static int OPERATE_ROLE_PACK_ERROR_2 = 10102;
	/**
	 * 拆分背包失败，背包中没有物品
	 */
	public static int OPERATE_ROLE_PACK_ERROR_3 = 10103;
	/**
	 * 拆分背包失败，拆分数量大于当前物品数量
	 */
	public static int OPERATE_ROLE_PACK_ERROR_4 = 10104;
	/**
	 * 拆分背包失败，背包已满
	 */
	public static int OPERATE_ROLE_PACK_ERROR_5 = 10105;
	/**
	 * 删除背包失败，不存在该背包
	 */
	public static int OPERATE_ROLE_PACK_ERROR_6 = 10106;

	// ===================背包操作错误编码end

	// ===================快捷栏操作错误编码(10101-10119)

	/**
	 * 变更角色快捷栏信息失败,角色没有对应的快捷栏
	 */
	public static int MODIFY_ROLE_QUICKBAR_ERROR_3 = 10112;

	// ===================快捷栏操作错误编码end

	// -------------------------聊天模块10200~10249--------------------------------

	/**
	 * 对方不在线或者不存在。
	 */
	public static int CHAT_ROLE_NOEXIST = 10200;
	/**
	 * 收件人不存在。
	 */
	public static int MAIL_RECEIVER_NOEXIST = 10201;
	/**
	 * 获得邮件列表失败，出现未知错误，请重启浏览器，并清空浏览器缓存，如问题还是存在，请联系客服人员。
	 */
	public static int MAIL_LIST_ERROR = 10202;

	/**
	 * 对方邮箱空间不足，无法接收新邮件。
	 */
	public static int MAILSPACE_NOT_ENOUGH = 10203;
	/**
	 * 保存邮件失败，请重启浏览器，并清空浏览器缓存，如问题还是存在，请联系客服人员。
	 */
	public static int SAVE_MAIL_FAILED = 10204;
	/**
	 * 读取邮件失败，请重启浏览器，并清空浏览器缓存，如问题还是存在，请联系客服人员。
	 */
	public static int READ_MAIL_FAILED = 10205;
	/**
	 * 该邮件不存在。
	 */
	public static int MAIL_NOT_EXIST = 10206;
	/**
	 * 删除邮件失败，请重启浏览器，并清空浏览器缓存，如问题还是存在，请联系客服人员。
	 */
	public static int DELETE_MAIL_ERROR = 10207;

	/**
	 * 设置邮件全部已读失败，请重启浏览器，并清空浏览器缓存，如问题还是存在，请联系客服人员。
	 */
	public static int SET_ALLMAILREAD_ERROR = 10208;
	/**
	 * 邮件附件领取失败，该邮件不属于您。
	 */
	public static int MAIL_GET_ATTACHMENT_ERROR_1 = 10209;
	/**
	 * 邮件附件领取失败，该邮件没有附件。
	 */
	public static int MAIL_GET_ATTACHMENT_ERROR_2 = 10210;
	/**
	 * 邮件附件领取失败，该邮件附件已领取。
	 */
	public static int MAIL_GET_ATTACHMENT_ERROR_3 = 10211;
	/**
	 * 邮件附件领取失败，包裹空间不足。
	 */
	public static int MAIL_GET_ATTACHMENT_ERROR_4 = 10212;
	/**
	 * 邮件发送失败，黑名单中无法发送。
	 */
	public static int MAIL_BLACKLIST_ERROR1 = 10213;

	/**
	 * 您的邮箱快要满了，请注意清理，否则可能无法接收到新邮件
	 */
	public static int MAIL_NUM_ERROR_0 = 10215;
	/**
	 * 您的邮箱已经满了，请注意清理，否则可能无法接收到新邮件
	 */
	public static int MAIL_NUM_ERROR_1 = 10216;

	/**
	 * 发言失败，您正处于禁言中。
	 */
	public static int NIP_CHAT_ERROR_1 = 10217;

	/**
	 * 发言失败，出现未知错误。
	 */
	public static int NIP_CHAT_ERROR_2 = 10218;

	/**
	 * 新手辅导员权限设置失败，请您稍后再试。
	 */
	public static int NIP_CHAT_ERROR_3 = 10219;

	/**
	 * 新手辅导员权限设置失败,超出设置范围。
	 */
	public static int NIP_CHAT_ERROR_4 = 10220;

	/**
	 * 新手辅导员权限设置失败,已和该角色权限一致。
	 */
	public static int NIP_CHAT_ERROR_5 = 10221;

	/**
	 * 您不是新手辅导员，发言失败。
	 */
	public static int NIP_CHAT_ERROR_6 = 10222;
	/**
	 * 聊天内容不能为空。
	 */
	public static int NIP_CHAT_ERROR_7 = 10223;
	/**
	 * 聊天消息类型设置失败,超出设置范围。
	 */
	public static int NIP_CHAT_ERROR_8 = 10224;


	// ---聊天end

}
