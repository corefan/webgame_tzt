package com.snail.webgame.engine.component.scene.protocal.move;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class MoveResp extends MessageBody {
	private int actionActor;
	private long roleId;
	private float currX;
	private float currY;
	private float currZ;
	private int moveMaxTime;//移动时间
	
	private String moveList;//移动路线
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("actionActor", 0);
		ps.add("roleId", 0);
		ps.add("currX", 0);
		ps.add("currY", 0);
		ps.add("currZ", 0);
		ps.add("moveMaxTime", 0);
		ps.addString("moveList", "flashCode", 0);
	}
	public int getActionActor() {
		return actionActor;
	}
	public void setActionActor(int actionActor) {
		this.actionActor = actionActor;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public float getCurrX() {
		return currX;
	}
	public void setCurrX(float currX) {
		this.currX = currX;
	}
	public float getCurrY() {
		return currY;
	}
	public void setCurrY(float currY) {
		this.currY = currY;
	}
	public float getCurrZ() {
		return currZ;
	}
	public void setCurrZ(float currZ) {
		this.currZ = currZ;
	}
	public int getMoveMaxTime() {
		return moveMaxTime;
	}
	public void setMoveMaxTime(int moveMaxTime) {
		this.moveMaxTime = moveMaxTime;
	}
	public String getMoveList() {
		return moveList;
	}
	public void setMoveList(String moveList) {
		this.moveList = moveList;
	}

}
