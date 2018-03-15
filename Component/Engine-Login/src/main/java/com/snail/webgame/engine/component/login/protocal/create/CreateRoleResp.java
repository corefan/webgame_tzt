package com.snail.webgame.engine.component.login.protocal.create;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class CreateRoleResp extends MessageBody{
	
	private int result;
	
	protected void setSequnce(ProtocolSequence ps) {
		
		ps.add("result", 0);
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
