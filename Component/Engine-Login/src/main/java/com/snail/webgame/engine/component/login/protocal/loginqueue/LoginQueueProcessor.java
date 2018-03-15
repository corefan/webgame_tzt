package com.snail.webgame.engine.component.login.protocal.loginqueue;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.login.GameMessageHead;
import com.snail.webgame.engine.component.login.protocal.service.RoleMgtService;


public class LoginQueueProcessor extends ProtocolProcessor 
{
	private RoleMgtService roleMgtService;
	
	private static final Logger logger=LoggerFactory.getLogger("logs");
	

	public void setRoleMgtService(RoleMgtService roleMgtService) {
		this.roleMgtService = roleMgtService;
	}


	@Override
	public void execute(Request request, Response response) 
	{
		 
		Message message = request.getMessage();
		GameMessageHead head = (GameMessageHead) message.getHeader();

		LoginQueueReq req = (LoginQueueReq) message.getBody();
		
		head.setMsgType(0xA012);
		
		LoginQueueResp resp = roleMgtService.getLoginQueueResp(req.getAccount());
		
		message.setBody(resp);
		response.write(message);
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_6")+":result="+resp.getResult()+
					",index="+resp.getIndex()+",num="+resp.getNum());
		}
	}

}
