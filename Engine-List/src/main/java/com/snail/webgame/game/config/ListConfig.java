package com.snail.webgame.game.config;

 
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.Element;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
import com.snail.webgame.game.common.ServerConfigList;
 

public class ListConfig {

	private static  ListConfig config = null;
	private static  String configPath = "/config/game-config.xml";
 
	private static int gameType;
 
	private int vendorId;
	private static HashMap<Integer,ServerConfigList> serverMap = new HashMap<Integer,ServerConfigList>();
	
	private static ArrayList<ServerConfigList> serverList = new ArrayList<ServerConfigList>();
	 
	
	
	private ListConfig()
	{
		init();
	}
	public synchronized static ListConfig getInstance()
	{
		if(config==null)
		{
			config = new ListConfig();
		}
		return config;
	}
	
	public synchronized static ListConfig getInstance(String path)
	{
		if(config==null)
		{
			configPath = path;
			config = new ListConfig();
			
		}
		return config;
	}
	
	public void reload()
	{
		init();
	}
	private void init()
	{
		InputStream is = ListConfig.class.getResourceAsStream(configPath);
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null) {
			
			try
			{
				rootEle = doc.getRootElement();
				
				String log4jPaht  = rootEle.elementTextTrim("log4j-path");
				if(log4jPaht!=null&&log4jPaht.length()>0&&log4jPaht.endsWith("log4j.properties"))
				{
					//PropertyConfigurator.configure(log4jPaht);
					InputStream logis = ListConfig.class.getResourceAsStream(log4jPaht);
					Properties properties = new Properties();
					try {
						properties.load(logis);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					PropertyConfigurator.configure(properties);
				}

				gameType = Integer.valueOf(rootEle.elementTextTrim("gameType"));
	         
	            vendorId =Integer.valueOf(rootEle.elementTextTrim("game-vendorId"));
	            
 
 
	            
	         	List configServerList = rootEle.elements("list");
	            
	         	if (configServerList != null&&configServerList.size()>0) {
					for(int i=0;i<configServerList.size();i++)
					{
						Element tempElement = (Element) configServerList.get(i);
						ServerConfigList vo = new ServerConfigList();
						String serverName = tempElement.elementTextTrim("name");
						int groupServerId = Integer.valueOf(tempElement.elementTextTrim("server-id"));
			
						
						
						vo.setServerName(serverName);
						vo.setGroupServerId(groupServerId);
				 
						
						serverMap.put(groupServerId, vo);
					}
					
					Set<Integer> serverIdSet = serverMap.keySet();
					Iterator <Integer> iterator = serverIdSet.iterator();
					
					while(iterator.hasNext())
					{
						int groupServerId = iterator.next();
						ServerConfigList listVO = serverMap.get(groupServerId);
						serverList.add(listVO);
					}
				}
	         	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
            
            
            
		}
	}
 
 
	public int getGameType()
	{
		return gameType;
	}
	
	
	public int getVendorId()
	{
		return vendorId;
	}
	
	 
	public List<ServerConfigList> getServerList()
	{
		
		return serverList;
	}
	

}
