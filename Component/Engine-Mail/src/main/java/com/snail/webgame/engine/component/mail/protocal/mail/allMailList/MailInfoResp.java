package com.snail.webgame.engine.component.mail.protocal.mail.allMailList;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class MailInfoResp extends MessageBody{
	
	private long id;			//邮件Id
	private byte mailType;			//邮件类型:1-系统2-玩家
	private long sendRoleId;			//发送人角色Id
	private String sendRoleName;	//发送人角色名称
	private long receRoleId;			//接收人角色Id
	private String receRoleName;	//接收人角色名称
	private String mailTitle;		//邮件标题
	private long sendTime;		    //邮件发送时间(yyyy-MM-dd HH:mm:ss)
	private byte readState;			//读取标志：0-未读取1-已读取
	
	protected void setSequnce(ProtocolSequence ps)
	{
		ps.add("id", 0);
		ps.add("mailType", 0);
		ps.add("sendRoleId", 0);
		ps.addString("sendRoleName", "flashCode", 0);
		ps.add("receRoleId", 0);
		ps.addString("receRoleName", "flashCode", 0);
		ps.addString("mailTitle", "flashCode", 0);
		ps.add("sendTime", 0);
		ps.add("readState", 0);
 	}

	

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public byte getMailType() {
		return mailType;
	}

	public void setMailType(byte mailType) {
		this.mailType = mailType;
	}

	public long getSendRoleId() {
		return sendRoleId;
	}

	public void setSendRoleId(long sendRoleId) {
		this.sendRoleId = sendRoleId;
	}

	public String getSendRoleName() {
		return sendRoleName;
	}

	public void setSendRoleName(String sendRoleName) {
		this.sendRoleName = sendRoleName;
	}

	public long getReceRoleId() {
		return receRoleId;
	}

	public void setReceRoleId(long receRoleId) {
		this.receRoleId = receRoleId;
	}

	public String getReceRoleName() {
		return receRoleName;
	}

	public void setReceRoleName(String receRoleName) {
		this.receRoleName = receRoleName;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public byte getReadState() {
		return readState;
	}

	public void setReadState(byte readState) {
		this.readState = readState;
	}

	
}

