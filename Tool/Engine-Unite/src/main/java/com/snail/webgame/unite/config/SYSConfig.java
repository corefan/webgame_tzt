package com.snail.webgame.unite.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
import com.snail.webgame.unite.common.value.DBPath;

/**
 * 系统配置
 * @author panxj
 * @version 1.0 2010-7-22
 */

public class SYSConfig {
	private static SYSConfig me= null;
	private static HashMap<String, String> dbURLTemplete = new HashMap<String, String>();
	private static final Logger log =LoggerFactory.getLogger("logs");
	private SYSConfig()
	{
		init();
	}
	
	public static SYSConfig getInstance()
	{
		if(me==null)
		{
			me = new SYSConfig();
		}	
		return me;
	}
	
	private void init()
	{
		InputStream is = SYSConfig.class.getClass().getResourceAsStream(DBPath.SYS_CONFIG_PATH);
		if(is == null)
		{
			log.error("sys-config.xml can not be finded,system will exit!");
			System.exit(0);
		}
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null)
		{
			try {
				rootEle = doc.getRootElement();
				String log4jPaht  = rootEle.elementTextTrim("log4j-path");
				if(log4jPaht!=null && log4jPaht.length()>0 && log4jPaht.endsWith("log4j.properties"))
				{
					Properties properties = new Properties();
					InputStream is1 = SYSConfig.class.getClass().getResourceAsStream(log4jPaht);
					if(is1 == null)
					{
						log.error("log4j.properties can not be finded,system will exit!");
						System.exit(0);
					}
					properties.load(is1);
					PropertyConfigurator.configure(properties);
				}
				
				Element dbURLEle = rootEle.element("db-url-templete");
				if(dbURLEle != null){
					List<Element> eles = dbURLEle.elements();
					if(eles != null && !eles.isEmpty()){
						for(Element ele : eles){
							dbURLTemplete.put(ele.getName(), ele.getTextTrim());
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}	
	
	public static String getDBUrlTemplete(String name){
		return dbURLTemplete.get(name);
	}
	
}
