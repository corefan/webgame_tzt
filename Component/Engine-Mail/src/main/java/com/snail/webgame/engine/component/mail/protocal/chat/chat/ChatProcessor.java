package com.snail.webgame.engine.component.mail.protocal.chat.chat;

import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;

import com.snail.webgame.engine.component.mail.common.GameMessageHead;
import com.snail.webgame.engine.component.mail.protocal.chat.service.ChatMgtService;

public class ChatProcessor  extends ProtocolProcessor {

 
	private ChatMgtService chatMgtService;
	
 
 

	public void setChatMgtService(ChatMgtService chatMgtService) {
		this.chatMgtService = chatMgtService;
	}




	@Override
	public void execute(Request request, Response response) {
		
		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		int roleId = head.getUserID0();
		int ip=head.getUserID5();
		ChatReq req = (ChatReq) message.getBody();
		
		 chatMgtService.sendChatMessage(req,roleId,ip);
		
	 
		
	}

}
