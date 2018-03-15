package com.snail.webgame.engine.component.mail.thread;

import org.epilot.ccf.server.acceptor.Server;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.db.thread.LazyDataThread;

public class ShutDownThread extends Thread {

	private Server server;
	private LazyDataThread lazyDataThread;
	private static final Logger logger = LoggerFactory.getLogger("logs");

	public ShutDownThread(Server server, LazyDataThread lazyDataThread) {
		this.server = server;
		this.lazyDataThread = lazyDataThread;
	}

	public void run() {
		// 关闭已经连接到监听端口的所有连接
		server.unbind(ServerName.MAIL_SERVER_NAME);
		if (logger.isWarnEnabled()) {
			logger.warn("Server unbind'" + ServerName.MAIL_SERVER_NAME + "' Listener....");
		}

		lazyDataThread.cancel();

		// 关闭数据库连接
		ProxoolFacade.shutdown();
		// 系统保护
		try {
			Thread.sleep(10000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (logger.isWarnEnabled()) {
			logger.warn("System is shut down the database connection.....");
		}

		// 邮件服务器正常关闭
		if (logger.isWarnEnabled()) {
			logger.warn("Normal closure of the server!");
		}
	}
}
