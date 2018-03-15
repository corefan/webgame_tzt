package com.snail.webgame.engine.component.pack.protocol.quickbar.add;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf
 * @date 2012-8-31 快捷栏变更协议请求类
 */
public class QuickBarAddReq extends MessageBody {
	private long id;	// 如果是新增的快捷栏，则id为-1；如果是往已有的快捷栏中放东西，则为原快捷栏id
	private int quickBarNo; // 快捷栏编号
	private int relType; // 对应关系类型(1:物品背包，2：技能)
	private long relId; // 关联ID

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
		ps.add("quickBarNo", 0);
		ps.add("relType", 0);
		ps.add("relId", 0);

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuickBarNo() {
		return quickBarNo;
	}

	public void setQuickBarNo(int quickBarNo) {
		this.quickBarNo = quickBarNo;
	}

	public int getRelType() {
		return relType;
	}

	public void setRelType(int relType) {
		this.relType = relType;
	}

	public long getRelId() {
		return relId;
	}

	public void setRelId(long relId) {
		this.relId = relId;
	}

}
