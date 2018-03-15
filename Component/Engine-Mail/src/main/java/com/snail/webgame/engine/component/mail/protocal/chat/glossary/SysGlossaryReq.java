package com.snail.webgame.engine.component.mail.protocal.chat.glossary;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class SysGlossaryReq extends MessageBody {
	
	private String accountId;
	private String sendRoleName;
	private String recRoleName;
	private String word;
	private String chatRecord;

	@Override
	protected void setSequnce(ProtocolSequence ps) {
		
		ps.addString("accountId", "flashCode", 0);
		ps.addString("sendRoleName", "flashCode", 0);
		ps.addString("recRoleName", "flashCode", 0);
		ps.addString("word", "flashCode", 0);
		ps.addString("chatRecord", "flashCode", 0);

	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getRecRoleName() {
		return recRoleName;
	}

	public void setRecRoleName(String recRoleName) {
		this.recRoleName = recRoleName;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getChatRecord() {
		return chatRecord;
	}

	public void setChatRecord(String chatRecord) {
		this.chatRecord = chatRecord;
	}

	public String getSendRoleName() {
		return sendRoleName;
	}

	public void setSendRoleName(String sendRoleName) {
		this.sendRoleName = sendRoleName;
	}

}
