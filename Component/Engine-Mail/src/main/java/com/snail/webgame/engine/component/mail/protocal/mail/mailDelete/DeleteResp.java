package com.snail.webgame.engine.component.mail.protocal.mail.mailDelete;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class DeleteResp extends MessageBody {
	private int result;
	private String mailId;
 
	
	
	protected void setSequnce(ProtocolSequence ps)
	{
		ps.add("result", 0);
		ps.addString("mailId", "flashCode", 0);
	 
	}



	public int getResult() {
		return result;
	}



	public void setResult(int result) {
		this.result = result;
	}



	public String getMailId() {
		return mailId;
	}



	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	
	
}

