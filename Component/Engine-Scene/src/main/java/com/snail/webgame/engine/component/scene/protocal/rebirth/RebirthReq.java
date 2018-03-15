package com.snail.webgame.engine.component.scene.protocal.rebirth;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf
 * @date 2012-11-23
 * 复活请求协议类
 */
public class RebirthReq extends MessageBody {
	private int rebirthType;

	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("rebirthType", 0);
	}

	public int getRebirthType() {
		return rebirthType;
	}

	public void setRebirthType(int rebirthType) {
		this.rebirthType = rebirthType;
	}
	
}
