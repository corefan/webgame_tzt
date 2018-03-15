package com.snail.webgame.engine.common.info;

import com.snail.webgame.engine.common.to.BaseTO;

public class SkillInfo extends BaseTO {
	protected int index;// 技能栏位置
	protected int skillId;// 技能编号
	protected int skillLevel;// 技能等级
	protected long lastReleaseTime;// 最后一次释放时间
	protected long lastCalHurtTime;	// 最后一次计算技能伤害时间(旋风斩这样的技能每次计算伤害的时间)
	protected long roleId; // 角色id
	protected int SDMax;//射程

	public int getSDMax() {
		return SDMax;
	}

	public void setSDMax(int sDMax) {
		SDMax = sDMax;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}

	@Override
	public byte getSaveMode() {
		return ONLINE;
	}

	public long getLastReleaseTime() {
		return lastReleaseTime;
	}

	public void setLastReleaseTime(long lastReleaseTime) {
		this.lastReleaseTime = lastReleaseTime;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getLastCalHurtTime() {
		return lastCalHurtTime;
	}

	public void setLastCalHurtTime(long lastCalHurtTime) {
		this.lastCalHurtTime = lastCalHurtTime;
	}

}
