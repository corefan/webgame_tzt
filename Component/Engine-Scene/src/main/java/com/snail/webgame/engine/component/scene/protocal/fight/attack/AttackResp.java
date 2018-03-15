package com.snail.webgame.engine.component.scene.protocal.fight.attack;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class AttackResp extends MessageBody {

	private long attackAim;
	private int attackAimType;
	private int attackAimCurrHP;
	private long attack;
	private int attackType;
	private int aimLossHP;
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("attackAim", 0);
		ps.add("attackAimType", 0);
		ps.add("attackAimCurrHP", 0);
		ps.add("attack", 0);
		ps.add("attackType", 0);
		ps.add("aimLossHP", 0);
	}
	public long getAttackAim() {
		return attackAim;
	}
	public void setAttackAim(long attackAim) {
		this.attackAim = attackAim;
	}
	public int getAttackAimCurrHP() {
		return attackAimCurrHP;
	}
	public void setAttackAimCurrHP(int attackAimCurrHP) {
		this.attackAimCurrHP = attackAimCurrHP;
	}
	public long getAttack() {
		return attack;
	}
	public void setAttack(long attack) {
		this.attack = attack;
	}
	public int getAttackAimType() {
		return attackAimType;
	}
	public void setAttackAimType(int attackAimType) {
		this.attackAimType = attackAimType;
	}
	public int getAttackType() {
		return attackType;
	}
	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}
	public int getAimLossHP() {
		return aimLossHP;
	}
	public void setAimLossHP(int aimLossHP) {
		this.aimLossHP = aimLossHP;
	}
}
