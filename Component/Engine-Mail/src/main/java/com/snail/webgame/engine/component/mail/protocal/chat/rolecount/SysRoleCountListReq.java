package com.snail.webgame.engine.component.mail.protocal.chat.rolecount;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class SysRoleCountListReq extends MessageBody {
	
	private String roleCounts;

	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.addString("roleCounts", "flashCode", 0);
	}

	public String getRoleCounts() {
		return roleCounts;
	}

	public void setRoleCounts(String roleCounts) {
		this.roleCounts = roleCounts;
	}

}
