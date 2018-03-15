package com.snail.webgame.engine.component.scene.protocal.fight.skill;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class SkillAttackResp extends MessageBody {

	private int result;
	private long attack;
	private int attackType;
	private int skillType;
	private int skillLevel;
	private float attackCurrX;
	private float attackCurrY;
	private float attackCurrZ;
	private float attackAimCurrX;
	private float attackAimCurrY;
	private float attackAimCurrZ;
	
	private int defendCount;
	private List<DefendInfoResp> defendList;
	
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("result", 0);
		ps.add("attack", 0);
		ps.add("attackType", 0);
		ps.add("skillType", 0);
		ps.add("skillLevel", 0);
		ps.add("attackCurrX", 0);
		ps.add("attackCurrY", 0);
		ps.add("attackCurrZ", 0);
		ps.add("attackAimCurrX", 0);
		ps.add("attackAimCurrY", 0);
		ps.add("attackAimCurrZ", 0);
		ps.add("defendCount", 0);
		ps.addObjectArray("defendList", "com.snail.webgame.engine.component.scene.protocal.fight.skill.DefendInfoResp", "defendCount");
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public long getAttack() {
		return attack;
	}

	public void setAttack(long attack) {
		this.attack = attack;
	}

	public float getAttackAimCurrX() {
		return attackAimCurrX;
	}

	public void setAttackAimCurrX(float attackAimCurrX) {
		this.attackAimCurrX = attackAimCurrX;
	}

	public float getAttackAimCurrY() {
		return attackAimCurrY;
	}

	public void setAttackAimCurrY(float attackAimCurrY) {
		this.attackAimCurrY = attackAimCurrY;
	}

	public float getAttackAimCurrZ() {
		return attackAimCurrZ;
	}

	public void setAttackAimCurrZ(float attackAimCurrZ) {
		this.attackAimCurrZ = attackAimCurrZ;
	}

	public int getAttackType() {
		return attackType;
	}

	public void setAttackType(int attackType) {
		this.attackType = attackType;
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

	public float getAttackCurrX() {
		return attackCurrX;
	}

	public void setAttackCurrX(float attackCurrX) {
		this.attackCurrX = attackCurrX;
	}

	public float getAttackCurrY() {
		return attackCurrY;
	}

	public void setAttackCurrY(float attackCurrY) {
		this.attackCurrY = attackCurrY;
	}

	public float getAttackCurrZ() {
		return attackCurrZ;
	}

	public void setAttackCurrZ(float attackCurrZ) {
		this.attackCurrZ = attackCurrZ;
	}

	public int getDefendCount() {
		return defendCount;
	}

	public void setDefendCount(int defendCount) {
		this.defendCount = defendCount;
	}

	public List<DefendInfoResp> getDefendList() {
		return defendList;
	}

	public void setDefendList(List<DefendInfoResp> defendList) {
		this.defendList = defendList;
	}
	

}
