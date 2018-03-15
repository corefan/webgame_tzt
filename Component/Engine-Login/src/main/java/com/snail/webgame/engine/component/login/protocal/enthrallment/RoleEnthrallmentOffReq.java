package com.snail.webgame.engine.component.login.protocal.enthrallment;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RoleEnthrallmentOffReq extends MessageBody {

	
	private int promptId;//0是防沉迷，1是非防沉迷
	
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("promptId", 0);
	}

	public int getPromptId() {
		return promptId;
	}

	public void setPromptId(int promptId) {
		this.promptId = promptId;
	}
}
