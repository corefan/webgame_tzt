package com.snail.webgame.engine.component.login.charge.refresh;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RefreshAccResp extends MessageBody{

 
	private int accountId;
	private int result;
	private int points;
	private double accLimit;
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("accountId", 1);
		ps.add("result", 1);
		ps.add("points", 1);
		ps.add("accLimit", 1);
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public double getAccLimit() {
		return accLimit;
	}

	public void setAccLimit(double accLimit) {
		this.accLimit = accLimit;
	}

}
