package com.snail.webgame.engine.component.mail.protocal.rolemgt.service;

import java.util.Map;

import com.snail.webgame.engine.component.mail.cache.MailListMap;
import com.snail.webgame.engine.component.mail.cache.RoleLoginMap;
import com.snail.webgame.engine.component.mail.cache.RoleNameMap;
import com.snail.webgame.engine.component.mail.common.MailInfo;
import com.snail.webgame.engine.component.mail.common.RoleInfo;
import com.snail.webgame.engine.component.mail.protocal.mail.service.MailMgtService;
import com.snail.webgame.engine.component.mail.protocal.rolemgt.login.UserLoginResp;

public class RoleMgtService implements IRoleMgtService{

	
	/**
	 * 缓存用户登录信息
	 * @param resp
	 */
	public void roleLogin(UserLoginResp resp ,int gateServerId)
	{
		if(resp.getResult()==1)
		{
			int roleId = resp.getRoleId();

			RoleInfo info = new RoleInfo();
			info.setRoleId(roleId);
			info.setRoleName(resp.getRoleName());
			info.setGateServerId(gateServerId);
			info.setChatStatus(0);
			info.setLastWorldChatTime(0);			
			info.setLastCommChatTime(0);
			info.setLastNipChatTime(0);
			
			info.setVendorId(resp.getVendorId());
			
			RoleLoginMap.addRoleInfo(roleId, info);
			RoleNameMap.addRoleInfo(info.getRoleName(),info.getRoleId());
			
			Map<Long, MailInfo> mailMap = MailListMap.getMailInfo(roleId);
			if(mailMap != null && !mailMap.isEmpty()){
				for(long key : mailMap.keySet()){
					MailInfo mail = mailMap.get(key);
					if(mail != null && mail.getReadState() == 0){
						MailMgtService.sendNewMail(roleId, 1);
						break;
					}
				}
			}
			
		}
	}
	

	
	/**
	 * 用户退出
	 * @param roleId
	 */
	public void userLogout(int roleId)
	{
		if(RoleLoginMap.isExitRoleInfo(roleId))
		{
			RoleInfo info = RoleLoginMap.getRoleInfo(roleId);
			RoleLoginMap.removeRoleInfo(roleId);
			RoleNameMap.removeRoleInfo(info.getRoleName());
		}
	}

	
}
