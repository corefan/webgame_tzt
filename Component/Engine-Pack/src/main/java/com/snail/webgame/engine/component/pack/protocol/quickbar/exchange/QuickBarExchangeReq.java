package com.snail.webgame.engine.component.pack.protocol.quickbar.exchange;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf
 * @date 2012-8-27
 * 
 */
public class QuickBarExchangeReq extends MessageBody{
	private int oldQuickBarNo;	// 原快捷栏编号
	private int newQuickBarNo;	// 新快捷栏编号

	/* (non-Javadoc)
	 * @see org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core.protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("oldQuickBarNo", 0);
		ps.add("newQuickBarNo", 0);
	}

	public int getOldQuickBarNo() {
		return oldQuickBarNo;
	}

	public void setOldQuickBarNo(int oldQuickBarNo) {
		this.oldQuickBarNo = oldQuickBarNo;
	}

	public int getNewQuickBarNo() {
		return newQuickBarNo;
	}

	public void setNewQuickBarNo(int newQuickBarNo) {
		this.newQuickBarNo = newQuickBarNo;
	}

}
