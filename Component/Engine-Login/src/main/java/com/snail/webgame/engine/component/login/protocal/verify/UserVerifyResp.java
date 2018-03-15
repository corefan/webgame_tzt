package com.snail.webgame.engine.component.login.protocal.verify;


import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class UserVerifyResp extends MessageBody {


	private int result;
	private String account;
	private int serverId;
	


	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("result", 0);
		ps.addString("account", "flashCode", 0);
		ps.add("serverId", 0);
	}

}
