package com.snail.webgame.engine.component.scene.protocal.fight.addphalanx;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

import com.snail.webgame.engine.component.scene.protocal.fight.in.RoleInFightArmy;
import com.snail.webgame.engine.component.scene.protocal.fight.in.RoleInFightRe;

public class AddPhalanxResp extends MessageBody {

	private long fightId;
	private int count;
	private List<RoleInFightArmy> list;
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("fightId", 0);
		ps.add("count", 0);
		ps.addObjectArray("list", "com.snail.webgame.engine.component.scene.protocal.fight.in.RoleInFightArmy", "count");
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
	public List<RoleInFightArmy> getList() {
		return list;
	}
	public void setList(List<RoleInFightArmy> list) {
		this.list = list;
	}

}
