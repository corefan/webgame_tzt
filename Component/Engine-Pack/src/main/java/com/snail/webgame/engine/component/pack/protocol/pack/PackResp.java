package com.snail.webgame.engine.component.pack.protocol.pack;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;


/**
 * @author wangxf
 * @date 2012-8-23
 * 背包信息返回协议
 */
public class PackResp extends MessageBody{
	private int result;
	private int count;	// 数量
	private List<TransPackInfo> packInfoList;

	/* (non-Javadoc)
	 * @see org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core.protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("result", 0);
		ps.add("count", 0);
		ps.addObjectArray("packInfoList", "com.snail.webgame.engine.component.pack.protocol.pack.TransPackInfo", "count");
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<TransPackInfo> getPackInfoList() {
		return packInfoList;
	}

	public void setPackInfoList(List<TransPackInfo> packInfoList) {
		this.packInfoList = packInfoList;
	}

}
