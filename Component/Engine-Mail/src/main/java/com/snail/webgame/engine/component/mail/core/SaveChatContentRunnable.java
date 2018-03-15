package com.snail.webgame.engine.component.mail.core;

import com.snail.webgame.engine.component.mail.dao.ChatContentDAO;


public class SaveChatContentRunnable implements Runnable {

	String roleName;
	int channelType;
	String targetRoleName;
	String ip;
	String content;
	
	public SaveChatContentRunnable(String roleName,int channelType,
			String targetRoleName,String ip,String content)
	{
		this.roleName=roleName;
		this.channelType=channelType;
		this.targetRoleName=targetRoleName;
		this.ip=ip;
		this.content=content;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		//ChatContentDAO.saveChatContentDAO(roleName,channelType,targetRoleName,ip,content);
	}

}
