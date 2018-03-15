package com.snail.webgame.engine.component.scene.common;

import com.snail.webgame.engine.common.info.BuffInfo;

public class DefendInfo {

	protected int time;
	protected String attackPhalanxId;
	protected String defendPhalanxId;
 
	protected long aimLossHP;
	protected long aimLossMP;
	protected int face;
	protected int skillType;
	protected int skillLevel;
	protected int skillKeepTime;
	protected int type;//伤害类型(1-普通伤害 2- 3-反弹伤害 4-Bingo)
	protected byte flag;//(1-暴击 2-MISS)
	protected BuffInfo buff;
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	public String getAttackPhalanxId() {
		return attackPhalanxId;
	}
	public void setAttackPhalanxId(String attackPhalanxId) {
		this.attackPhalanxId = attackPhalanxId;
	}
	public String getDefendPhalanxId() {
		return defendPhalanxId;
	}
	public void setDefendPhalanxId(String defendPhalanxId) {
		this.defendPhalanxId = defendPhalanxId;
	}
 
	public long getAimLossHP() {
		return aimLossHP;
	}
	public void setAimLossHP(long aimLossHP) {
		this.aimLossHP = aimLossHP;
	}
	public int getFace() {
		return face;
	}
	public void setFace(int face) {
		this.face = face;
	}
	public BuffInfo getBuff() {
		return buff;
	}
	public void setBuff(BuffInfo buff) {
		this.buff = buff;
	}
	public int getSkillType() {
		return skillType;
	}
	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}
	public int getSkillLevel() {
		return skillLevel;
	}
	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}
	public int getSkillKeepTime() {
		return skillKeepTime;
	}
	public void setSkillKeepTime(int skillKeepTime) {
		this.skillKeepTime = skillKeepTime;
	}
	public long getAimLossMP() {
		return aimLossMP;
	}
	public void setAimLossMP(long aimLossMP) {
		this.aimLossMP = aimLossMP;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public byte getFlag() {
		return flag;
	}
	public void setFlag(byte flag) {
		this.flag = flag;
	}
 
 
 
 
	public DefendInfo clone(){
		try {
			return (DefendInfo)super.clone();
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
