package com.snail.webgame.engine.component.mail.core;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.core.protocol.Message;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.component.mail.cache.ServerMap;
import com.snail.webgame.engine.component.mail.common.GameMessageHead;
import com.snail.webgame.engine.component.mail.common.RoleInfo;
import com.snail.webgame.engine.component.mail.protocal.chat.chat.ChatResp;

public class ChatService {
	
	


	
 
	
	public static void sendMessage(ChatResp resp,RoleInfo roleInfo)
	{
		Message message = new Message();
		GameMessageHead head = new GameMessageHead();
		head.setUserID0((int)roleInfo.getRoleId());
		head.setMsgType(0xD002);
		message.setHeader(head);
		message.setBody(resp);

		IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME+"-"+roleInfo.getGateServerId());
		if(session!=null&&session.isConnected())
		{
			session.write(message);
		}
	}
}
