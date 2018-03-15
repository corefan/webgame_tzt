package com.snail.webgame.engine.component.scene.protocal.fight.in;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RoleInFightReq extends MessageBody{

	private long fightId;
	private int isGM; 
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		 ps.add("fightId", 0);
		 ps.add("isGM", 0);
	}

	public long getFightId() {
		return fightId;
	}

	public void setFightId(long fightId) {
		this.fightId = fightId;
	}

	public int getIsGM() {
		return isGM;
	}

	public void setIsGM(int isGM) {
		this.isGM = isGM;
	}
 

	 

}
