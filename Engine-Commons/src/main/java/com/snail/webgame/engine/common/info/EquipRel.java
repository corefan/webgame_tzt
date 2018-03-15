package com.snail.webgame.engine.common.info;

import com.snail.webgame.engine.common.to.BaseTO;

public class EquipRel extends BaseTO{

	private long roleId;
	private long equipId;
	private int partNO;
	public long getEquipId() {
		return equipId;
	}
	public void setEquipId(long equipId) {
		this.equipId = equipId;
	}
	public int getPartNO() {
		return partNO;
	}
	public void setPartNO(int partNO) {
		this.partNO = partNO;
	}
	@Override
	public byte getSaveMode() {
		return ONLINE;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
}
