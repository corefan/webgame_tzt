package com.snail.webgame.engine.component.scene.protocal.skill;

import java.util.List;

import org.epilot.ccf.core.protocol.MessageBody;
import org.epilot.ccf.core.protocol.ProtocolSequence;

/**
 * @author wangxf
 * @date 2012-9-10
 * 
 */
public class SkillResp extends MessageBody {
	private int result;
	private int skillCount; // 数量
	private List<TransSkillInfo> skillList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.epilot.ccf.core.protocol.MessageBody#setSequnce(org.epilot.ccf.core
	 * .protocol.ProtocolSequence)
	 */
	@Override
	protected void setSequnce(ProtocolSequence ps) {
		ps.add("result", 0);
		ps.add("skillCount", 0);
		ps.addObjectArray(
				"skillList",
				"com.snail.webgame.engine.component.scene.protocal.skill.TransSkillInfo",
				"skillCount");
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getSkillCount() {
		return skillCount;
	}

	public void setSkillCount(int skillCount) {
		this.skillCount = skillCount;
	}

	public List<TransSkillInfo> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<TransSkillInfo> skillList) {
		this.skillList = skillList;
	}

}
