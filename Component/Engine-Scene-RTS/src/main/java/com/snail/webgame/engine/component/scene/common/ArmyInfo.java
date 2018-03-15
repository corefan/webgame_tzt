package com.snail.webgame.engine.component.scene.common;

import com.snail.webgame.engine.component.scene.cache.ItemLossMap;

public class ArmyInfo {

	/**
	 * 部队ID
	 */
	protected String armyId;
	/**
	 * 英雄XML编号
	 */
	protected int heroNo;
	/**
	 * 角色ID
	 */
	protected int roleId;
	/**
	 * 接入服务器ID
	 */
	protected int gateServerId;
	/**
	 * 角色头像
	 */
	protected String rolePic;
	/**
	 * 角色等级
	 */
	protected int roleLevel;
	/**
	 * 士气
	 */
	protected double morale;
	/**
	 * 角色名称
	 */
	protected String roleName;
//	protected String armyName;
	
	/**
	 * 兵的数量
	 */
	protected int robotNum;
	/**
	 * 战斗所属方
	 */
	protected int side;
	/**
	 * 角色阵营
	 */
	protected int roleRace;
	/**
	 * 0-正常 1-死亡
	 */
	protected byte status;
	
	/**
	 * 方阵列表
	 */
	protected ArmyPhalanx  armyPhalanx[];
 
	/**
	 * 死亡信息
	 */
	protected ItemLossMap itemMap = new ItemLossMap();
	
	
	
	public String getArmyId() {
		return armyId;
	}
	public void setArmyId(String armyId) {
		this.armyId = armyId;
	}
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
	public int getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}
	public String getRolePic() {
		return rolePic;
	}
	public int getHeroNo() {
		return heroNo;
	}
	public void setHeroNo(int heroNo) {
		this.heroNo = heroNo;
	}
	public void setRolePic(String rolePic) {
		this.rolePic = rolePic;
	}
	public double getMorale() {
		return morale;
	}
	public void setMorale(double morale) {
		this.morale = morale;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getRobotNum() {
		return robotNum;
	}
	public void setRobotNum(int robotNum) {
		this.robotNum = robotNum;
	}
	public int getSide() {
		return side;
	}
	public void setSide(int side) {
		this.side = side;
	}
	public ArmyPhalanx[] getArmyPhalanx() {
		return armyPhalanx;
	}
	public void setArmyPhalanx(ArmyPhalanx[] armyPhalanx) {
		this.armyPhalanx = armyPhalanx;
	}
	public int getRoleRace() {
		return roleRace;
	}
	public void setRoleRace(int roleRace) {
		this.roleRace = roleRace;
	}
	public ItemLossMap getItemMap() {
		return itemMap;
	}
	public void setItemMap(ItemLossMap itemMap) {
		this.itemMap = itemMap;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	
	
	public void addPhalanx(ArmyPhalanx phalanx){
		if(this.armyPhalanx != null){
			for(int i = 0; i < this.armyPhalanx.length; i ++){
				if(armyPhalanx[i] == null){
					armyPhalanx[i] = phalanx;
					break;
				}
			}
		}
	}
	
	public void delPhalanx(String phalanxId){
		if(this.armyPhalanx != null){
			for(int i = 0; i < this.armyPhalanx.length; i ++){
				if(armyPhalanx[i] != null && armyPhalanx[i].getPhalanxId().equals(phalanxId)){
					armyPhalanx[i] = null;
					break;
				}
			}
		}
	}
}
