package com.snail.webgame.engine.component.scene.protocal.fight.prop;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class UsePropReq  extends MessageBody{

 
	private long fightId;
	private String userPhalanxId;
	private String phalanxId;
	private int propNo;
	
	
	
	protected void setSequnce(ProtocolSequence ps) {
		 
		ps.add("fightId", 0);
		ps.addString("userPhalanxId", "flashCode", 0);
		ps.addString("phalanxId", "flashCode", 0);
		ps.add("propNo", 0);
		
	}



 



	public long getFightId() {
		return fightId;
	}







	public void setFightId(long fightId) {
		this.fightId = fightId;
	}







 







	public String getUserPhalanxId() {
		return userPhalanxId;
	}







	public void setUserPhalanxId(String userPhalanxId) {
		this.userPhalanxId = userPhalanxId;
	}







	public String getPhalanxId() {
		return phalanxId;
	}



	public void setPhalanxId(String phalanxId) {
		this.phalanxId = phalanxId;
	}



	public int getPropNo() {
		return propNo;
	}



	public void setPropNo(int propNo) {
		this.propNo = propNo;
	}

	
}
