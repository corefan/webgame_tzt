package com.snail.webgame.engine.component.pack.protocol.pack;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf
 * @date 2012-8-23 与客户端交互的包裹对象
 */
public class TransPackInfo extends MessageBody {
	private long id;	// 包裹id
	private int girdNo; // 格子编号
	private int relType;	// 关联类型
	private long relId; // 关联ID

	/* (non-Javadoc)
	 * @see org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core.protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("id", 0);
		ps.add("girdNo", 0);
		ps.add("relType", 0);
		ps.add("relId", 0);
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGirdNo() {
		return girdNo;
	}

	public void setGirdNo(int girdNo) {
		this.girdNo = girdNo;
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
