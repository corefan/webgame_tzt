package com.snail.webgame.engine.component.scene.protocal.fight.tire;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RefreshRe extends MessageBody{


	private String phalanxId;
	private int morale;
	
	protected void setSequnce(ProtocolSequence ps) {
		 

		ps.addString("phalanxId", "flashCode", 0);
		ps.add("morale", 0);
	}

	

	public String getPhalanxId() {
		return phalanxId;
	}

	public void setPhalanxId(String phalanxId) {
		this.phalanxId = phalanxId;
	}

	public int getMorale() {
		return morale;
	}

	public void setMorale(int morale) {
		this.morale = morale;
	}

 
}
