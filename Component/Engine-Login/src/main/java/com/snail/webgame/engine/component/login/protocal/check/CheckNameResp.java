package com.snail.webgame.engine.component.login.protocal.check;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class CheckNameResp extends MessageBody{

	private int result;
	private String roleName;
	private int checkResult;
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("result", 0);
		ps.addString("roleName", "flashCode", 0);
		ps.add("checkResult", 0);
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(int checkResult) {
		this.checkResult = checkResult;
	}

}
