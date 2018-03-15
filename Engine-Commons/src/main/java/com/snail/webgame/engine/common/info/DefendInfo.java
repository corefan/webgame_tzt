package com.snail.webgame.engine.common.info;

/**
 * 受到攻击信息
 * @author zenggy
 *
 */
public class DefendInfo {

	private long defendId;//被攻击者
	private int defendType;//被攻击者类型  0-NPC 1-玩家
	private int aimLossHP;//减少血
	private int currHP;
	public int getAimLossHP() {
		return aimLossHP;
	}
	public void setAimLossHP(int aimLossHP) {
		this.aimLossHP = aimLossHP;
	}
	public long getDefendId() {
		return defendId;
	}
	public void setDefendId(long defendId) {
		this.defendId = defendId;
	}
	public int getDefendType() {
		return defendType;
	}
	public void setDefendType(int defendType) {
		this.defendType = defendType;
	}
	public int getCurrHP() {
		return currHP;
	}
	public void setCurrHP(int currHP) {
		this.currHP = currHP;
	}
	
	
	
	
}
