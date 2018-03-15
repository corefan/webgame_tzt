package com.snail.webgame.engine.component.mail.protocal.chat.service;

import com.snail.webgame.engine.component.mail.protocal.chat.chat.ChatReq;
import com.snail.webgame.engine.component.mail.protocal.chat.rolecount.SysRoleCountListReq;
import com.snail.webgame.engine.component.mail.protocal.chat.status.ChatStatusReq;
import com.snail.webgame.engine.component.mail.protocal.chat.wordlist.SysWordListReq;

public interface IChatMgtService {
	/**
	 * 聊天
	 */
	public void sendChatMessage( ChatReq req,int chatRoleId,int ipInt );
	

	/**
	 * 发送邮箱空间不足提醒
	 */
	public  void sendMailContain(int result,long roleId,String roleName);

	/**
	 * 聊天状态更新
	 * @param req
	 */
	public void chatStatusMessage(ChatStatusReq req);
	/**
	 * 与游戏服务器同步被监控角色信息
	 * @param req
	 */
	public void setGlossaryRoleCount(SysRoleCountListReq req);
	/**
	 * 与游戏服务器同步被监控词
	 * @param req
	 */
	public void setGlossaryWord(SysWordListReq req);
}
