package com.snail.webgame.engine.component.mail.protocal.rolemgt.login;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.mail.common.GameMessageHead;
import com.snail.webgame.engine.component.mail.protocal.rolemgt.service.RoleMgtService;
 

public class UserLoginProcessor extends ProtocolProcessor {

 
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	private RoleMgtService roleMgtService;
	
	
	
	public void setRoleMgtService(RoleMgtService roleMgtService) {
		this.roleMgtService = roleMgtService;
	}



	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		UserLoginResp resp = (UserLoginResp) message.getBody();
	 
		roleMgtService.roleLogin(resp,head.getUserID1());
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("mail","GAME_MAIL_RESP_6")+":result="+resp.getResult()+",roleId="+resp.getRoleId());
		}
		
	
		
	}

}
