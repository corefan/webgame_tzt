package com.snail.webgame.engine.component.login.charge.refresh;

import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;

import com.snail.webgame.engine.component.login.ChargeMessageHead;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;

public class RefreshAccProcessor extends ProtocolProcessor{

	private ChargeMgtService chargeMgtService ; 
	
	public void setChargeMgtService(ChargeMgtService chargeMgtService) {
		this.chargeMgtService = chargeMgtService;
	}
	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
		RefreshAccResp resp = (RefreshAccResp) message.getBody();
		ChargeMessageHead header = (ChargeMessageHead) message.getHeader();
		
		chargeMgtService.refreshAccountResp(header.getUserID0(), resp);
		
		
		
		
	}

}
