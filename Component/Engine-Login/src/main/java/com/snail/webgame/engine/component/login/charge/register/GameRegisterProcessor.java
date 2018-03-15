package com.snail.webgame.engine.component.login.charge.register;

 
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.Flag;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;

public class GameRegisterProcessor  extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");

 
	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
		
		GameRegisterResp resp = (GameRegisterResp) message.getBody();
		
		if(resp.getResult()==1)
		{
			Flag.flag = 0;//注册成功了
		}
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_2")+":result="+resp.getResult());
		}
	}
}
