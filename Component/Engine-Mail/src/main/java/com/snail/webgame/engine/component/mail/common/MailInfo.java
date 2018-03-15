package com.snail.webgame.engine.component.mail.common;

import java.sql.Timestamp;

import com.snail.webgame.engine.common.to.BaseTO;

public class MailInfo extends BaseTO{
	
	protected byte mailType;			//邮件类型
	protected long sendRoleId;			//发送人角色Id
	protected String sendRoleName;	//发送人角色名称
	protected long receRoleId;			//接收人角色Id
	protected String receRoleName;	//接收人角色名称
	protected String mailTitle;		//邮件标题
	protected String content;
	protected Timestamp sendTime;		    //邮件发送时间(yyyy-MM-dd HH:mm:ss)
	protected byte readState;			//读取标志：0-未读取1-已读取
	protected byte subType;           //邮件子类型
	protected byte flag;				//处理标识
	
 	protected String attachment;      //附件
 	protected String reserve;			//保留字段


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


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Timestamp getSendTime() {
		return sendTime;
	}


	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}


	public byte getReadState() {
		return readState;
	}


	public void setReadState(byte readState) {
		this.readState = readState;
	}


	public byte getSubType() {
		return subType;
	}


	public void setSubType(byte subType) {
		this.subType = subType;
	}


	public byte getFlag() {
		return flag;
	}


	public void setFlag(byte flag) {
		this.flag = flag;
	}


	public String getAttachment() {
		return attachment;
	}


	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}


	public String getReserve() {
		return reserve;
	}


	public void setReserve(String reserve) {
		this.reserve = reserve;
	}


	@Override
	public byte getSaveMode() {
		return ONLINE;
	}
	
  
	
}

