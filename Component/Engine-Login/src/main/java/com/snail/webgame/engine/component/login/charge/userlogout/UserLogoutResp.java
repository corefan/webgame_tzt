package com.snail.webgame.engine.component.login.charge.userlogout;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class UserLogoutResp  extends MessageBody {
 
	
	private int accountId;
	private int result;
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("accountId", 1);
		ps.add("result", 1);
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

 
}
