package com.snail.webgame.engine.component.mail.init;

import com.snail.webgame.engine.component.mail.dao.MailDAO;

/**
 * 邮件服务器初始化
 * @author panxj
 * @version 1.0 2010-6-22
 */

public class MailInit {
	
//	private static final Logger logger=LoggerFactory.getLogger("logs");	
	private static MailDAO mailDAO = new MailDAO();
	public static  boolean init()
	{		
		if(initData())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	private static boolean initData()
	{
		return mailDAO.loadMailInfo();
	}
}
