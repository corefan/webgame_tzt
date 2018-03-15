package com.snail.webgame.engine.component.scene.protocal.skill.load;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.scene.GameMessageHead;
import com.snail.webgame.engine.component.scene.protocal.skill.SkillResp;
import com.snail.webgame.engine.component.scene.protocal.skill.service.ISkillMgtService;

/**
 * @author wangxf
 * 2012-9-6
 * 
 */
public class SkillLoadProcessor extends ProtocolProcessor{
	private static final Logger logger=LoggerFactory.getLogger("logs");
	private ISkillMgtService skillMgtService;

	public void setSkillMgtService(ISkillMgtService skillMgtService) {
		this.skillMgtService = skillMgtService;
	}

	public void execute(Request request, Response response) {

		Message message = request.getMessage();

		GameMessageHead head = (GameMessageHead) message.getHeader();
		int roleId = head.getUserID0();
		
		SkillResp resp = skillMgtService.loadRoleSkill(roleId);
		head.setMsgType(0xC122);
		message.setHeader(head);
		message.setBody(resp);
		response.write(message);
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("game", "GAME_SKILL_INFO_1")
					+ ":result = " + resp.getResult() + ",roleId = " + roleId);
		}
	}
}
