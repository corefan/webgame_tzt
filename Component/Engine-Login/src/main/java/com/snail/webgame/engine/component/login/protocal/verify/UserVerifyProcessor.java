package com.snail.webgame.engine.component.login.protocal.verify;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.component.login.cache.TempMsgrMap;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;
import com.snail.webgame.engine.component.login.protocal.service.RoleMgtService;
import com.snail.webgame.engine.component.login.util.Sequence;

/**
 * 接收到游戏客户端的登录请求，并处理
 * @author cici
 *
 */
public class UserVerifyProcessor extends ProtocolProcessor {

	 
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	private RoleMgtService roleMgtService;
	
	
	public void setRoleMgtService(RoleMgtService roleMgtService) {
		this.roleMgtService = roleMgtService;
	}


	public void execute(Request request, Response response) {
		 
		int sequenceId =Sequence.getSequenceId();
		Message message = request.getMessage();

		UserVerifyReq req = (UserVerifyReq) message.getBody();
	 
		
		if(request.getSession()!=null&&request.getSession().isConnected())
		{
			 request.getSession().setAttribute("sequenceId",sequenceId);
			 TempMsgrMap.addMessage(sequenceId,message);
		 
			
			 
			if(GameValue.GAME_VALIDATEIN_FLAG==0)
			{
				UserVerifyResp resp = new UserVerifyResp();
				 
				 message.getHeader().setProtocolId(0xA002);
				 message.setBody(resp);
				 resp.setResult(1);
				 resp.setAccount(req.getAccount());
				 //resp.setServerId(GameConfig.getInstance().getGameServerId());
				 response.write(message);
				 TempMsgrMap.removeMessage(sequenceId);
				 
				 if(logger.isInfoEnabled())
				 {
					 logger.info(Resource.getMessage("game","USER_GET_LIST_RESP")
							 +":account="+req.getAccount());
				 }
			}
			else
			{
				ChargeMgtService.sendVerfiyAccount(sequenceId,req);
			}
			 
			 
			 

		 
			 
		}
	}

}
