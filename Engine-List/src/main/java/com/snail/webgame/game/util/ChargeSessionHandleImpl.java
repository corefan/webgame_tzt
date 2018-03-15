package com.snail.webgame.game.util;

 
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoSession;
import org.apache.mina.transport.socket.nio.SocketSessionConfig;
import org.epilot.ccf.config.Resource;
import org.epilot.ccf.core.session.handle.SessionHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

public class ChargeSessionHandleImpl implements SessionHandle{
	
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	public  void notifyClose(IoSession session) {
		
	 
			
			if(logger.isInfoEnabled())
			{
				logger.info(Resource.getMessage("list","CHARGE_SERVER_ERROR"));
			}
		 
	 
	}

	public void notifyCreate(IoSession session) {
		

	}

	public void notifyException(IoSession session) {
		session.close();
	}

	public void notifyIdle(IoSession session) {
	
 
		session.close();
	}

 
	public void notifyOpen(IoSession session) {
		((SocketSessionConfig) session.getConfig()).setKeepAlive(true);
		((SocketSessionConfig) session.getConfig()).setTcpNoDelay(false);
		((SocketSessionConfig) session.getConfig()).setSendBufferSize(204800);
		((SocketSessionConfig) session.getConfig()).setReceiveBufferSize(204800);
		 session.setWriteTimeout(5);
		 session.setIdleTime(IdleStatus.WRITER_IDLE, 30);
	}

}
