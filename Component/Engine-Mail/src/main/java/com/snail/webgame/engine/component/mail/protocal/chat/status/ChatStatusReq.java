package com.snail.webgame.engine.component.mail.protocal.chat.status;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ChatStatusReq extends MessageBody{

	private long roleId;
	private int chatStatus;
	private long time ; 
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("roleId", 0);
		ps.add("chatStatus", 0);
		ps.add("time", 0);
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	 
 
	public int getChatStatus() {
		return chatStatus;
	}
	public void setChatStatus(int chatStatus) {
		this.chatStatus = chatStatus;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

}
