package com.snail.webgame.unite.thread;

import com.snail.webgame.unite.core.DBConnService;

/**
 * TODO
 * @author panxj
 * @version 1.0 2010-7-22
 */

public class ShutdownThread extends Thread {

	@Override
	public void run()
	{		
		//关闭数据池
		DBConnService.closePool("LOG_DB");
	}
}
