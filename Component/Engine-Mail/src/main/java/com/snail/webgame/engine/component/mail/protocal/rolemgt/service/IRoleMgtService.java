package com.snail.webgame.engine.component.mail.protocal.rolemgt.service;

import com.snail.webgame.engine.component.mail.protocal.rolemgt.login.UserLoginResp;

public interface IRoleMgtService {
	/**
	 * 缓存用户登录信息
	 * @param resp
	 */
	public void roleLogin(UserLoginResp resp ,int gateServerId);
	/**
	 * 用户退出
	 * @param roleId
	 */
	public void userLogout(int roleId);
}
