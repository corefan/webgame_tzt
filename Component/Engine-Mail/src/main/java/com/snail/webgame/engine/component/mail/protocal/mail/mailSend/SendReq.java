package com.snail.webgame.engine.component.mail.protocal.mail.mailSend;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class SendReq extends MessageBody
{
	private byte mailType;			//邮件类型
	private String receRoleName;	//接收人角色名称
	private String mailTitle;		//邮件标题
	private String mailContent;		//邮件内容
	private byte smallType;			//小类型
	private String attachment;      //附件（运营后台使用）
	private String reserve;	
	
	
	
	protected void setSequnce(ProtocolSequence ps)
	{
		ps.add("mailType", 0);
		ps.addString("receRoleName", "flashCode", 0);
		ps.addString("mailTitle", "flashCode", 0);
		ps.addString("mailContent", "flashCode", 0);		
		ps.add("smallType", 0);
		ps.addString("attachment", "flashCode", 0);
		ps.addString("reserve", "flashCode", 0);
		
	}



	public byte getMailType() {
		return mailType;
	}



	public void setMailType(byte mailType) {
		this.mailType = mailType;
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



	public String getMailContent() {
		return mailContent;
	}



	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}



	public byte getSmallType() {
		return smallType;
	}



	public void setSmallType(byte smallType) {
		this.smallType = smallType;
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
	
}

