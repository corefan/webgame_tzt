package com.snail.webgame.engine.component.login.protocal.logout;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.component.login.GameMessageHead;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;
import com.snail.webgame.engine.component.login.protocal.service.RoleMgtService;

public class UserLogoutProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	private RoleMgtService roleMgtService;
	private ChargeMgtService chargeMgtService ; 
	
	
	
	public void setRoleMgtService(RoleMgtService roleMgtService) {
		this.roleMgtService = roleMgtService;
	}

	
	public void setChargeMgtService(ChargeMgtService chargeMgtService) {
		this.chargeMgtService = chargeMgtService;
	}


	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
 
		GameMessageHead head= (GameMessageHead) message.getHeader();
		
		UserLogoutResp resp = (UserLogoutResp) message.getBody();
		
		int roleId = resp.getRoleId();
		
		
	
		if(GameValue.GAME_VALIDATEIN_FLAG==0)
		{
			roleMgtService.userLogout(roleId);
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("game", "GAME_ROLE_INFO_7")+":roleId="+roleId);
			}
		}
		else
		{
			roleMgtService.userLogout(roleId);
			chargeMgtService.sendUserLogout(roleId);
		}

	}

}
