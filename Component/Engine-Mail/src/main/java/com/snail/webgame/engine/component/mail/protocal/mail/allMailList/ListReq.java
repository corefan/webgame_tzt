package com.snail.webgame.engine.component.mail.protocal.mail.allMailList;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ListReq extends MessageBody{


	/**
	 * 邮件类型
	 */
	private byte type;
	/**
	 * 开始索引
	 */
	private int index;
	/**
	 * 邮件数量
	 */
	private int count;

	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("type", 0);
		ps.add("index", 0);
		ps.add("count", 0);
		
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


 
}