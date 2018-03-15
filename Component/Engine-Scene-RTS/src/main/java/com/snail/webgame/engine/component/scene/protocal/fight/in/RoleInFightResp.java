package com.snail.webgame.engine.component.scene.protocal.fight.in;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class RoleInFightResp extends MessageBody {

	private int result;
	private long fightId;
	// private int landForm;
	private String fightMapType;
	private int fightType;
	private long endTime1;
	private long endTime2;
	private int controlType;
	private int count;
	private List list;

	// private long duelEndTime1;
	// private int maxDeulRound;
	// private int duelState;
	// private int duelPhalanxCount;
	// private List duelPhalanxList;

	protected void setSequnce(ProtocolSequence ps) {

		ps.add("result", 0);
		ps.add("fightId", 0);
		// ps.add("landForm", 0);
		ps.addString("fightMapType", "flashCode", 0);
		ps.add("fightType", 0);
		ps.add("endTime1", 0);
		ps.add("endTime2", 0);
		ps.add("controlType", 0);
		ps.add("count", 0);
		ps.addObjectArray(
				"list",
				"com.snail.webgame.engine.component.scene.protocal.fight.in.RoleInFightArmy",
				"count");
		// ps.add("duelEndTime1", 0);
		// ps.add("maxDeulRound", 0);
		// ps.add("duelState", 0);
		// ps.add("duelPhalanxCount", 0);
		// ps.addObjectArray("duelPhalanxList",
		// "com.snail.webgame.game.protocal.fight.in.DuelPhalanxRe",
		// "duelPhalanxCount");
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public long getFightId() {
		return fightId;
	}

	public void setFightId(long fightId) {
		this.fightId = fightId;
	}

	// public int getLandForm() {
	// return landForm;
	// }
	//
	// public void setLandForm(int landForm) {
	// this.landForm = landForm;
	// }

	public String getFightMapType() {
		return fightMapType;
	}

	public void setFightMapType(String fightMapType) {
		this.fightMapType = fightMapType;
	}

	public int getFightType() {
		return fightType;
	}

	public void setFightType(int fightType) {
		this.fightType = fightType;
	}

	public long getEndTime1() {
		return endTime1;
	}

	public void setEndTime1(long endTime1) {
		this.endTime1 = endTime1;
	}

	public long getEndTime2() {
		return endTime2;
	}

	public void setEndTime2(long endTime2) {
		this.endTime2 = endTime2;
	}

	public int getControlType() {
		return controlType;
	}

	public void setControlType(int controlType) {
		this.controlType = controlType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	// public long getDuelEndTime1() {
	// return duelEndTime1;
	// }
	//
	// public void setDuelEndTime1(long duelEndTime1) {
	// this.duelEndTime1 = duelEndTime1;
	// }
	//
	// public int getMaxDeulRound() {
	// return maxDeulRound;
	// }
	//
	// public void setMaxDeulRound(int maxDeulRound) {
	// this.maxDeulRound = maxDeulRound;
	// }
	//
	// public int getDuelPhalanxCount() {
	// return duelPhalanxCount;
	// }
	//
	// public void setDuelPhalanxCount(int duelPhalanxCount) {
	// this.duelPhalanxCount = duelPhalanxCount;
	// }
	//
	// public List getDuelPhalanxList() {
	// return duelPhalanxList;
	// }
	//
	// public void setDuelPhalanxList(List duelPhalanxList) {
	// this.duelPhalanxList = duelPhalanxList;
	// }
	//
	// public int getDuelState() {
	// return duelState;
	// }
	//
	// public void setDuelState(int duelState) {
	// this.duelState = duelState;
	// }

}
