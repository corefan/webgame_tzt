package com.snail.webgame.game.gatemgt.protocal.active;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.game.gatemgt.service.GateServerService;

public class ActiveProcessor extends ProtocolProcessor {
	private static final Logger logger=LoggerFactory.getLogger("logs");
 
	private GateServerService service ;
	public ActiveProcessor()
	{
		service = new GateServerService();
	}
	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
			
		ActiveReq req = (ActiveReq) message.getBody();
		service.activeGateConnect(req, request.getSession());

		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("list","GATE_SERVER_ACTIVE")
					+":serverName="+req.getServerName()+",onLineNum="+req.getNum());
		}
	}

}
