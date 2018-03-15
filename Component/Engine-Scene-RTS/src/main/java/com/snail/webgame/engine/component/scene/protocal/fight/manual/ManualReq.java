package com.snail.webgame.engine.component.scene.protocal.fight.manual;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ManualReq extends MessageBody{
	
	private long fightId;
	private int type;
 
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("fightId", 0);
		ps.add("type", 0);
	}

	public long getFightId() {
		return fightId;
	}

	public void setFightId(long fightId) {
		this.fightId = fightId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
}
