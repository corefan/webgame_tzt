package com.snail.webgame.engine.component.scene.protocal.fight.end;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class EndFightReq extends MessageBody{

 
	private long fightId;
	private int victorySide ;
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("fightId", 0);
		ps.add("victorySide", 0);
	}

	public long getFightId() {
		return fightId;
	}

	public void setFightId(long fightId) {
		this.fightId = fightId;
	}

	public int getVictorySide() {
		return victorySide;
	}

	public void setVictorySide(int victorySide) {
		this.victorySide = victorySide;
	}

}
