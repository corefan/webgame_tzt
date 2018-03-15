package com.snail.webgame.game.main;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.client.Client;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.game.cache.ChargeServerMap;
import com.snail.webgame.game.common.ChargeMessageHead;
 
 

 

/**
 * 链路保持线程
 * @author cici
 *
 */
public class ChargeThread extends Thread{


	private static final Logger logger=LoggerFactory.getLogger("logs");
 
	public void run()
	{
		 while(true)
		 {
			 //获取缓存的链接
			 IoSession session = ChargeServerMap.getSession(ServerName.GAME_CHARGE_SERVER);
			  
			 if(session!=null&&session.isConnected())
			 {
				 
					 //发送激活信息
					 
					 session.write(activeCharge());
					
					 
					 if(logger.isInfoEnabled())
					 {
						 logger.info(Resource.getMessage("list","CHARGE_SERVER_ACTIVE"));
					 }
				 
			 }
			 else
			 {
				 //连接计费服务器
				 
				 session = Client.getInstance().initConnect(ServerName.GAME_CHARGE_SERVER);
				 
				 if(session!=null&&session.isConnected())
				 {
				
					 session.write(activeCharge());
					 if(logger.isInfoEnabled())
					 {
						 logger.info(Resource.getMessage("list","CHARGE_SERVER_ACTIVE"));
					 }
					 ChargeServerMap.addSession(ServerName.GAME_CHARGE_SERVER, session);
				 }
			 }
			 
				 try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
				}
		}
	}
	 
	
	/**
	 * 游戏激活消息
	 */
	private static Message activeCharge()
	{
		Message message = new Message();
		ChargeMessageHead head = new ChargeMessageHead();
		head.setMsgType(0xFFFF);
		head.setVersion(0x301);
		message.setHeader(head);
		return message;
		
	}
}
