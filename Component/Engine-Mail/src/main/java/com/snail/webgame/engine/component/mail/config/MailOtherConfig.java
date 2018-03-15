package com.snail.webgame.engine.component.mail.config;


import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
 

public class MailOtherConfig {

	private static  MailOtherConfig config = null;
	private static  String configPath = "/config/other-config.xml";
	private static final Logger logger=LoggerFactory.getLogger("logs");
	
    private int chatNum = 30;
    private int chatInterval = 60;
    private int nipChatinterval = 0; //指导员间隔时间
    private boolean chatContentLog=false;
    private int worldType = 0;
	private MailOtherConfig()
	{
		init();
	}
	public synchronized static MailOtherConfig getInstance()
	{
		if(config==null)
		{
			config = new MailOtherConfig();
		}
		return config;
	}
	
	public synchronized static MailOtherConfig getInstance(String path)
	{
		if(config==null)
		{
			configPath = path;
			config = new MailOtherConfig();
			
		}
		return config;
	}
	private void init()
	{
		InputStream is = MailOtherConfig.class.getResourceAsStream(configPath);
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null) {
			rootEle = doc.getRootElement();
 

			String chat = rootEle.elementTextTrim("chat-num");
			
			if(chat!=null)
			{
				chatNum = Integer.valueOf(chat);
			}
			
			String worldTypeStr = rootEle.elementTextTrim("world-type");
			
			if(worldTypeStr!=null)
			{
				worldType = Integer.valueOf(worldTypeStr);
			}
			
			String chatContent=rootEle.elementTextTrim("chatContentLog");
			if(chatContent!=null)
			{
				int chatContentLogger=Integer.valueOf(chatContent);
				if(chatContentLogger==1)
				{
					chatContentLog=true;
				}
				else if (chatContentLogger==0)
				{
					chatContentLog=false;
				}
			}
			String interval = rootEle.elementTextTrim("world-chat-interval");
			if(interval!=null)
			{
				chatInterval = Integer.valueOf(interval);
			}			
			String IntervalTime = rootEle.elementTextTrim("nip-chat-interval");
			if(IntervalTime!=null)
			{
				nipChatinterval = Integer.valueOf(IntervalTime);
			}
			
		}
	}
	 


	public int getChatNum()
	{
		return chatNum;
	}
	
	public int getWorldChatInterval()
	{
		return chatInterval;
	}
	
	public int getNipChatInterval()
	{
		return nipChatinterval;
	}
	
	public boolean getChatContentLog()
	{
		return chatContentLog;
	}
	
	public int getWorldType()
	{
		return worldType;
	}
}
