package com.snail.webgame.engine.component.mail.protocal.server.active;

 
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.mail.protocal.server.service.ServerActiveService;

public class ServerActiveProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");

	private ServerActiveService serverActiveService ;
	
	
	
	public void setServerActiveService(ServerActiveService serverActiveService) {
		this.serverActiveService = serverActiveService;
	}



	public void execute(Request request, Response response) {
	 
		 
		Message message = request.getMessage();
		ServerActiveReq req =  (ServerActiveReq) message.getBody();
		serverActiveService.activeConnect(req, request.getSession());
		
		
		
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("mail","GAME_MAIL_RESP_8")+":serverName="+req.getServerName());
		}
			 
		 
	}

}
