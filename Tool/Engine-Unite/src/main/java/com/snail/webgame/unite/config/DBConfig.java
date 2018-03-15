package com.snail.webgame.unite.config;

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
 * 服务器初始化
 * @author panxj
 * @version 1.0 2010-7-22
 */

public class DBConfig {	
	private static  DBConfig me = null;
	private static HashMap<String,Properties> poolConfig =new HashMap<String,Properties>();
	private static final Logger log =LoggerFactory.getLogger("logs");
	private DBConfig()
	{
		init();
	}
	
	public synchronized static DBConfig getInstance()
	{
		if(me==null)
		{
			me = new DBConfig();
		}
		return me;
	}	

	/**
	 * 得到数据库配置
	 * @param name
	 * @return
	 */
	public Properties getDBPoolConfig(String name)
	{
		return poolConfig.get(name);
	}
	
	/**
	 * 初始化数据库配置
	 */
	private void init()
	{
		InputStream is = DBConfig.class.getClass().getResourceAsStream(DBPath.DB_CONFIG_PATH);
		if(is == null)
		{
			log.error("db-config.xml can not be finded,system will exit!");
			System.exit(0);
		}
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null) 
		{
			rootEle = doc.getRootElement();
			String log4jPaht  = rootEle.elementTextTrim("log4j-path");
			if(log4jPaht!=null && log4jPaht.length()>0 && log4jPaht.endsWith("log4j.properties"))
			{
				PropertyConfigurator.configure(log4jPaht);
			}
			List<?> configPool = rootEle.elements("pool");
			if (configPool != null&&configPool.size()>0)
			{
				for(int i=0;i<configPool.size();i++)
				{
					Properties p = new Properties();
					Element tempElement = (Element) configPool.get(i);
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
}
