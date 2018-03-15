package com.snail.webgame.engine.component.scene.protocal.fight.out;

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

public class RoleOutFightProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");

	private IFightMgtService fightMgtService;
	 
	
	public void setFightMgtService(IFightMgtService fightMgtService) {
		this.fightMgtService = fightMgtService;
	}
	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		head.setMsgType(0xB054);
		
		RoleOutFightReq req =(RoleOutFightReq)message.getBody();
		int roleId = head.getUserID0();
		
		RoleOutFightResp resp = fightMgtService.roleOutFightControl(req,roleId);
		
		message.setBody(resp);
		
		response.synchWrite(message);
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("fight","FIGHT_RESP_12")+
					":figthId="+req.getFightId());
		}
		
	}

}
