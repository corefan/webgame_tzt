package com.snail.webgame.game.charge.service;

import java.util.ArrayList;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.game.cache.UserSeqMap;
import com.snail.webgame.game.charge.validate.VerfiyAccountResp;
import com.snail.webgame.game.common.GameMessageHead;
import com.snail.webgame.game.gamerole.protocal.login.ServerInfo;
import com.snail.webgame.game.gamerole.protocal.login.UserLoginResp;
import com.snail.webgame.game.gamerole.service.UserLoginService;

public class ChargeMgtService {

	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	/**
	 * 验证帐号是否正确
	 * @param sequenceId
	 * @param chargeResp
	 */
	public void VerfiyAccount(int sequenceId, VerfiyAccountResp chargeResp)
	{
		 UserLoginResp resp = new UserLoginResp();
		 
		IoSession session =  UserSeqMap.getSession(sequenceId);
		 resp.setAccount(chargeResp.getAccount());
		if(session!=null&&session.isConnected())
		{
			 resp.setAccount(resp.getAccount());
			 
			 if(chargeResp.getResult()==1)
			 {
				 resp.setResult(1);
				 ArrayList<ServerInfo> list = UserLoginService.getServerList();
				 resp.setCount(list.size());
				 resp.setServerList(list);
			 }
			 else
			 {
				 resp.setResult(chargeResp.getResult());
				 
			 }
		
			

			 Message message =  new Message();
			 
			 GameMessageHead head = new GameMessageHead();
			 
			 head.setProtocolId(0xA002);
			 message.setHeader(head);
			 message.setBody(resp);
			  
			 session.write(message);
			 
			 if(logger.isInfoEnabled())
			 {
				 logger.info(Resource.getMessage("list","USER_LOGIN_RESULT")
						 +":result="+resp.getResult()+",account="+resp.getAccount());
			 }
			 if(logger.isInfoEnabled())
			 {
				 logger.info(Resource.getMessage("list","USER_GET_LIST_RESP")+
						 ":result="+resp.getResult()+",count="+resp.getCount());
			 }
		} 
		else
		{
			 if(logger.isInfoEnabled())
			 {
				 logger.info(Resource.getMessage("list","USER_LOGIN_RESULT")
						 +":result="+resp.getResult()+",account="+resp.getAccount());
			 }
		}
	}
}
