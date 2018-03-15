package com.snail.webgame.engine.component.pack.protocol.pack.del;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf 2012-9-6
 * 
 */
public class PackDelResp extends MessageBody {
	private int result;
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
		ps.add("result", 0);
		ps.add("id", 0);

	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
