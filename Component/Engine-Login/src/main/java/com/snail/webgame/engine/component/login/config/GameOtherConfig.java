package com.snail.webgame.engine.component.login.config;


import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.common.util.XMLUtil4DOM;
 

public class GameOtherConfig {

	private static  GameOtherConfig config = null;
	private static  String configPath = "/config/other-config.xml";
	private static final Logger logger=LoggerFactory.getLogger("logs");

	private int onlineNum = -1; 
    private int time = 0;
    private int num = 0;
	private int defenseFlag = 0 ;
	private int worldType = 0;
	private String gateServerName;
	private byte promptFlag;

    
	private GameOtherConfig()
	{
		init();
	}
	public synchronized static GameOtherConfig getInstance()
	{
		if(config==null)
		{
			config = new GameOtherConfig();
		}
		return config;
	}
	
	public synchronized static GameOtherConfig getInstance(String path)
	{
		if(config==null)
		{
			configPath = path;
			config = new GameOtherConfig();
			
		}
		return config;
	}
	private void init()
	{
		InputStream is = GameOtherConfig.class.getResourceAsStream(configPath);
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null) {
			rootEle = doc.getRootElement();
		
            String validateFlag = rootEle.elementTextTrim("validate-flag");
            if(validateFlag!=null&&validateFlag.trim().length()>0)
            {
            	GameValue.GAME_VALIDATEIN_FLAG = Integer.valueOf(validateFlag);
            }
            
            String indulgeFlag = rootEle.elementTextTrim("indulgePrompt-flag");
            if ((indulgeFlag != null) && (indulgeFlag.trim().length() > 0))
            {
                promptFlag = Byte.valueOf(indulgeFlag);
                if (promptFlag == 1)
                {
                  GameValue.GAME_INDULGE_ON = true;
                }
                else
                {
                	GameValue.GAME_INDULGE_ON = false;
                }

              
            }
            
            String indulgeTimeStr = rootEle.elementTextTrim("indulgePrompt-time");
            
            if(indulgeTimeStr!=null&&indulgeTimeStr.trim().length()>0)
            {
            	GameValue.GAME_INDULGE_TIME = Integer.valueOf(indulgeTimeStr);
            }
            
            
            String worldTypeStr = rootEle.elementTextTrim("world-type");
			
			if(worldTypeStr!=null)
			{
				worldType = Integer.valueOf(worldTypeStr);
			}
			
        	String defenseFlagStr = rootEle.elementTextTrim("defense-flag");
        	
			
			if(defenseFlagStr!=null&&defenseFlagStr.length()>0)
			{
				defenseFlag = Integer.valueOf(defenseFlagStr);
			}
        	
            String onlineNumStr = rootEle.elementTextTrim("online-num");
            if(onlineNumStr!=null&&onlineNumStr.length()>0)
            {
            	onlineNum = Integer.valueOf(onlineNumStr);
            }
                 
            List ConfigFreq = rootEle.elements("login-freq");
            
			if (ConfigFreq != null&&ConfigFreq.size()>0) {
				for(int i=0;i<ConfigFreq.size();i++)
				{
					Element tempElement = (Element) ConfigFreq.get(i);
			 
					String timeStr = tempElement.elementTextTrim("time");
					String numStr = tempElement.elementTextTrim("num");
					
					if(timeStr!=null&&numStr!=null&&timeStr.length()>0&&numStr.length()>0)
					{
						time = Integer.valueOf(timeStr);
						num = Integer.valueOf(numStr);
					}
					
				}
			}
			
			
			gateServerName = rootEle.elementTextTrim("gate-server-name");
			
       
		}
	}
	 

	public byte getPromptFlag() {
		return promptFlag;
	}
	public String getGateServerName() {
		return gateServerName;
	}
	public int getOnlineNum()
	{
		return onlineNum;
	}

	public int getLoginFreq()
	{
		return time;
	}
	public int getLoginFreqNum()
	{
		return num;
	}

	public int getDefenseFlag()
	{
		return defenseFlag;
	}
	public int getWorldType() {
		return worldType;
	}
	public void setWorldType(int worldType) {
		this.worldType = worldType;
	}
	
}
