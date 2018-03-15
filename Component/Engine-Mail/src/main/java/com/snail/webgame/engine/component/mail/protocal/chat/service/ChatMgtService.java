package com.snail.webgame.engine.component.mail.protocal.chat.service;

 
 
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.mail.cache.GlossaryRoleCountMap;
import com.snail.webgame.engine.component.mail.cache.GlossaryWordMap;
import com.snail.webgame.engine.component.mail.cache.RoleLoginMap;
import com.snail.webgame.engine.component.mail.cache.RoleNameMap;
import com.snail.webgame.engine.component.mail.common.RoleInfo;
import com.snail.webgame.engine.component.mail.core.ChatService;
import com.snail.webgame.engine.component.mail.protocal.chat.chat.ChatReq;
import com.snail.webgame.engine.component.mail.protocal.chat.chat.ChatResp;
import com.snail.webgame.engine.component.mail.protocal.chat.rolecount.SysRoleCountListReq;
import com.snail.webgame.engine.component.mail.protocal.chat.status.ChatStatusReq;
import com.snail.webgame.engine.component.mail.protocal.chat.wordlist.SysWordListReq;

public class ChatMgtService implements IChatMgtService{

	protected static final Logger logger=LoggerFactory.getLogger("logs");
	/**
	 * 聊天
	 */
	public void sendChatMessage( ChatReq req,int chatRoleId,int ipInt ){}
	
	/**
	 * 发送邮箱空间不足提醒
	 */
	public  void sendMailContain(int result,long roleId,String roleName)
	{
		ChatResp resp = new ChatResp();
		resp.setResult(result);
		resp.setMsgType(0);
		resp.setSendRoleId(roleId);
		resp.setSendRoleName(roleName);
		resp.setMsgContent(roleName);
		RoleInfo recRoleInfo = RoleLoginMap.getRoleInfo(roleId);
		
		if(recRoleInfo!=null)
		{
			ChatService.sendMessage(resp,recRoleInfo);
		}
	}

	/**
	 * 聊天状态更新
	 * @param req
	 */
	public void chatStatusMessage(ChatStatusReq req) 
	{
		
		long roleId = req.getRoleId();
		int status = req.getChatStatus();
		if(RoleLoginMap.isExitRoleInfo(roleId))
		{
			RoleInfo info = RoleLoginMap.getRoleInfo(roleId);
			info.setChatStatus(status);
			info.setChatTime(req.getTime());
		}
		
	}
	/**
	 * 与游戏服务器同步被监控角色信息
	 * @param req
	 */
	public void setGlossaryRoleCount(SysRoleCountListReq req)
	{
		GlossaryRoleCountMap.removeAll();
		String roleCountStr = req.getRoleCounts();
		if(roleCountStr != null && roleCountStr.length() > 0)
		{
			String[] roleCounts = roleCountStr.split(",");
			if(roleCounts != null && roleCounts.length > 0)
			{
				for(int i=0; i<roleCounts.length; i++)
				{
					String roleCount = roleCounts[i];
					if(roleCount != null && roleCount.length() > 0)
					{
						GlossaryRoleCountMap.addRoleCount(roleCount);
					}
				}
			}
		}
	}
	/**
	 * 与游戏服务器同步被监控词
	 * @param req
	 */
	public void setGlossaryWord(SysWordListReq req)
	{
		GlossaryWordMap.removeAll();
		
		if(req.getWord() != null && req.getWord().length() > 0)
		{
			String[] words = req.getWord().split(",");
			for(int i=0; i<words.length; i++)
			{
				String word = words[i];
				if(word != null && word.length() > 0)
				{
					GlossaryWordMap.addWord(word);
				}
			}
			
		}
	}
}
