package com.snail.webgame.engine.component.scene.config;


 
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
 

public class FightOtherConfig {

	private static  FightOtherConfig config = null;
	private static  String configPath = "/config/other-config.xml";
 
	
    
    private int worldType = 0;
    
	private FightOtherConfig()
	{
		init();
	}
	public synchronized static FightOtherConfig getInstance()
	{
		if(config==null)
		{
			config = new FightOtherConfig();
		}
		return config;
	}
	
	public synchronized static FightOtherConfig getInstance(String path)
	{
		if(config==null)
		{
			configPath = path;
			config = new FightOtherConfig();
			
		}
		return config;
	}
	private void init()
	{
		InputStream is = FightOtherConfig.class.getResourceAsStream(configPath);
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null) {
			rootEle = doc.getRootElement();
	
    
             worldType = Integer.valueOf(rootEle.elementTextTrim("world-type"));
            
		}
	}
	 
 
	
	public int getWorldType()
	{
		return worldType;
	}
}
