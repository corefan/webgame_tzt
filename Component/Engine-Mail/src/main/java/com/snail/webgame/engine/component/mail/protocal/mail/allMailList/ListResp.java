package com.snail.webgame.engine.component.mail.protocal.mail.allMailList;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;



public class ListResp extends MessageBody {
	
	private int result;
	private byte type;
	private int mailSum;
	private int index;
	private int count;
	private List<MailInfoResp> mailInfoList;
	
	protected void setSequnce(ProtocolSequence ps)
	{
		ps.add("result", 0);
		ps.add("type", 0);
		ps.add("mailSum", 0);
		ps.add("index", 0);
		ps.add("count", 0);
		ps.addObjectArray("mailInfoList", "com.snail.webgame.game.protocal.mail.allMailList.MailInfoResp", "count");
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getMailSum() {
		return mailSum;
	}

	public void setMailSum(int mailSum) {
		this.mailSum = mailSum;
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

	public List<MailInfoResp> getMailInfoList() {
		return mailInfoList;
	}

	public void setMailInfoList(List<MailInfoResp> mailInfoList) {
		this.mailInfoList = mailInfoList;
	}
	

}

