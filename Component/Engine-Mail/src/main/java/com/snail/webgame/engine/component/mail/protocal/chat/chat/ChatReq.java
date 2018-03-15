package com.snail.webgame.engine.component.mail.protocal.chat.chat;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ChatReq  extends MessageBody {

 
	private int msgType;//// 1-系统消息 2-当前 3-私聊 4-世界 
	private String recRoleNames;
	private String msgContent;
	private String vendorId = "-1";
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("msgType", 0);
		ps.addString("recRoleNames", "flashCode", 0);
		ps.addString("msgContent", "flashCode", 0);
		ps.addString("vendorId", "flashCode", 0);
		
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getRecRoleNames() {
		return recRoleNames;
	}
	public void setRecRoleNames(String recRoleNames) {
		this.recRoleNames = recRoleNames;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	
	
}