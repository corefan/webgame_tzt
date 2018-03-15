package com.snail.webgame.engine.component.login.protocal.check;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.login.GameMessageHead;
import com.snail.webgame.engine.component.login.protocal.service.RoleMgtService;

public class CheckNameProcessor  extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	private RoleMgtService roleMgtService;
	
	public void setRoleMgtService(RoleMgtService roleMgtService) {
		this.roleMgtService = roleMgtService;
	}

	public void execute(Request request, Response response) {
		 
		Message message = request.getMessage();
		GameMessageHead head = (GameMessageHead) message.getHeader();

		CheckNameReq req = (CheckNameReq) message.getBody();
		
		head.setMsgType(0xA004);
		
		CheckNameResp resp = roleMgtService.checkRoleName(req);
		
		message.setBody(resp);
		response.write(message);
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_1")+":result="+resp.getResult()+
					",rolename="+resp.getRoleName()+",checkResult="+resp.getCheckResult());
		}
	}

}
