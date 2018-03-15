package com.snail.webgame.engine.component.scene.protocal.fight.attack;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class PhalanxAttackReq extends MessageBody{

 
	private long fightId;
	private String phalanxId;
	private int attackCurrX;
	private int attackCurrY;
	private int currAttackType;
	private int currSkillLevel;
	private long lastReleaseTime;
	private int currAttackNum;
	private long currAttackHP;
	private long currAttackMP;
	private int attackTime;
	private String aimPhalanxId;
	private int aimX;
	private int aimY;
	private int face;
	private String reserve;
	
	
	protected void setSequnce(ProtocolSequence ps) {
	 
		ps.add("fightId", 0);
		ps.addString("phalanxId", "flashCode", 0);
		ps.add("attackCurrX",  0);
		ps.add("attackCurrY",  0);
		ps.add("currAttackType",  0);
		ps.add("currSkillLevel", 0);
		ps.add("lastReleaseTime", 0);
		ps.add("currAttackNum", 0);
		ps.add("currAttackHP", 0);
		ps.add("currAttackMP", 0);
		ps.add("attackTime", 0);
		ps.addString("aimPhalanxId", "flashCode", 0);
		ps.add("aimX", 0);
		ps.add("aimY", 0);
		ps.add("face", 0);
		ps.addString("reserve", "flashCode", 0);
		
	}


	public long getFightId() {
		return fightId;
	}


	public void setFightId(long fightId) {
		this.fightId = fightId;
	}


	public String getPhalanxId() {
		return phalanxId;
	}


	public void setPhalanxId(String phalanxId) {
		this.phalanxId = phalanxId;
	}


	public int getAttackCurrX() {
		return attackCurrX;
	}


	public void setAttackCurrX(int attackCurrX) {
		this.attackCurrX = attackCurrX;
	}


	public int getAttackCurrY() {
		return attackCurrY;
	}


	public void setAttackCurrY(int attackCurrY) {
		this.attackCurrY = attackCurrY;
	}


	public int getCurrAttackType() {
		return currAttackType;
	}


	public void setCurrAttackType(int currAttackType) {
		this.currAttackType = currAttackType;
	}


	public int getCurrSkillLevel() {
		return currSkillLevel;
	}


	public void setCurrSkillLevel(int currSkillLevel) {
		this.currSkillLevel = currSkillLevel;
	}


	public long getLastReleaseTime() {
		return lastReleaseTime;
	}


	public void setLastReleaseTime(long lastReleaseTime) {
		this.lastReleaseTime = lastReleaseTime;
	}


	public int getCurrAttackNum() {
		return currAttackNum;
	}


	public void setCurrAttackNum(int currAttackNum) {
		this.currAttackNum = currAttackNum;
	}


	public long getCurrAttackHP() {
		return currAttackHP;
	}


	public void setCurrAttackHP(long currAttackHP) {
		this.currAttackHP = currAttackHP;
	}


	public long getCurrAttackMP() {
		return currAttackMP;
	}


	public void setCurrAttackMP(long currAttackMP) {
		this.currAttackMP = currAttackMP;
	}


	public int getAttackTime() {
		return attackTime;
	}


	public void setAttackTime(int attackTime) {
		this.attackTime = attackTime;
	}


	public String getAimPhalanxId() {
		return aimPhalanxId;
	}


	public void setAimPhalanxId(String aimPhalanxId) {
		this.aimPhalanxId = aimPhalanxId;
	}


	public int getAimX() {
		return aimX;
	}


	public void setAimX(int aimX) {
		this.aimX = aimX;
	}


	public int getAimY() {
		return aimY;
	}


	public void setAimY(int aimY) {
		this.aimY = aimY;
	}


	public int getFace() {
		return face;
	}


	public void setFace(int face) {
		this.face = face;
	}


	public String getReserve() {
		return reserve;
	}


	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

 
}
