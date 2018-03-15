package com.snail.webgame.unite.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
import com.snail.webgame.unite.common.value.DBPath;

/**
 * 错误码加载
 * @author panxj
 * @version 1.0 2010-7-27
 */

public class MSGConfig {	
	private static MSGConfig me= null;
	private static HashMap<String,String> codeConfig =new HashMap<String,String>();
	private static final Logger log =LoggerFactory.getLogger("logs");
	/*
	 * 私有的构造
	 */
	private MSGConfig()
	{
		init();
	}
	
	/**
	 * 得到错误码的单列
	 * @return
	 */
	public static MSGConfig getInstance()
	{
		if(me==null)
		{
			me = new MSGConfig();
		}
		return me;
	}
	
	/**
	 * 初始化错误码配置
	 */
	private void init()
	{
		InputStream is = MSGConfig.class.getClass().getResourceAsStream(DBPath.ERR_CONFIG_PATH);
		if(is == null)
		{
			log.error("msg-config.xml can not be finded,system will exit!");
			System.exit(0);
		}
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null) 
		{
			rootEle = doc.getRootElement();
			List<?> codeProperty = rootEle.elements("property");
			if (codeProperty != null&& codeProperty.size()>0) 
			{
				for(int i=0;i<codeProperty.size();i++)
				{
					Element tempElement = (Element) codeProperty.get(i);
					codeConfig.put(tempElement.elementTextTrim("code"), tempElement.elementTextTrim("explan"));
				}
			}
		}
	}
	
	/**
	 * 得到错误码
	 * @param codeId
	 * @return
	 */
	public String getErrorCode(String codeId)
	{
		return codeConfig.get(codeId);
	}
}
