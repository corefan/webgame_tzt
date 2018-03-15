package com.snail.webgame.engine.component.login.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
 

public class GameConfig {

	private static  GameConfig config = null;
	private static  String configPath = "/config/game-config.xml";
	private static final Logger logger=LoggerFactory.getLogger("logs");
    private String serverName;
    private int gameServerId;
    private int gameType;
    private String gameName;
    private String toolIp;
    private int toolPort;
    private String gmccIp;
    private int gmccPort;
    private String monitorIP;
    private int monitorPort;
    private String monitorId;

    
	private GameConfig()
	{
		init();
	}
	public synchronized static GameConfig getInstance()
	{
		if(config==null)
		{
			config = new GameConfig();
		}
		return config;
	}
	
	public synchronized static GameConfig getInstance(String path)
	{
		if(config==null)
		{
			configPath = path;
			config = new GameConfig();
			
		}
		return config;
	}
	private void init()
	{
		Document doc = XMLUtil4DOM.file2Dom(GameConfig.class.getResourceAsStream(configPath));
		Element rootEle = null;
		if (doc != null) {
			rootEle = doc.getRootElement();
			String log4jPaht  = rootEle.elementTextTrim("log4j-path");
			if(log4jPaht!=null&&log4jPaht.length()>0&&log4jPaht.endsWith("log4j.properties"))
			{
				InputStream is = GameConfig.class.getResourceAsStream(log4jPaht);
				Properties properties = new Properties();
				try {
					properties.load(is);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				PropertyConfigurator.configure(properties);
			}

            serverName = rootEle.elementTextTrim("server-name");
            gameType = Integer.valueOf(rootEle.elementTextTrim("gameType"));
            gameServerId = Integer.valueOf(rootEle.elementTextTrim("game-server-id"));
            gameName = rootEle.elementTextTrim("game-server-name");

			
            toolIp = rootEle.elementTextTrim("tool-ip");
            toolPort = Integer.valueOf(rootEle.elementTextTrim("tool-port"));
            gmccIp = rootEle.elementTextTrim("gmcc-ip");
            String gmccPortStr = rootEle.elementTextTrim("gmcc-port");
            if(gmccPortStr!=null&&gmccPortStr.length()>0)
            {
            	gmccPort = Integer.valueOf(gmccPortStr);
            }
            
            monitorIP = rootEle.elementTextTrim("monitor-ip");
            String monitorPortStr = rootEle.elementTextTrim("monitor-port");
            if(monitorPortStr!=null&&monitorPortStr.length()>0)
            {
            	monitorPort = Integer.valueOf(monitorPortStr);
            }
            monitorId =  rootEle.elementTextTrim("monitor-id");
            
		}
	}

 
	public String getServerName()
	{
		return serverName;
	}
	public int getGameServerId()
	{
		return gameServerId;
	}
	
	public int getGameType()
	{
		return gameType;
	}
	public String getGameServerName()
	{
		return gameName;
	}
	public String getGameToolIp()
	{
		return toolIp;
	}
	public int getGameToolPort()
	{
		return toolPort;
	}
	public String getGmccIp() {
		return gmccIp;
	}
	public void setGmccIp(String gmccIp) {
		this.gmccIp = gmccIp;
	}
	public int getGmccPort() {
		return gmccPort;
	}
	public void setGmccPort(int gmccPort) {
		this.gmccPort = gmccPort;
	}
	


	public String getMonitorIP()
	{
		return monitorIP;
	}
	
	public int getMonitorPort()
	{
		return monitorPort;
	}
	public String getMonitorId()
	{
		return monitorId;
	}

}
