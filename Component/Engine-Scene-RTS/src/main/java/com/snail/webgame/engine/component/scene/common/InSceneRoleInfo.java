package com.snail.webgame.engine.component.scene.common;


public class InSceneRoleInfo {

	protected int roleId;
	
	protected String roleName;
 
	protected int gateServerId;

	protected int type;//0-参与者 1- 观察者
	
	protected long time;//最后一次发言时间，用于聊天间隔
	protected int side;//-1观察放 0-进攻方 1-防御方
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getGateServerId() {
		return gateServerId;
	}

	public void setGateServerId(int gateServerId) {
		this.gateServerId = gateServerId;
	}

	public int getType() {
		return type;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}
	
	 
 
}
