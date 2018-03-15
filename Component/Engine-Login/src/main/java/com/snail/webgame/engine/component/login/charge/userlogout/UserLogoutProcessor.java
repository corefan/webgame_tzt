package com.snail.webgame.engine.component.login.charge.userlogout;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.login.ChargeMessageHead;

public class UserLogoutProcessor  extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
		
		ChargeMessageHead head = (ChargeMessageHead) message.getHeader();
		
		UserLogoutResp resp = (UserLogoutResp) message.getBody();
		
		
 	 
		
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_5")+":result="+resp.getResult()+",accountId="+resp.getAccountId());
			
		}
	}

 
}
