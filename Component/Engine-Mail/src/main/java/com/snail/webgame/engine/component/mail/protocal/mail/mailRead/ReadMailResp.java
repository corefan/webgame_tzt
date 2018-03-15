package com.snail.webgame.engine.component.mail.protocal.mail.mailRead;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ReadMailResp extends MessageBody
{
	private int result;
	private long mailId;			//邮件Id
	private byte mailType;//邮件类型
	private long sendRoleId;			//发送人角色Id
	private String sendRoleName;	//发送人角色名称
	private long receRoleId;			//接收人角色Id
	private String receRoleName;	//接收人角色名称
	private String mailTitle;		//邮件标题
	private String mailContent;		//邮件内容
	private long sendTime;		//邮件发送时间(yyyy-MM-dd HH:mm:ss
	private byte smallType;		    //邮件小类型
	private byte flag;
	private String attachment;      //附件 物品ID:物品数量
	private String reserve;
	
	protected void setSequnce(ProtocolSequence ps)
	{
		ps.add("result", 0);
		ps.add("mailId", 0);
		ps.add("mailType", 0);
		ps.add("sendRoleId", 0);
		ps.addString("sendRoleName", "flashCode", 0);
		ps.add("receRoleId", 0);
		ps.addString("receRoleName", "flashCode", 0);
		ps.addString("mailTitle", "flashCode", 0);
		ps.addString("mailContent", "flashCode", 0);
		ps.add("sendTime", 0);
		ps.add("smallType", 0);
		ps.add("flag", 0);
		ps.addString("attachment", "flashCode", 0);
		ps.addString("reserve", "flashCode", 0);
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
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

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public byte getSmallType() {
		return smallType;
	}

	public void setSmallType(byte smallType) {
		this.smallType = smallType;
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

}
