package com.snail.webgame.engine.component.scene.protocal.fight.prop;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class UsePropResp  extends MessageBody{

	private int result;
	private long fightId;
	private String usePhalanxId;
	private String phalanxId;
	private int propNo;
	
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("result", 0);
		ps.add("fightId", 0);
		ps.addString("usePhalanxId", "flashCode", 0);
		ps.addString("phalanxId", "flashCode", 0);
		ps.add("propNo", 0);
	 
	}



 



	public int getResult() {
		return result;
	}







	public void setResult(int result) {
		this.result = result;
	}







	public long getFightId() {
		return fightId;
	}







	public void setFightId(long fightId) {
		this.fightId = fightId;
	}







	public String getUsePhalanxId() {
		return usePhalanxId;
	}







	public void setUsePhalanxId(String usePhalanxId) {
		this.usePhalanxId = usePhalanxId;
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
