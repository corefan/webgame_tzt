package com.snail.webgame.engine.component.scene.protocal.fight.prop;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.scene.common.GameMessageHead;
import com.snail.webgame.engine.component.scene.protocal.fight.service.FightMgtService;
import com.snail.webgame.engine.component.scene.protocal.fight.service.IFightMgtService;

public class UsePropProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");

	private IFightMgtService fightMgtService;
	 
	
	public void setFightMgtService(IFightMgtService fightMgtService) {
		this.fightMgtService = fightMgtService;
	}
	
	
	public void execute(Request request, Response response) {
		 
		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		UsePropReq req = (UsePropReq) message.getBody();
		
		int roleId = head.getUserID0();
		
		UsePropResp resp = fightMgtService.useProp(roleId, req);
		
		head.setMsgType(0xB072);
		
		message.setBody(resp);
		
		response.write(message);
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("fight","FIGHT_RESP_13")+
					":roleId="+roleId+",fightId="+resp.getFightId());
		}
		
		
	}

}
