package com.snail.webgame.game.gamerole.protocal.login;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.processor.ProtocolProcessor;
import org.epilot.ccf.core.processor.Request;
import org.epilot.ccf.core.processor.Response;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.game.cache.UserSeqMap;
import com.snail.webgame.game.gamerole.service.UserLoginService;
import com.snail.webgame.game.util.SequenceId;

/**
 * 接收到游戏客户端的登录请求，并处理
 * @author cici
 *
 */
public class UserLoginProcessor extends ProtocolProcessor {

	 
	private static final Logger logger=LoggerFactory.getLogger("logs");
	public UserLoginProcessor()
	{
	 
		
	}
	public void execute(Request request, Response response) {
		 
		InetSocketAddress address = (InetSocketAddress) request.getSession().getRemoteAddress();
		String ip = address.getAddress().getHostAddress();
		int port = address.getPort();
		
		int sequenceId =SequenceId.getSequenceId();
		

		UserLoginReq req = (UserLoginReq) request.getMessage().getBody();
		
		if(request.getSession()!=null&&request.getSession().isConnected())
		{
			 request.getSession().setAttribute("sequenceId",sequenceId);
			 UserSeqMap.addSession(sequenceId, request.getSession());
		 
			
			 
			if(GameValue.GAME_VALIDATEIN_FLAG==0)
			{
				UserLoginResp resp = new UserLoginResp();
				resp.setAccount(req.getAccount());
				resp.setResult(1);
				ArrayList<ServerInfo> list = UserLoginService.getServerList();
				resp.setCount(list.size());
				resp.setServerList(list);

	
				 
				 Message message =  request.getMessage();
				 message.getHeader().setProtocolId(0xA002);
				 message.setBody(resp);
				 response.write(message);

				 
				 if(logger.isInfoEnabled())
				 {
					 logger.info(Resource.getMessage("list","USER_GET_LIST_RESP")
							 +":account="+req.getAccount()+",count="+resp.getCount());
				 }
			}
			else
			{
				UserLoginService.sendVerfiyAccount(sequenceId,req,ip,port);
			}
			 
			 
			 

		 
			 
		}
	}

}
