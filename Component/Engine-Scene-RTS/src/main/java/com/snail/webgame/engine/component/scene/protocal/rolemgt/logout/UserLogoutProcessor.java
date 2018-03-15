package com.snail.webgame.engine.component.scene.protocal.rolemgt.logout;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.scene.common.GameMessageHead;
import com.snail.webgame.engine.component.scene.protocal.rolemgt.service.RoleMgtService;

public class UserLogoutProcessor extends ProtocolProcessor {

	private RoleMgtService service;
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	public UserLogoutProcessor()
	{
		service = new RoleMgtService();
	}
	
	
	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
 
		GameMessageHead head= (GameMessageHead) message.getHeader();
		
		UserLogoutResp resp = (UserLogoutResp) message.getBody();
		
		int roleId = resp.getRoleId();
		
		service.userLogout(roleId);
		
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("fight","FIGHT_RESP_0")+
					":roleId="+roleId);
		}
	}

}
