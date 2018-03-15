package com.snail.webgame.engine.component.scene.protocal.skill;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

public class TransSkillInfo extends MessageBody {
	private long id; // id
	private int index;// 技能栏位置
	private int skillType;// 技能类型
	private int skillLevel;

	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("id", 0);
		ps.add("index", 0);
		ps.add("skillType", 0);
		ps.add("skillLevel", 0);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
