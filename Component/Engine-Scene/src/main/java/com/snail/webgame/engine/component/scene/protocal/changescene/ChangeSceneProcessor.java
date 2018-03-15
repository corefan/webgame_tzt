package com.snail.webgame.engine.component.scene.protocal.changescene;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.scene.GameMessageHead;
import com.snail.webgame.engine.component.scene.core.ISceneMgtService;

public class ChangeSceneProcessor extends ProtocolProcessor {
	
	private static final Logger logger=LoggerFactory.getLogger("logs");

	private ISceneMgtService sceneMgtService;
 
	public void setSceneMgtService(ISceneMgtService sceneMgtService) {
		this.sceneMgtService = sceneMgtService;
	}

	public void execute(Request request, Response response) {
	 
		Message message = request.getMessage();
		
		GameMessageHead head = (GameMessageHead) message.getHeader();
		
		ChangeSceneReq req = (ChangeSceneReq) message.getBody();
		
		head.setMsgType(0xff05);
		
		int roleId = head.getUserID0();
		
		ChangeSceneResp resp = sceneMgtService.changeScene(roleId, req);
		message.setBody(resp);
		response.write(message);
		
		if(logger.isInfoEnabled())
		{
			logger.info(Resource.getMessage("game","GAME_SCENE_INFO_9")+
					":roleId="+roleId+ ", result=" + resp.getResult() + ", sceneId=" + resp.getSceneId() + ",mapId=" + resp.getMapId());
		}
		
		
		
		
	}

}
