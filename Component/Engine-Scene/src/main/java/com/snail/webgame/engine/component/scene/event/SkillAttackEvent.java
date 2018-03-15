package com.snail.webgame.engine.component.scene.event;

public class SkillAttackEvent extends AttackEvent {

	protected int skillType;//技能类型
	protected int skillLevel;//技能等级
	protected int releaseCount;//释放次数
	public SkillAttackEvent(long targetId, byte eventActor, long attackAim,
			int attackAimType, int skillType, int skillLevel) {
		super(targetId, eventActor, attackAim, attackAimType);
		this.skillType = skillType;
		this.skillLevel = skillLevel;
		this.releaseCount = 1;
	}
	
	public SkillAttackEvent(long targetId, byte eventActor, float attackAimX, float attackAimY, float attackAimZ, int skillType, int skillLevel) {
		super(targetId, eventActor, 0, -1, attackAimX, attackAimY, attackAimZ);
		this.skillType = skillType;
		this.skillLevel = skillLevel;
		this.releaseCount = 1;
	}
	public int getSkillType() {
		return skillType;
	}
	public int getSkillLevel() {
		return skillLevel;
	}

	public int getReleaseCount() {
		return releaseCount;
	}

	public void setReleaseCount(int releaseCount) {
		this.releaseCount = releaseCount;
	}

}
