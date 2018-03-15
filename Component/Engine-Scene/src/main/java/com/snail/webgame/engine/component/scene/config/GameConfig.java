package com.snail.webgame.engine.component.scene.config;


 
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.Element;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
 

public class GameConfig {

	private static  GameConfig config = null;
	private static  String configPath = "/config/game-config.xml";
 
 
    private String mapPath = null;
    
    /**
     * 角色刷新范围半径
     */
    private int refreshRadiiX = 10;
    /**
     * 角色刷新范围半径
     */
    private int refreshRadiiY = 10;
    /**
     * 角色刷新范围半径
     */
    private int refreshRadiiZ = 10;
    
    private String serverName;
    
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
		InputStream is = GameConfig.class.getResourceAsStream(configPath);
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null) {
			rootEle = doc.getRootElement();

			serverName = rootEle.elementTextTrim("server-name");
            mapPath =  rootEle.elementTextTrim("map-path");

            refreshRadiiX = Integer.parseInt(rootEle.elementTextTrim("refresh-radii-x"));
            refreshRadiiY = Integer.parseInt(rootEle.elementTextTrim("refresh-radii-y"));
            refreshRadiiZ = Integer.parseInt(rootEle.elementTextTrim("refresh-radii-z"));
            
            String log4jPaht  = rootEle.elementTextTrim("log4j-path");
			if(log4jPaht!=null&&log4jPaht.length()>0&&log4jPaht.endsWith("log4j.properties"))
			{
				InputStream logis = GameConfig.class.getResourceAsStream(log4jPaht);
				Properties properties = new Properties();
				try {
					properties.load(logis);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				PropertyConfigurator.configure(properties);
			}
		}
	}
	 
	
	public String getMapPath()
	{
		return mapPath;
	}
	public int getRefreshRadiiX() {
		return refreshRadiiX;
	}
	public String getServerName() {
		return serverName;
	}
	public int getRefreshRadiiY() {
		return refreshRadiiY;
	}
	public int getRefreshRadiiZ() {
		return refreshRadiiZ;
	}
	

}
