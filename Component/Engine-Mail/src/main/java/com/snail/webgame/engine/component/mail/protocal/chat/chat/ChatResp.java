package com.snail.webgame.engine.component.mail.protocal.chat.chat;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ChatResp  extends MessageBody {

 
	private int result;
	private int msgType;
	private long sendRoleId;
	private String sendRoleName;
	private String recRoleName;
	private String msgContent;
	private long sendTime;
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("result", 0);
		ps.add("msgType", 0);
		ps.add("sendRoleId", 0);
		ps.addString("sendRoleName", "flashCode", 0);
		ps.addString("recRoleName", "flashCode", 0);
		ps.addString("msgContent", "flashCode", 0);
		ps.add("sendTime", 0);
		
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getMsgType() {
		return msgType;
	}

	public long getSendRoleId() {
		return sendRoleId;
	}

	public void setSendRoleId(long sendRoleId) {
		this.sendRoleId = sendRoleId;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}


	public String getSendRoleName() {
		return sendRoleName;
	}

	public void setSendRoleName(String sendRoleName) {
		this.sendRoleName = sendRoleName;
	}


	public String getRecRoleName() {
		return recRoleName;
	}

	public void setRecRoleName(String recRoleName) {
		this.recRoleName = recRoleName;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

}
