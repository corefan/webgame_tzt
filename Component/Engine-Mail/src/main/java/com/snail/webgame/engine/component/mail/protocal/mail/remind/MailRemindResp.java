package com.snail.webgame.engine.component.mail.protocal.mail.remind;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class MailRemindResp extends MessageBody {
	private int result;
	private int type;//1-已满 2-新邮件

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	 

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	protected void setSequnce(ProtocolSequence ps)
	{
		ps.add("result", 0);	
		ps.add("type", 0);
	}

}
