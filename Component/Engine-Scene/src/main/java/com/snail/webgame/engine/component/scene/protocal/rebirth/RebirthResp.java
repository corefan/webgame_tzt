package com.snail.webgame.engine.component.scene.protocal.rebirth;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf
 * @date 2012-11-23
 * 复活响应协议类
 */
public class RebirthResp extends MessageBody {
	private int result;
	private long roleId;	// 角色ID
	private float currX;	// 当前X坐标
	private float currY;	// 当前Y坐标
	private float currZ;	// 当前Z坐标
	private int currHP;	// 角色当前生命值
	private int currMP;	// 当前法力值
	private int status;//角色状态

	/* (non-Javadoc)
	 * @see org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core.protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("result", 0);
		ps.add("roleId", 0);
		ps.add("currX", 0);
		ps.add("currY", 0);
		ps.add("currZ", 0);
		ps.add("currHP", 0);
		ps.add("currMP", 0);
		ps.add("status", 0);
	}
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
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

	public int getCurrHP() {
		return currHP;
	}

	public void setCurrHP(int currHP) {
		this.currHP = currHP;
	}

	public int getCurrMP() {
		return currMP;
	}

	public void setCurrMP(int currMP) {
		this.currMP = currMP;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
