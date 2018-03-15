package com.snail.webgame.engine.component.login.charge.validatein;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.login.ChargeMessageHead;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;

public class VerifyAccountInProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	private ChargeMgtService chargeMgtService ; 
	
	
	
	public void setChargeMgtService(ChargeMgtService chargeMgtService) {
		this.chargeMgtService = chargeMgtService;
	}
	
	
	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
		
		ChargeMessageHead head = (ChargeMessageHead) message.getHeader();
		
		VerifyAccountInResp resp = (VerifyAccountInResp) message.getBody();
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("game", "CHARGE_SERVER_INFO_4")+":result="+resp.getResult()+",account="+resp.getAccount());
		}
	
		
		chargeMgtService.userLoginMgt(resp,head.getUserID0(),head.getUserID1());
	
	
	
	}

}
