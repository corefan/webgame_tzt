package com.snail.webgame.engine.component.login.charge.refresh;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RefreshAccReq extends MessageBody{

 
	private int accountId;
	private String loginId;
	private int GMLevel;
	private String chaInfo;
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("accountId", 1);
		ps.addString("loginId", "chargeCode", 1);
		ps.add("GMLevel", 1);
		ps.addString("chaInfo", "chargeCode", 1);
		
		
		
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public int getGMLevel() {
		return GMLevel;
	}
	public void setGMLevel(int level) {
		GMLevel = level;
	}
	public String getChaInfo() {
		return chaInfo;
	}
	public void setChaInfo(String chaInfo) {
		this.chaInfo = chaInfo;
	}

}
