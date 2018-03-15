package com.snail.webgame.engine.component.scene.protocal.drop;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf
 * @date 2012-9-5 传送给客户端的掉落物品对象
 */
public class TransDropInfo extends MessageBody {
	protected long dropId;	// 掉落ID
	protected int itemNo; // 物品编号
	protected int itemSort; // 物品类型1-道具，2-装备

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core
	 * .protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("dropId", 0);
		ps.add("itemNo", 0);
		ps.add("itemSort", 0);

	}

	public long getDropId() {
		return dropId;
	}

	public void setDropId(long dropId) {
		this.dropId = dropId;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public int getItemSort() {
		return itemSort;
	}

	public void setItemSort(int itemSort) {
		this.itemSort = itemSort;
	}

}
