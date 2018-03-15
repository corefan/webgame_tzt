package com.snail.webgame.engine.component.login.protocal.server.time;

import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;

import com.snail.webgame.engine.component.login.GameMessageHead;
import com.snail.webgame.engine.component.login.protocal.server.service.ServerActiveService;

public class SysTimeProcessor extends ProtocolProcessor {
	
	
	private ServerActiveService service ;
	
	public SysTimeProcessor()
	{
		service = new ServerActiveService();
	}
 
	public void execute(Request request, Response response) {
	 
		 
		Message message = request.getMessage();
		GameMessageHead head = (GameMessageHead) message.getHeader();
		head.setMsgType(0xff03);
		
		
		SysTimeResp resp = service.getSysTime();
		message.setBody(resp);
		
		response.write(message);
		
	}

}
