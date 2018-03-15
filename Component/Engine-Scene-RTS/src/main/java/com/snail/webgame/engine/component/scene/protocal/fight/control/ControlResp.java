package com.snail.webgame.engine.component.scene.protocal.fight.control;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class ControlResp extends MessageBody{

 
	private int result;
	private long fightId;
	private String phalanxId;
	private int skillType;
	private int skillLevel;
	private long lastReleaseTime;
	private int aimX;
	private int aimY;
	private String aimPhalanxId;
	private int currHP;
	private int currMP;
	private int itemNum;
	
	private int forceValue;//勇武
	private int strategyValue;//谋略
	private int politicalValue;//政治
	
	private double maxAttackValue;//攻击
	private double hitRate;//精度（物理）	
	private double critPHY;//暴击（物理）
	private double critSkill;//暴击（战计）
	private int attackMaxTime;//攻速
	
	private double defendValue;//防御
	private double critC;//抗暴（全）	
	private double evade;//闪避（物理）
	private int currMoveMaxTime;//移速
	
	
	protected void setSequnce(ProtocolSequence ps) {
		
		ps.add("result", 0);
		ps.add("fightId", 0);
		ps.addString("phalanxId", "flashCode",0);
		ps.add("skillType",0);
		ps.add("skillLevel", 0);
		ps.add("lastReleaseTime", 0);
		ps.add("aimX", 0);
		ps.add("aimY", 0);
		ps.addString("aimPhalanxId", "flashCode",0);
		ps.add("currHP", 0);
		ps.add("currMP", 0);
		ps.add("itemNum", 0);
		
		ps.add("forceValue", 0);
		ps.add("strategyValue", 0);
		ps.add("politicalValue", 0);
		
		ps.add("maxAttackValue", 0);
		ps.add("hitRate", 0);
		ps.add("critPHY", 0);
		ps.add("critSkill", 0);
		ps.add("attackMaxTime", 0);
		
		ps.add("defendValue", 0);
		ps.add("critC", 0);
		ps.add("evade", 0);
		ps.add("currMoveMaxTime", 0);
	}

	
	public int getResult() {
		return result;
	}


	public void setResult(int result) {
		this.result = result;
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

	public long getLastReleaseTime() {
		return lastReleaseTime;
	}

	public void setLastReleaseTime(long lastReleaseTime) {
		this.lastReleaseTime = lastReleaseTime;
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

	public String getAimPhalanxId() {
		return aimPhalanxId;
	}

	public void setAimPhalanxId(String aimPhalanxId) {
		this.aimPhalanxId = aimPhalanxId;
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


	public int getItemNum() {
		return itemNum;
	}


	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}


	public int getForceValue() {
		return forceValue;
	}


	public void setForceValue(int forceValue) {
		this.forceValue = forceValue;
	}


	public int getStrategyValue() {
		return strategyValue;
	}


	public void setStrategyValue(int strategyValue) {
		this.strategyValue = strategyValue;
	}


	public int getPoliticalValue() {
		return politicalValue;
	}


	public void setPoliticalValue(int politicalValue) {
		this.politicalValue = politicalValue;
	}


	public double getMaxAttackValue() {
		return maxAttackValue;
	}


	public void setMaxAttackValue(double maxAttackValue) {
		this.maxAttackValue = maxAttackValue;
	}


	public double getHitRate() {
		return hitRate;
	}


	public void setHitRate(double hitRate) {
		this.hitRate = hitRate;
	}


	public double getCritPHY() {
		return critPHY;
	}


	public void setCritPHY(double critPHY) {
		this.critPHY = critPHY;
	}


	public double getCritSkill() {
		return critSkill;
	}


	public void setCritSkill(double critSkill) {
		this.critSkill = critSkill;
	}


	public int getAttackMaxTime() {
		return attackMaxTime;
	}


	public void setAttackMaxTime(int attackMaxTime) {
		this.attackMaxTime = attackMaxTime;
	}


	public double getDefendValue() {
		return defendValue;
	}


	public void setDefendValue(double defendValue) {
		this.defendValue = defendValue;
	}


	public double getCritC() {
		return critC;
	}


	public void setCritC(double critC) {
		this.critC = critC;
	}


	public double getEvade() {
		return evade;
	}


	public void setEvade(double evade) {
		this.evade = evade;
	}


	public int getCurrMoveMaxTime() {
		return currMoveMaxTime;
	}


	public void setCurrMoveMaxTime(int currMoveMaxTime) {
		this.currMoveMaxTime = currMoveMaxTime;
	}
	
	 

}
