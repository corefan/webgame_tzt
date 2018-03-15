package com.snail.webgame.engine.component.mail.protocal.rolemgt.logout;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class UserLogoutResp extends MessageBody {

 
	private int result;
	private int roleId;
	
	protected void setSequnce(ProtocolSequence ps) {
		 
		ps.add("result", 0);
		ps.add("roleId", 0);
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
