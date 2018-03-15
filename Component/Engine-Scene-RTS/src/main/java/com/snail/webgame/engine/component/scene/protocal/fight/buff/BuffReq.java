package com.snail.webgame.engine.component.scene.protocal.fight.buff;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class BuffReq extends MessageBody{

 
	private long fightId;
	private String phalanxId;
	private String buff;
	private byte buffFlag;
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("fightId", 0);
		ps.addString("phalanxId", "flashCode", 0);
		ps.addString("buff", "flashCode", 0);
		ps.add("buffFlag", 0);
		
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
	public String getBuff() {
		return buff;
	}
	public void setBuff(String buff) {
		this.buff = buff;
	}
	public byte getBuffFlag() {
		return buffFlag;
	}
	public void setBuffFlag(byte buffFlag) {
		this.buffFlag = buffFlag;
	}
 
	 

}
