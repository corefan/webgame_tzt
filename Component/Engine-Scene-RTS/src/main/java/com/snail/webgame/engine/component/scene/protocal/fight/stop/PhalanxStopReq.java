package com.snail.webgame.engine.component.scene.protocal.fight.stop;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class PhalanxStopReq extends MessageBody{

	private long fightId;
	private String phalanxId;
	private int currX;
	private int currY;
	
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("fightId", 0);
		ps.addString("phalanxId","flashCode", 0);
		ps.add("currX", 0);
		ps.add("currY", 0);
	}

	public long getFightId() {
		return fightId;
	}

	public void setFightId(long fightId) {
		this.fightId = fightId;
	}

	public String getPhalanxId() {
		return phalanxId;
	}

	public void setPhalanxId(String phalanxId) {
		this.phalanxId = phalanxId;
	}

	public int getCurrX() {
		return currX;
	}

	public void setCurrX(int currX) {
		this.currX = currX;
	}

	public int getCurrY() {
		return currY;
	}

	public void setCurrY(int currY) {
		this.currY = currY;
	}
	
	
	
 
	
}
