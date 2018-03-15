package com.snail.webgame.engine.component.mail.protocal.rolemgt.logout;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.mail.common.GameMessageHead;
import com.snail.webgame.engine.component.mail.protocal.rolemgt.service.RoleMgtService;

public class UserLogoutProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	private RoleMgtService roleMgtService;
	
	
	
	public void setRoleMgtService(RoleMgtService roleMgtService) {
		this.roleMgtService = roleMgtService;
	}

	
	
	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
 
		GameMessageHead head= (GameMessageHead) message.getHeader();
		
	 
		
		UserLogoutResp resp = (UserLogoutResp) message.getBody();
		
		int roleId = resp.getRoleId();
		
		roleMgtService.userLogout(roleId);
		
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("mail","GAME_MAIL_RESP_7")+":roleId="+roleId);
		}
	}

}
