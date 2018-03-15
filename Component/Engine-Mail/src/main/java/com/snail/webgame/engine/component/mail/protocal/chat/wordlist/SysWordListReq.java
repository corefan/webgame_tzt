package com.snail.webgame.engine.component.mail.protocal.chat.wordlist;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class SysWordListReq extends MessageBody {
	
	private String word;

	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.addString("word", "flashCode", 0);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}



}
