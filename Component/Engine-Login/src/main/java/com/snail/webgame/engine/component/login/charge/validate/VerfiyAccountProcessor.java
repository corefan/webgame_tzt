package com.snail.webgame.engine.component.login.charge.validate;

import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.login.ChargeMessageHead;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;



public class VerfiyAccountProcessor  extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	private ChargeMgtService chargeMgtService ; 
	
	
	
	public void setChargeMgtService(ChargeMgtService chargeMgtService) {
		this.chargeMgtService = chargeMgtService;
	}



	public void execute(Request request, Response response) {
	 
		
		Message message  =request.getMessage();
		ChargeMessageHead head = (ChargeMessageHead) message.getHeader();
		
		
		VerfiyAccountResp resp = (VerfiyAccountResp) message.getBody();
		
		int sequenceId  = head.getUserID0();
		
		
		chargeMgtService.VerfiyAccount(sequenceId,resp);
			
 
		 
	}

}
