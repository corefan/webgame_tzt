package com.snail.webgame.engine.component.login;


 
import org.apache.mina.common.IoSession;
import org.epilot.ccf.core.session.handle.SessionHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 

 

 

public class SessionHandleImpl implements SessionHandle{
	
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
	public  void notifyClose(IoSession session) {
		
		
	

	}

	public void notifyCreate(IoSession session) {
		
	
	}

	public void notifyException(IoSession session) {
		
	
		session.close();
	
	}

	public void notifyIdle(IoSession session) {
	
	//	session.close();
	}

	public void notifyOpen(IoSession session) {
		// TODO Auto-generated method stub
		
	}

}
