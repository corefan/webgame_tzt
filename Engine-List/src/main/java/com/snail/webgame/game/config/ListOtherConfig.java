package com.snail.webgame.game.config;

 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;

import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.common.util.CommUtil;
import com.snail.webgame.engine.common.util.XMLUtil4DOM;
 

public class ListOtherConfig {

	private static  ListOtherConfig config = null;
	private static  String configPath = "/config/other-config.xml";
 

 
	
	private byte [][] data ;
	
	
	private ListOtherConfig()
	{
		init();
	}
	public synchronized static ListOtherConfig getInstance()
	{
		if(config==null)
		{
			config = new ListOtherConfig();
		}
		return config;
	}
	
	public synchronized static ListOtherConfig getInstance(String path)
	{
		if(config==null)
		{
			configPath = path;
			config = new ListOtherConfig();
			
		}
		return config;
	}
	
	public void reload()
	{
		init();
	}
	private void init()
	{
		InputStream is = ListOtherConfig.class.getResourceAsStream(configPath);
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null) {
			
			try
			{
				rootEle = doc.getRootElement();
				
			 
	             
	            String validateFlag = rootEle.elementTextTrim("validate-flag");
	            if(validateFlag!=null&&validateFlag.trim().length()>0)
	            {
	            	GameValue.GAME_VALIDATEIN_FLAG = Integer.valueOf(validateFlag);
	            }
	            
	            String keyPath =  rootEle.elementTextTrim("client-key");
	         	
	      

	         	if(keyPath!=null)
	         	{
	         	    File myFilePath = new File(keyPath);
	         	    if(myFilePath.isDirectory())
	         	    {
	         	    	File file[] = myFilePath.listFiles();
	         	    	if(file!=null&&file.length>0)
	         	    	{
	         	    		data = new byte[file.length][];
	         	    		for(int i=0;i<file.length;i++)
	         	    		{
	         	    			if(file[i].isFile())
	         	    			{
	         	    				String name = file[i].getName();
		         	    			int k = Integer.valueOf(name);
		         	    			FileInputStream stream = new FileInputStream(file[i]);
					         		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
					         		byte[] b = new byte[1024];
					         		int n = 0;
					         		byte[] date1 = null;;
					         		while((n = stream.read(b))!=-1)
					         		{
						         		byteArray.write(b, 0, n);
					         		}
					         		date1 = byteArray.toByteArray();
					         		
					         		 
					         		byteArray.close();
					         		stream.close();
					         		
					         		if(date1!=null)
					         		{
					         			ByteArrayOutputStream byteArray1 = new ByteArrayOutputStream();
						          		byteArray1.write(new byte[]{10});
						          		byteArray1.write(CommUtil.int2bytes(date1.length));
						          		byteArray1.write(date1);
						          		
						          		
						          		data[k] = byteArray1.toByteArray();
						          		byteArray1.close();	
					         		}
	         	    			}
	         	    			
	         	    		}
	         	    		
	         	    	}
	         	    	
	         	    }
	         		
	         		
	          		
	         	}
	         	
	         	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
            
            
            
		}
	}
 
  
	
	public byte[][] getClientData()
	{
		return data;
	}
}
