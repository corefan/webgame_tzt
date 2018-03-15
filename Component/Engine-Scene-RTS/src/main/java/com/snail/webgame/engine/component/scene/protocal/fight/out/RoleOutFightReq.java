package com.snail.webgame.engine.component.scene.protocal.fight.out;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RoleOutFightReq extends MessageBody{

	private long fightId;
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("fightId", 0);
	}
	public long getFightId() {
		return fightId;
	}
	public void setFightId(long fightId) {
		this.fightId = fightId;
	}
	 

}
