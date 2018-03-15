package com.snail.webgame.engine.component.scene.protocal.fight.control;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.scene.common.GameMessageHead;
import com.snail.webgame.engine.component.scene.protocal.fight.service.FightMgtService;
import com.snail.webgame.engine.component.scene.protocal.fight.service.IFightMgtService;

public class ControlProcessor extends ProtocolProcessor {
	
	private static final Logger logger=LoggerFactory.getLogger("logs");

	private IFightMgtService fightMgtService;
	 
	
	public void setFightMgtService(IFightMgtService fightMgtService) {
		this.fightMgtService = fightMgtService;
	}
	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		ControlReq req = (ControlReq) message.getBody();
		
		int roleId = head.getUserID0();
		
		ControlResp resp = fightMgtService.changeRoleAim(roleId, req);
		if(resp!=null)
		{
			head.setMsgType(0xB060);
			message.setBody(resp);
			response.write(message);
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("fight","FIGHT_RESP_10")+
						":result="+resp.getResult()+",roleId="+roleId+",phalanxId="+req.getId()+",skillType="+req.getSkillType()+",aimX="+req.getAimX()
						+",aimY="+req.getAimY()+",aimPhalanxId="+req.getAimPhalanxId()+",policy="+req.getPolicy());
			}
		}
		else
		{
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("fight","FIGHT_RESP_10")+
						":roleId="+roleId+",phalanxId="+req.getId()+",skillType="+req.getSkillType()+",aimX="+req.getAimX()
						+",aimY="+req.getAimY()+",aimPhalanxId="+req.getAimPhalanxId()+",policy="+req.getPolicy());
			}
		}
		
		
		
	}

}
