package com.snail.webgame.engine.component.scene.protocal.fight.in;

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

public class RoleInFightProcessor extends ProtocolProcessor {

	private static final Logger logger=LoggerFactory.getLogger("logs");

	private IFightMgtService fightMgtService;
	 
	
	public void setFightMgtService(IFightMgtService fightMgtService) {
		this.fightMgtService = fightMgtService;
	}
	
	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		RoleInFightReq req = (RoleInFightReq) message.getBody();
		int roleId = head.getUserID0();
		int gateServerId = head.getUserID1();
		RoleInFightResp resp = fightMgtService.roleGetFigthInit(req,roleId,gateServerId);
		head.setMsgType(0xB052);
		
		message.setBody(resp);
		
		response.synchWrite(message);
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("fight","FIGHT_RESP_11")+
					":result="+resp.getResult()+",fightId="+resp.getFightId());
		}
	}

}
