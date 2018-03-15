package com.snail.webgame.game.charge.validate;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.game.charge.service.ChargeMgtService;
import com.snail.webgame.game.common.ChargeMessageHead;



public class VerfiyAccountProcessor  extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	private ChargeMgtService chargeMgt ; 
	
	public VerfiyAccountProcessor()
	{
		chargeMgt = new ChargeMgtService();
	}
	
	
	public void execute(Request request, Response response) {
	 
		
		Message message  =request.getMessage();
		ChargeMessageHead head = (ChargeMessageHead) message.getHeader();
		
		
		VerfiyAccountResp resp = (VerfiyAccountResp) message.getBody();
		
		int sequenceId  = head.getUserID0();
		
		
		chargeMgt.VerfiyAccount(sequenceId,resp);
			
 
		 
	}

}
