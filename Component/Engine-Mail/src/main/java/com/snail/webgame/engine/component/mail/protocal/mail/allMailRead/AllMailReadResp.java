package com.snail.webgame.engine.component.mail.protocal.mail.allMailRead;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class AllMailReadResp extends MessageBody {

	private int result;
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		// TODO Auto-generated method stub
		ps.add("result", 0);
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}

}
