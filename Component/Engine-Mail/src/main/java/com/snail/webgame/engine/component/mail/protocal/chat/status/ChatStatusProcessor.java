package com.snail.webgame.engine.component.mail.protocal.chat.status;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.mail.common.GameMessageHead;
import com.snail.webgame.engine.component.mail.protocal.chat.service.ChatMgtService;

public class ChatStatusProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	private ChatMgtService chatMgtService;
	
	 
	 

	public void setChatMgtService(ChatMgtService chatMgtService) {
		this.chatMgtService = chatMgtService;
	}
	public void execute(Request request, Response response) {
		 
		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		ChatStatusReq req = (ChatStatusReq) message.getBody();
		
		 chatMgtService.chatStatusMessage(req);
		 
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("mail","GAME_MAIL_RESP_10")+":roleId="+req.getRoleId()+",chatStatus="+req.getChatStatus());
			}
	}

}
