package com.snail.webgame.engine.component.scene.protocal.fight.manual;

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

public class ManualProcessor  extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");

	private IFightMgtService fightMgtService;
	 
	
	public void setFightMgtService(IFightMgtService fightMgtService) {
		this.fightMgtService = fightMgtService;
	}
	public void execute(Request request, Response response) {

		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		ManualReq req = (ManualReq) message.getBody();
		int roleId = head.getUserID0();
		fightMgtService.controlArmy(roleId, req);
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("fight","FIGHT_RESP_9")+
					":type="+req.getType()+",fightId="+req.getFightId());
		}
	
	}

}
