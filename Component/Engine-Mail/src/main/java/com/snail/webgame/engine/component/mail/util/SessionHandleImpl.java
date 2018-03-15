package com.snail.webgame.engine.component.mail.util;

 
import org.apache.mina.common.IoSession;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.session.handle.SessionHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.mail.cache.ServerMap;

 

 

 

public class SessionHandleImpl implements SessionHandle{
	
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	public  void notifyClose(IoSession session) {
		
		
 
		//通讯服务器断开，清除列表注册
		if(session.getAttribute("serverName")!=null)
		{
			 
			String serverName = (String) session.getAttribute("serverName");
	 
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("mail","GAME_MAIL_RESP_9")+":serverName="+serverName);
			}
			ServerMap.removeServer(serverName);
		}
	 
	}

	public void notifyCreate(IoSession session) {
		
		
	}

	public void notifyException(IoSession session) {
		session.close();
	}

	public void notifyIdle(IoSession session) {
	
		//session.close();
	}

	@Override
	public void notifyOpen(IoSession session) {
		// TODO Auto-generated method stub
		
	}

}
