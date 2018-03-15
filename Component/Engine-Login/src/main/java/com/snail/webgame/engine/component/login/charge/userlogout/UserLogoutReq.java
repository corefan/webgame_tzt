package com.snail.webgame.engine.component.login.charge.userlogout;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class UserLogoutReq  extends MessageBody {

	private int accountId;
	private String loginId;
	private int GMLevel;
	private String account;
	private String MD5Pass;
	
	protected void setSequnce(ProtocolSequence ps) {
		 
		ps.add("accountId", 1);
		ps.addString("loginId", "chargeCode", 1);
		ps.add("GMLevel", 1);
		ps.addString("account", "chargeCode", 1);
		ps.addString("MD5Pass", "chargeCode", 1);
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMD5Pass() {
		return MD5Pass;
	}

	public void setMD5Pass(String pass) {
		MD5Pass = pass;
	}

}
