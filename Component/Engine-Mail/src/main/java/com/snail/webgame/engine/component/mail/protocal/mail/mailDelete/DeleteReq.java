package com.snail.webgame.engine.component.mail.protocal.mail.mailDelete;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class DeleteReq extends MessageBody {
	
	private String mailId;
	
	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}


	protected void setSequnce(ProtocolSequence ps)
	{
		ps.addString("mailId", "flashCode", 0);
		
	}
}
