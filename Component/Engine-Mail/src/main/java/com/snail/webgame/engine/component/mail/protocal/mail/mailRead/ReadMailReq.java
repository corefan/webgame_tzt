package com.snail.webgame.engine.component.mail.protocal.mail.mailRead;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ReadMailReq extends MessageBody {
	
	
	private long mailId;

	@Override
	protected void setSequnce(ProtocolSequence ps)
	{
		ps.add("mailId",  0);
		
	}

	public long getMailId() {
		return mailId;
	}

	public void setMailId(long mailId) {
		this.mailId = mailId;
	}


}
