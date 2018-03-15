package com.snail.webgame.engine.component.scene.util;

 
import org.apache.mina.common.IoSession;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.session.handle.SessionHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.component.scene.cache.RoleFightMap;
import com.snail.webgame.engine.component.scene.cache.RoleInFightCache;
import com.snail.webgame.engine.component.scene.cache.ServerMap;

 

 

 

public class SessionHandleImpl implements SessionHandle{
	
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	public  void notifyClose(IoSession session) {
		
		
 
		//通讯服务器断开，清除列表注册
		if(session.getAttribute("serverName")!=null)
		{
			 
			String serverName = (String) session.getAttribute("serverName");
	 
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("fight","FIGHT_ACTIVE_1")
						+":serverName="+serverName);
			}
			if(serverName.startsWith(ServerName.GATE_SERVER_NAME))
			{
				RoleFightMap.clear();
				RoleInFightCache.clear();
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
	
 
	}

	public void notifyOpen(IoSession session) {
	 
		
	}

}
