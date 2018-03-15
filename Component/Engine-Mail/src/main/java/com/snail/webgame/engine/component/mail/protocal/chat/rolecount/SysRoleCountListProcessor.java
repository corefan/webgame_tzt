package com.snail.webgame.engine.component.mail.protocal.chat.rolecount;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.mail.common.GameMessageHead;
import com.snail.webgame.engine.component.mail.protocal.chat.service.ChatMgtService;

public class SysRoleCountListProcessor  extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
 
	private ChatMgtService chatMgtService;
	
	 
	 

	public void setChatMgtService(ChatMgtService chatMgtService) {
		this.chatMgtService = chatMgtService;
	}
 
 

	@Override
	public void execute(Request request, Response response) {
		
		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		SysRoleCountListReq req = (SysRoleCountListReq)message.getBody();
		
		chatMgtService.setGlossaryRoleCount(req);
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("mail","GAME_GLOSSARY_ROLE_ACCOUNT_1")+":roleAccounts="+req.getRoleCounts());
		}
		
	}

}
