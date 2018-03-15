package com.snail.webgame.engine.component.scene.event;

public class AttackEvent extends Event {
	
	protected long attackAim;//攻击目标
	protected int attackAimType;//攻击目标类型：0-NPC 1-玩家
	protected float attackAimCurrX;//攻击目标当前X坐标
	protected float attackAimCurrY;//攻击目标当前Y坐标
	protected float attackAimCurrZ;//攻击目标当前Z坐标

	public AttackEvent(long targetId, byte eventActor, long attackAim, int attackAimType) {
		super(targetId, eventActor, false);
		this.attackAim = attackAim;
		this.attackAimType = attackAimType;
		this.attackAimCurrX = -1;
		this.attackAimCurrY = -1;
	}
	public AttackEvent(long targetId, byte eventActor, long attackAim, int attackAimType, float attackAimCurrX, float attackAimCurrY, float attackAimCurrZ) {
		super(targetId, eventActor, false);
		this.attackAim = attackAim;
		this.attackAimType = attackAimType;
		this.attackAimCurrX = attackAimCurrX;
		this.attackAimCurrY = attackAimCurrY;
		this.attackAimCurrZ = attackAimCurrZ;
	}

	public long getAttackAim() {
		return attackAim;
	}

	public int getAttackAimType() {
		return attackAimType;
	}

	public float getAttackAimCurrX() {
		return attackAimCurrX;
	}

	public float getAttackAimCurrY() {
		return attackAimCurrY;
	}
	public float getAttackAimCurrZ() {
		return attackAimCurrZ;
	}
	public void setAttackAim(long attackAim) {
		this.attackAim = attackAim;
	}
	public void setAttackAimType(int attackAimType) {
		this.attackAimType = attackAimType;
	}
	public void setAttackAimCurrX(float attackAimCurrX) {
		this.attackAimCurrX = attackAimCurrX;
	}
	public void setAttackAimCurrY(float attackAimCurrY) {
		this.attackAimCurrY = attackAimCurrY;
	}
	public void setAttackAimCurrZ(float attackAimCurrZ) {
		this.attackAimCurrZ = attackAimCurrZ;
	}

}
