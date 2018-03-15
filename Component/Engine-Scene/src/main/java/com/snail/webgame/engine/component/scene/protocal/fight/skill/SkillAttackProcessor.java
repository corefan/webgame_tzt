package com.snail.webgame.engine.component.scene.protocal.fight.skill;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.scene.GameMessageHead;
import com.snail.webgame.engine.component.scene.core.ISceneMgtService;

public class SkillAttackProcessor extends ProtocolProcessor {
	
	private static final Logger logger=LoggerFactory.getLogger("logs");

	private ISceneMgtService sceneMgtService;
 
	public void setSceneMgtService(ISceneMgtService sceneMgtService) {
		this.sceneMgtService = sceneMgtService;
	}

	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		SkillAttackReq req = (SkillAttackReq) message.getBody();
		
		int roleId = head.getUserID0();
		
		sceneMgtService.skillAttack(roleId, req);
		
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("game","GAME_SCENE_INFO_8")+
						":roleId="+roleId+", aim=" + req.getAttackAim() + ", aimType=" + req.getAttackAimType()+
						", aimX=" + req.getAttackAimCurrX() + ", aimY=" + req.getAttackAimCurrY() + ", aimZ=" + req.getAttackAimCurrZ()
						+ ", skillType=" + req.getSkillType() + ", skillLevel=" + req.getSkillLevel());
		}
		
	}

}
