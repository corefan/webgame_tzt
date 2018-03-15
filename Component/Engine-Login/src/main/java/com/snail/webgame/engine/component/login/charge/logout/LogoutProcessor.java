package com.snail.webgame.engine.component.login.charge.logout;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.component.login.cache.ServerMap;

public class LogoutProcessor   extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	
	public void execute(Request request, Response response) {
	 
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("game","CHARGE_SERVER_INFO_1")+"......");
		}
		
		ServerMap.removeServer(ServerName.GAME_CHARGE_SERVER);
	}

}
