package com.snail.webgame.engine.component.login.charge.register;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class GameRegisterResp extends MessageBody{

 
	private int result ;
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("result", 1);
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}

}
