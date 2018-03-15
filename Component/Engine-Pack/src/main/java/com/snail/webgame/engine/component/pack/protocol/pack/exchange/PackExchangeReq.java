package com.snail.webgame.engine.component.pack.protocol.pack.exchange;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf
 * @date 2012-8-27
 * 
 */
public class PackExchangeReq extends MessageBody{
	private int oldGirdNo;
	private int newGirdNo;

	/* (non-Javadoc)
	 * @see org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core.protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("oldGirdNo", 0);
		ps.add("newGirdNo", 0);
	}

	public int getOldGirdNo() {
		return oldGirdNo;
	}

	public void setOldGirdNo(int oldGirdNo) {
		this.oldGirdNo = oldGirdNo;
	}

	public int getNewGirdNo() {
		return newGirdNo;
	}

	public void setNewGirdNo(int newGirdNo) {
		this.newGirdNo = newGirdNo;
	}

}
