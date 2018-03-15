package com.snail.webgame.engine.component.scene.config;


 
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.Element;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
 

public class FightConfig {

	private static  FightConfig config = null;
	private static  String configPath = "/config/game-config.xml";
 
	private static HashMap<String,Properties> poolConfig =new HashMap<String,Properties>();
 
    private int fightServerId;
     
    private String mapPath = null;
    
 
    
	private FightConfig()
	{
		init();
	}
	public synchronized static FightConfig getInstance()
	{
		if(config==null)
		{
			config = new FightConfig();
		}
		return config;
	}
	
	public synchronized static FightConfig getInstance(String path)
	{
		if(config==null)
		{
			configPath = path;
			config = new FightConfig();
			
		}
		return config;
	}
	private void init()
	{
		InputStream is = FightConfig.class.getResourceAsStream(configPath);
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null) {
			rootEle = doc.getRootElement();
			String log4jPaht  = rootEle.elementTextTrim("log4j-path");
			if(log4jPaht!=null&&log4jPaht.length()>0&&log4jPaht.endsWith("log4j.properties"))
			{
				InputStream logis = FightConfig.class.getResourceAsStream(log4jPaht);
				Properties properties = new Properties();
				try {
					properties.load(logis);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				PropertyConfigurator.configure(properties);
			}

    
            fightServerId = Integer.valueOf(rootEle.elementTextTrim("fight-server-id"));
            mapPath =  rootEle.elementTextTrim("map-path");

            
           
            
            
            
			List ConfigGPool = rootEle.elements("pool");
			
			if (ConfigGPool != null&&ConfigGPool.size()>0) {
				for(int i=0;i<ConfigGPool.size();i++)
				{
					Properties p = new Properties();
					Element tempElement = (Element) ConfigGPool.get(i);
					String name = tempElement.elementTextTrim("name");
					p.setProperty("driverClassName", tempElement.elementTextTrim("driverClassName"));
					p.setProperty("url", tempElement.elementTextTrim("url"));
					p.setProperty("username", tempElement.elementTextTrim("username"));
					p.setProperty("password", tempElement.elementTextTrim("password"));
					p.setProperty("initialSize", tempElement.elementTextTrim("initialSize"));
					p.setProperty("maxActive", tempElement.elementTextTrim("maxActive"));
					p.setProperty("maxIdle", tempElement.elementTextTrim("maxIdle"));
					p.setProperty("minIdle", tempElement.elementTextTrim("minIdle"));
					p.setProperty("maxWait", tempElement.elementTextTrim("maxWait"));
					p.setProperty("validationQuery", tempElement.elementTextTrim("validationQuery"));
					p.setProperty("testOnBorrow", tempElement.elementTextTrim("testOnBorrow"));
					p.setProperty("testOnReturn", tempElement.elementTextTrim("testOnReturn"));
					p.setProperty("testWhileIdle", tempElement.elementTextTrim("testWhileIdle"));
					p.setProperty("timeBetweenEvictionRunsMillis", tempElement.elementTextTrim("timeBetweenEvictionRunsMillis"));
					p.setProperty("numTestsPerEvictionRun", tempElement.elementTextTrim("numTestsPerEvictionRun"));
					p.setProperty("minEvictableIdleTimeMillis", tempElement.elementTextTrim("minEvictableIdleTimeMillis"));
					p.setProperty("logAbandoned", tempElement.elementTextTrim("logAbandoned"));
					p.setProperty("removeAbandoned", "false");
			        p.setProperty("removeAbandonedTimeout", "120");
			        poolConfig.put(name, p);
				}
			}
		}
	}
	 
	public Properties getDBPoolConfig(String name)
	{
		return poolConfig.get(name);
	}
 
 
	public int getFightServerId()
	{
		return fightServerId;
	}
	
	public String getMapPath()
	{
		return mapPath;
	}
	

}
