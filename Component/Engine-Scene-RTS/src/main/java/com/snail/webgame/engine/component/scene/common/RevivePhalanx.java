package com.snail.webgame.engine.component.scene.common;

/**
 * 需要复活的方阵
 * @author zenggy
 *
 */
public class RevivePhalanx {
	/**
	 * 角色ID
	 */
	protected int roleId;
	/**
	 * 部队ID
	 */
	protected String armyId;
	/**
	 * 方阵ID
	 */
	protected String phalanxId;
	/**
	 * 复活时间
	 */
	protected long currReviveTime;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getArmyId() {
		return armyId;
	}
	public void setArmyId(String armyId) {
		this.armyId = armyId;
	}
	public String getPhalanxId() {
		return phalanxId;
	}
	public void setPhalanxId(String phalanxId) {
		this.phalanxId = phalanxId;
	}
	public long getCurrReviveTime() {
		return currReviveTime;
	}
	public void setCurrReviveTime(long currReviveTime) {
		this.currReviveTime = currReviveTime;
	}
	
	
}
