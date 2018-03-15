package com.snail.webgame.engine.component.mail.main;

import org.epilot.ccf.server.acceptor.Server;
import org.logicalcobwebs.proxool.ProxoolFacade;

import com.snail.webgame.engine.component.mail.config.LoadWordList;
import com.snail.webgame.engine.component.mail.config.MailConfig;
import com.snail.webgame.engine.component.mail.init.MailInit;
import com.snail.webgame.engine.component.mail.thread.ShutDownThread;
import com.snail.webgame.engine.db.thread.LazyDataThread;

public class MailASMain {

	public static void main(String[] args) {
		
		MailConfig.getInstance();
		
		ProxoolFacade.disableShutdownHook();
		
		new LoadWordList().load();
		if(MailInit.init()){
			Server server=new Server();
			server.start();
			
			LazyDataThread lazyDataThread = new LazyDataThread();
			lazyDataThread.setName("定时存储");
			lazyDataThread.start();
			
			Runtime.getRuntime().addShutdownHook(new ShutDownThread(server, lazyDataThread));
		}
	}
}
