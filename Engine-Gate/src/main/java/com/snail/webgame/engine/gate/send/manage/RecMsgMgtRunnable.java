package com.snail.webgame.engine.gate.send.manage;

import org.apache.mina.common.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.gate.cache.SequenceMap;
import com.snail.webgame.engine.gate.config.Command;
import com.snail.webgame.engine.gate.util.IdentityMap;
import com.snail.webgame.engine.gate.util.MessageServiceManage;

public class RecMsgMgtRunnable  {
	
	private static final Logger log =LoggerFactory.getLogger("logs");
	
 
 
	public RecMsgMgtRunnable()
	{
		 
		 
	}
	
	/**
	 * 转发消息给客户端
	 * @param message
	 * @param msgmgt
	 */
	public static void sendMsg(byte[] message,MessageServiceManage msgmgt)
	{
		int msgType = msgmgt.getMessageType(message);
		
		if(msgType == Command.USER_LOGOUT_RESP)//用户注销
		{
			msgmgt.reportUserLogout(message);
 
			return ; 
		}
		else if(msgType == Command.USER_VERIFY_ROLE_RESP||
				msgType == Command.USER_CHECK_ROLE_RESP||//用户登录、创建角色和检查角色是否重复的应答
				msgType == Command.USER_CREATE_ROLE_RESP||
				msgType == Command.USER_LOGIN_RESP||
				msgType == Command.USER_ROLE_RESP||
				msgType == Command.USER_LOGIN_QUEUE_RESP)
		{
			 
			int roleId = 0;
			int result = 0;
			int sceneId = 0;
			int sequenceId =msgmgt.getMsgSequence(message);
			IoSession sendSess = SequenceMap.getSession(sequenceId);
			if(msgType == Command.USER_CREATE_ROLE_RESP
					||msgType == Command.USER_LOGIN_RESP)
			{
				int[] ret = msgmgt.checkLoginResult(sequenceId, message);
				result = ret[0];
				roleId = ret[1];
				sceneId = ret[2];
			}
			else if(msgType == Command.USER_LOGIN_QUEUE_RESP)
			{
				String account = msgmgt.checkLoginAccount(sequenceId, message);
				
				if(sendSess != null && sendSess.isConnected())
				{
					sendSess.setAttribute("Account", account);
				}
			}
			
			if(sendSess!=null&&sendSess.isConnected())
			{
				sendSess.write(message);
				//MessageServiceManage.sendClientMessage(sendSess,message);
			}
			else
			{
				if(log.isWarnEnabled())
				{
					log.warn("Message can not be send to game client,client maybe is closed,"+"msgType="+msgType+
							",result="+result+",roleId="+roleId);
				}
			}
			SequenceMap.removeSession(sequenceId);
			
			//登录过于频繁，主动断开连接
			if(result==ErrorCode.LOGIN_FREQ_ERROR_1)
			{
				if(sendSess!=null&&sendSess.isConnected())
				{
					sendSess.close();
				}
			}
			
			return ; 
		}

		//获得用户ID 
	 		
		int roleId = msgmgt.getRoleId((byte[]) message);
		 
		IoSession sendSess = IdentityMap.getSession(roleId);
		
		if(sendSess!=null&&sendSess.isConnected())
		{
			sendSess.write(message);
			//MessageServiceManage.sendClientMessage(sendSess,message);
		}
		else
		{

			if(log.isWarnEnabled())
			{
				log.warn("Message can not be send to game client," +
						"client maybe is closed,roleId = "+roleId+",msgType="+msgType);
			}
		}
	}
}
