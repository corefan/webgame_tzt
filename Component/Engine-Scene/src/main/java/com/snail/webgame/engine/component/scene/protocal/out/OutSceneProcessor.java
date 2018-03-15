package com.snail.webgame.engine.component.scene.protocal.out;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.scene.GameMessageHead;
import com.snail.webgame.engine.component.scene.core.ISceneMgtService;

public class OutSceneProcessor extends ProtocolProcessor {

	private static final Logger logger = LoggerFactory.getLogger("logs");

	private ISceneMgtService sceneMgtService;
	
	public void setSceneMgtService(ISceneMgtService sceneMgtService) {
		this.sceneMgtService = sceneMgtService;
	}

	public void execute(Request request, Response response) {

		Message message = request.getMessage();


		OutSceneReq req = (OutSceneReq) message.getBody();
		GameMessageHead head = (GameMessageHead) message.getHeader();
		

		int roleId = head.getUserID0();
		
		boolean result = sceneMgtService.outScene(roleId, req);
		
		if (result && logger.isInfoEnabled()) {
			logger.info(Resource.getMessage("game", "GAME_SCENE_INFO_2") + ",roleId="
					+ roleId);
		}
		
	}
}
