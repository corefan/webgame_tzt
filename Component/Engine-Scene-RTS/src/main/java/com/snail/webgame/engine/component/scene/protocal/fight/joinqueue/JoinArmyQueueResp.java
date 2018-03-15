package com.snail.webgame.engine.component.scene.protocal.fight.joinqueue;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class JoinArmyQueueResp extends MessageBody 
{
	private int result;
	private long fightId;
	private int fightType;
	
	@Override
	protected void setSequnce(ProtocolSequence ps) 
	{
		ps.add("result", 0);
		ps.add("fightId", 0);
		ps.add("fightType", 0);
	}

	public long getFightId() {
		return fightId;
	}

	public void setFightId(long fightId) {
		this.fightId = fightId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getFightType() {
		return fightType;
	}

	public void setFightType(int fightType) {
		this.fightType = fightType;
	}


}
