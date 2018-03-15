package com.snail.webgame.engine.common.info;

import com.snail.webgame.engine.common.to.BaseTO;

public class EquipInfo extends BaseTO{

	private long roleId;
	private int status;//装备状态 0-正常 1-已装备
	private int equipNO;
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getEquipNO() {
		return equipNO;
	}
	public void setEquipNO(int equipNO) {
		this.equipNO = equipNO;
	}
	@Override
	public byte getSaveMode() {
		return ONLINE;
	}
	
}
