package com.snail.webgame.engine.component.scene.main;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.client.Client;
import org.epilot.ccf.core.protocol.Message;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.component.scene.cache.FightDataCache;
import com.snail.webgame.engine.component.scene.common.GameMessageHead;
import com.snail.webgame.engine.component.scene.config.FightConfig;
import com.snail.webgame.engine.component.scene.protocal.server.active.ServerActiveReq;


/**
 * 定时检出与计费应用的链路
 * @author cici
 *
 */
public class CheckGameThread extends Thread{

	private int flag = 0;//0 刚启动 1 已经启动
	
	public void run()
	{
		
		
		while(true)
		{
 
			checkConnect(ServerName.GAME_SERVER_NAME);
			
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
 
				e.printStackTrace();
			}
		}
	}
	
	public void checkConnect(String serverName)
	{
		IoSession session = Client.getInstance().getSession(serverName);
		if(session!=null&&session.isConnected())
		{
		}
		else
		{
			session = Client.getInstance().initConnect(serverName);
		}
		
		
		if(session!=null&&session.isConnected())
		{
		
			Message message = new Message();
			GameMessageHead header = new GameMessageHead();
			header.setMsgType(0xfffE);
			message.setHeader(header);
			
			ServerActiveReq req = new ServerActiveReq();
			req.setServerName(ServerName.GAME_SCENE_SERVER+"-"+FightConfig.getInstance().getFightServerId());
			req.setFlag(flag);
			req.setReserve(""+FightDataCache.getSize());
			
			
			message.setBody(req);
				//发送连接维护信息
			session.write(message);
			
			
			
			if(flag==0)
			{
				flag=1;
			}
		}
		
	}
}
