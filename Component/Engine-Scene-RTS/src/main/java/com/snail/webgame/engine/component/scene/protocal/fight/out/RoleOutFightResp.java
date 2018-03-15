package com.snail.webgame.engine.component.scene.protocal.fight.out;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RoleOutFightResp extends MessageBody{

	private int result;
	private long fightId;
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("result", 0);
		ps.add("fightId", 0);
	}
	public long getFightId() {
		return fightId;
	}
	public void setFightId(long fightId) {
		this.fightId = fightId;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	 

}
