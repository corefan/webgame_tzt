package com.snail.webgame.engine.component.scene.protocal.fight.in;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class HeroItemInfoRe extends MessageBody{

 
	private int itemNo;
	private int itemNum;
	private long itemUseTime;
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("itemNo", 0);
		ps.add("itemNum", 0);
		ps.add("itemUseTime", 0);
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public long getItemUseTime() {
		return itemUseTime;
	}

	public void setItemUseTime(long itemUseTime) {
		this.itemUseTime = itemUseTime;
	}

}
