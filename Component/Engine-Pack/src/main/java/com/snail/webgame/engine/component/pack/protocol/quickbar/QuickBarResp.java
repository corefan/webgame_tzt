package com.snail.webgame.engine.component.pack.protocol.quickbar;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf
 * @date 2012-8-31
 * 快捷栏操作请求协议返回类
 */
public class QuickBarResp extends MessageBody{
	private int result;
	private int quickBarCount;
	private List<TransQuickBarInfo> quickBarList;

	/* (non-Javadoc)
	 * @see org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core.protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("result", 0);
		ps.add("quickBarCount", 0);
		ps.addObjectArray("quickBarList", "com.snail.webgame.engine.component.pack.protocol.quickbar.TransQuickBarInfo", "quickBarCount");
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getQuickBarCount() {
		return quickBarCount;
	}

	public void setQuickBarCount(int quickBarCount) {
		this.quickBarCount = quickBarCount;
	}

	public List<TransQuickBarInfo> getQuickBarList() {
		return quickBarList;
	}

	public void setQuickBarList(List<TransQuickBarInfo> quickBarList) {
		this.quickBarList = quickBarList;
	}

}
