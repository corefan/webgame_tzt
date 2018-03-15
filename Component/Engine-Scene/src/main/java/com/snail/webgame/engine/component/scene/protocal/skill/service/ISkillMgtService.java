package com.snail.webgame.engine.component.scene.protocal.skill.service;

import com.snail.webgame.engine.component.scene.protocal.skill.SkillResp;

/**
 * @author wangxf
 * @date 2012-9-10
 * 
 */
public interface ISkillMgtService {

	/**
	 * 加载角色技能
	 * @param roleId
	 * @author wangxf
	 * @return 
	 * @date 2012-9-10
	 */
	public SkillResp loadRoleSkill(int roleId);

}
