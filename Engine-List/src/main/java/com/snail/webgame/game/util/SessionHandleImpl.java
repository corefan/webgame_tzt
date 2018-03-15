package com.snail.webgame.game.util;

 
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoSession;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.session.handle.SessionHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.game.cache.UserSeqMap;
import com.snail.webgame.game.gatemgt.service.GateServerService;

 

public class SessionHandleImpl implements SessionHandle{
	
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	public  void notifyClose(IoSession session) {
		
		
		 
		
		
		//通讯服务器断开，清除列表注册
		if(session.getAttribute("groupServerId")!=null)
		{
			int groupServerId = (Integer) session.getAttribute("groupServerId");
			int gateServerId = (Integer) session.getAttribute("gateServerId");
		 
			
			GateServerService.interruptServer(groupServerId,gateServerId);
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("list","GATE_SERVER_CLOSE")+
						":groupServerId="+groupServerId+",gateServerId="+gateServerId);
			}
		}
		else if(session.getAttribute("sequenceId")!=null)
		{
			UserSeqMap.removeSession((Integer)session.getAttribute("sequenceId"));
		}
	 
	}

	public void notifyCreate(IoSession session) {
		
		session.setIdleTime(IdleStatus.READER_IDLE, 30);
	}

	public void notifyException(IoSession session) {
		
		session.close();
	}

	public void notifyIdle(IoSession session) {
	
		session.close();
	}

	public void notifyOpen(IoSession session) {
		// TODO Auto-generated method stub
		
	}

}
