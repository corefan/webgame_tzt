package com.snail.webgame.engine.component.scene.info;

import java.util.List;

public class SkillUpgradeXMLInfo {
 
	private int upLevel;
	private int burstMode;	// 触发方式 0按键触发 1选择目标 2选择范围
	private int keepTime;	// 持续时间
	private int flyTime;	// 每格飞行时间
	private int coolTime;	// 冷却时间
	private int aimObject;	// 施法目标 1敌人 2自己
	private int beatObject;	// 作用目标 0玩家+NPC 1玩家 2NPC
	private int beatObjectType;
	private int sDMax;	// 最大射程，最小射程默认为0
	private int beatType;	// 波及类型 0 单体 1 群体圆形指定目标  2群体三角形 指定目标
	private int beatRange;	// 波及范围 单体时，为目标所处的格子 群体指定目标时，以目标为中心的一圈格子 直线时，以目标位起点的直线格子 全体,全地图作用 群体不指定目标时，以目标为中心的一圈格子
	private int mP;	// 施放技能消耗的MP
	private int hP;	// 施放技能消耗的HP
	private double rebound;
	private int blink;
	private int resistType;
	private double resist;
	private int stopEnemyMove;
	private int stopEnemySkill;
	private String armyProperty;
	private String armyFunction ;
	private String heroProperty ;
	private String heroFuncion;
	private double harmHero;
	private double harmNPC;
	
	private double needSTR;
	private double needVIT;
	private double needCP;//英雄统帅 宠物狡诈
	private double needINT;
	
	private int isAct;//0-主动技能 1-被动技能
	
	private int burstType;//0-主动触发 1-被攻击触发 2- 攻击触发
	
	private int persistCount;//持续次数
	
	private List<Integer> buffNo;//BUFF编号
 
	public int getUpLevel() {
		return upLevel;
	}
	public void setUpLevel(int upLevel) {
		this.upLevel = upLevel;
	}
	public int getBurstMode() {
		return burstMode;
	}
	public void setBurstMode(int burstMode) {
		this.burstMode = burstMode;
	}
	public int getKeepTime() {
		return keepTime;
	}
	public void setKeepTime(int keepTime) {
		this.keepTime = keepTime;
	}
	
	public int getFlyTime() {
		return flyTime;
	}
	public int getPersistCount() {
		return persistCount;
	}
	public void setPersistCount(int persistCount) {
		this.persistCount = persistCount;
	}
	public void setFlyTime(int flyTime) {
		this.flyTime = flyTime;
	}
	public int getCoolTime() {
		return coolTime;
	}
	public void setCoolTime(int coolTime) {
		this.coolTime = coolTime;
	}
	public int getAimObject() {
		return aimObject;
	}
	public void setAimObject(int aimObject) {
		this.aimObject = aimObject;
	}
	public List<Integer> getBuffNo() {
		return buffNo;
	}
	public void setBuffNo(List<Integer> buffNo) {
		this.buffNo = buffNo;
	}
	public int getBeatObject() {
		return beatObject;
	}
	public void setBeatObject(int beatObject) {
		this.beatObject = beatObject;
	}
 
	public int getBeatObjectType() {
		return beatObjectType;
	}
	public void setBeatObjectType(int beatObjectType) {
		this.beatObjectType = beatObjectType;
	}
	public int getSDMax() {
		return sDMax;
	}
	public void setSDMax(int max) {
		sDMax = max;
	}
	public int getBeatType() {
		return beatType;
	}
	public void setBeatType(int beatType) {
		this.beatType = beatType;
	}
	public int getBeatRange() {
		return beatRange;
	}
	public void setBeatRange(int beatRange) {
		this.beatRange = beatRange;
	}
	public int getMP() {
		return mP;
	}
	public void setMP(int mp) {
		mP = mp;
	}
	public int getHP() {
		return hP;
	}
	public void setHP(int hp) {
		hP = hp;
	}
 
	public double getRebound() {
		return rebound;
	}
	public void setRebound(double rebound) {
		this.rebound = rebound;
	}
	public int getBlink() {
		return blink;
	}
	public void setBlink(int blink) {
		this.blink = blink;
	}
	public int getResistType() {
		return resistType;
	}
	public void setResistType(int resistType) {
		this.resistType = resistType;
	}
 
	public double getResist() {
		return resist;
	}
	public void setResist(double resist) {
		this.resist = resist;
	}
	public int getStopEnemyMove() {
		return stopEnemyMove;
	}
	public void setStopEnemyMove(int stopEnemyMove) {
		this.stopEnemyMove = stopEnemyMove;
	}
	public int getStopEnemySkill() {
		return stopEnemySkill;
	}
	public void setStopEnemySkill(int stopEnemySkill) {
		this.stopEnemySkill = stopEnemySkill;
	}
	public String getArmyProperty() {
		return armyProperty;
	}
	public void setArmyProperty(String armyProperty) {
		this.armyProperty = armyProperty;
	}
	 
	public String getArmyFunction() {
		return armyFunction;
	}
	public void setArmyFunction(String armyFunction) {
		this.armyFunction = armyFunction;
	}
	public String getHeroProperty() {
		return heroProperty;
	}
	public void setHeroProperty(String heroProperty) {
		this.heroProperty = heroProperty;
	}
	public String getHeroFuncion() {
		return heroFuncion;
	}
	public void setHeroFuncion(String heroFuncion) {
		this.heroFuncion = heroFuncion;
	}
	public double getHarmHero() {
		return harmHero;
	}
	public void setHarmHero(double harmHero) {
		this.harmHero = harmHero;
	}
	public double getHarmNPC() {
		return harmNPC;
	}
	public void setHarmNPC(double harmNPC) {
		this.harmNPC = harmNPC;
	}
	public double getNeedSTR() {
		return needSTR;
	}
	public void setNeedSTR(double needSTR) {
		this.needSTR = needSTR;
	}
	public double getNeedVIT() {
		return needVIT;
	}
	public void setNeedVIT(double needVIT) {
		this.needVIT = needVIT;
	}
	public double getNeedCP() {
		return needCP;
	}
	public void setNeedCP(double needCP) {
		this.needCP = needCP;
	}
	public double getNeedINT() {
		return needINT;
	}
	public void setNeedINT(double needINT) {
		this.needINT = needINT;
	}
	public int getIsAct() {
		return isAct;
	}
	public void setIsAct(int isAct) {
		this.isAct = isAct;
	}
	public int getBurstType() {
		return burstType;
	}
	public void setBurstType(int burstType) {
		this.burstType = burstType;
	}
	 
	 

}
