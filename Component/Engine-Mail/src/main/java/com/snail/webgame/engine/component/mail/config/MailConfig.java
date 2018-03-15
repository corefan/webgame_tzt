package com.snail.webgame.engine.component.mail.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
 

public class MailConfig {

	private static  MailConfig config = null;
	private static  String configPath = "/config/game-config.xml";
	private static final Logger logger=LoggerFactory.getLogger("logs");
    private String serverName;

	private MailConfig()
	{
		init();
	}
	public synchronized static MailConfig getInstance()
	{
		if(config==null)
		{
			config = new MailConfig();
		}
		return config;
	}
	
	public synchronized static MailConfig getInstance(String path)
	{
		if(config==null)
		{
			configPath = path;
			config = new MailConfig();
			
		}
		return config;
	}
	private void init()
	{
		InputStream is = MailConfig.class.getResourceAsStream(configPath);
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null) {
			rootEle = doc.getRootElement();
 
            serverName = rootEle.elementTextTrim("server-name");
            String log4jPaht  = rootEle.elementTextTrim("log4j-path");
			if(log4jPaht!=null&&log4jPaht.length()>0&&log4jPaht.endsWith("log4j.properties"))
			{
				InputStream logIs = MailConfig.class.getResourceAsStream(log4jPaht);
				Properties properties = new Properties();
				try {
					properties.load(logIs);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				PropertyConfigurator.configure(properties);
			}

		}
	}
	 

 
	public String getServerName()
	{
		return serverName;
	}
	

}
