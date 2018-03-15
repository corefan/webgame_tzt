package com.snail.webgame.engine.component.login.config;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.component.login.cache.WordListMap;
 

public class LoadWordList {

private static final Logger logger = LoggerFactory.getLogger("logs");
	
	/**
	 * 装载过滤字
	 */
	public boolean load()
	{
		String path = LoadWordList.class.getResource("/config/lawlessWordList.txt").getPath();
	 
		try 
		{
			File file = new File(path);
			if(!file.isFile())
			{
				if(logger.isErrorEnabled())
				{
					logger.error("Load [lawlessWordList.txt]  infomation failure!");
					return true;
				}
			}
			String str = null;
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader fileReader = new InputStreamReader(fis, "UTF-8");
			BufferedReader in = new BufferedReader(fileReader);
			while ((str = in.readLine()) != null)
			{
				if(str.trim().length()>0)
				{
					WordListMap.addWordList(str);
				}
			}
		    in.close();
		    fileReader.close();
		    fis.close();
		    
		    
		   if(logger.isInfoEnabled())
		   {
			   logger.info("Load [lawlessWordList.txt]  infomation successful!");
				
		   }
		        
		  }
		  catch (Exception e)
		  {
		    	if(logger.isErrorEnabled())
				{
					logger.error("Load [lawlessWordList.txt]  infomation failure!",e);
				}
		   }

		
		
		 
		return true;
	}
}
