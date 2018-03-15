package com.snail.webgame.engine.component.pack.protocol.pack.del;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf 2012-9-6
 * 
 */
public class PackDelReq extends MessageBody {
	private long id;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core
	 * .protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("id", 0);

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
