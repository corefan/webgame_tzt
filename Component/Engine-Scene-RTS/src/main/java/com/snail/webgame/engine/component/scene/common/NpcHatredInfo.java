package com.snail.webgame.engine.component.scene.common;
/**
 * 怪物仇恨信息对象
 * @author leiqiang
 * @date   2012-8-27 下午1:35:18
 */
public class NpcHatredInfo {

	protected String phalanxId;
	protected long totalDamage;//总伤害
	protected int skillHatredValue;//技能仇恨
	protected int flag;
	
	public String getPhalanxId() {
		return phalanxId;
	}
	public void setPhalanxId(String phalanxId) {
		this.phalanxId = phalanxId;
	}
	public long getTotalDamage() {
		return totalDamage;
	}
	public void setTotalDamage(long totalDamage) {
		this.totalDamage = totalDamage;
	}
	public int getSkillHatredValue() {
		return skillHatredValue;
	}
	public void setSkillHatredValue(int skillHatredValue) {
		this.skillHatredValue = skillHatredValue;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
