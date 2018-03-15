package com.snail.webgame.engine.common.info;

/**
 * @author wangxf
 * @date 2012-8-1 NPC模型基本信息类
 */
public class NPCModelInfo {
	protected int no;	// npcno
	protected String name;	// npc名称
	protected int level;	// npc等级
	protected int npcType;	// npc类型
	protected int hp;	// npc血量
	protected int mp;	// npc魔法量
	protected int resource;	// npc模型
	protected int attack;	// npc攻击力
	protected int defend;	// npc防御力
	protected int maxMoveTime;	// 最大移动速度
	protected int function;	// NPC的功能

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getNpcType() {
		return npcType;
	}

	public void setNpcType(int npcType) {
		this.npcType = npcType;
	}

	public int getFunction() {
		return function;
	}

	public void setFunction(int function) {
		this.function = function;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public int getResource() {
		return resource;
	}

	public void setResource(int resource) {
		this.resource = resource;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefend() {
		return defend;
	}

	public void setDefend(int defend) {
		this.defend = defend;
	}

	public int getMaxMoveTime() {
		return maxMoveTime;
	}

	public void setMaxMoveTime(int maxMoveTime) {
		this.maxMoveTime = maxMoveTime;
	}

}
