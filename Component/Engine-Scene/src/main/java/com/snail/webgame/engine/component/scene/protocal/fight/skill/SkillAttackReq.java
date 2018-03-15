package com.snail.webgame.engine.component.scene.protocal.fight.skill;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class SkillAttackReq extends MessageBody {

	private long attackAim;
	private byte attackAimType;
	private float attackAimCurrX;
	private float attackAimCurrY;
	private float attackAimCurrZ;
	private int skillType;
	private int skillLevel;
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("attackAim", 0);
		ps.add("attackAimType", 0);
		ps.add("attackAimCurrX", 0);
		ps.add("attackAimCurrY", 0);
		ps.add("attackAimCurrZ", 0);
		ps.add("skillType", 0);
		ps.add("skillLevel", 0);
	}
	public long getAttackAim() {
		return attackAim;
	}
	public void setAttackAim(long attackAim) {
		this.attackAim = attackAim;
	}
	public byte getAttackAimType() {
		return attackAimType;
	}
	public void setAttackAimType(byte attackAimType) {
		this.attackAimType = attackAimType;
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
	public int getSkillType() {
		return skillType;
	}
	public int getSkillLevel() {
		return skillLevel;
	}
	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}
	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}

}
