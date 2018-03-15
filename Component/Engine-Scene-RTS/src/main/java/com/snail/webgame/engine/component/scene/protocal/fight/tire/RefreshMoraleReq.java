package com.snail.webgame.engine.component.scene.protocal.fight.tire;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RefreshMoraleReq extends MessageBody{

	private long fightId;
	private int status;
	private int count;
	private List list;
	
	protected void setSequnce(ProtocolSequence ps) {
		 
		ps.add("fightId", 0);
		ps.add("status", 0);
		ps.add("count", 0);
		ps.addObjectArray("list", "com.snail.webgame.engine.component.scene.protocal.fight.tire.RefreshRe"
				, "count");
	}

	public long getFightId() {
		return fightId;
	}

	public void setFightId(long fightId) {
		this.fightId = fightId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	
 
}
