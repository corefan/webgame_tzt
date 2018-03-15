package com.snail.webgame.unite.cmd.core;

import java.util.Properties;

import com.snail.webgame.unite.config.DBConfig;
import com.snail.webgame.unite.config.SYSConfig;
import com.snail.webgame.unite.core.DBConnService;
import com.snail.webgame.unite.dao.DBUtilDao;
import com.snail.webgame.unite.ui.value.DBSource;
import com.snail.webgame.unite.util.ParamUtil4Verify;

/**
 * cmd服务
 * @author panxj
 * @version 1.0 2010-9-25
 */

public class CmdService {
	
	/**
	 * 得到db配置
	 * @return
	 */
	public static String getDBView()
	{
		DBSource[] dbSource = DBSource.values();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<dbSource.length ; i++)
		{
			Properties p = DBConfig.getInstance().getDBPoolConfig(dbSource[i].name());
			if(p != null)
			{
				sb.append(dbSource[i].name()+":\n");			
				String url = p.getProperty("url");			
				sb.append(url.substring(url.indexOf("//")+2, url.indexOf(":",24))+ " | ");			
				sb.append(url.substring(url.indexOf(":",24)+1,url.indexOf(";"))+ " | ");		
				sb.append(url.substring(url.indexOf("=",59)+1,url.length())+ " | ");
				sb.append(p.getProperty("username")+ " | ");
				sb.append(p.getProperty("password")+ "\n");	
			}					
		}
		return sb.toString();		
	}	
	
	public static boolean changDB(String config)
	{
		if(!ParamUtil4Verify.isUnNull(config))
		{
			return false;
		}
		String[] configs = config.split(" ");
		if(configs.length != 6 || !ParamUtil4Verify.isAddress(configs[1]) || !ParamUtil4Verify.isPort(configs[2]))
		{
			return false;
		}		
		Properties p  = DBConfig.getInstance().getDBPoolConfig(configs[0]);
		if(p != null)
		{
			String sqlType = p.getProperty("url").split(":")[1];
			String url = SYSConfig.getDBUrlTemplete(sqlType);
			if(url == "")
				return false;
			
			url = url.replace("{ip}", configs[1]);
			url = url.replace("{port}", configs[2]);
			url = url.replace("{database}", configs[3]);
			
			p.setProperty("url",url);		
			p.setProperty("username", configs[4]);		
			p.setProperty("password", configs[5]);
			DBConnService.removeConnection(configs[0]);
			if(DBUtilDao.testDao(configs[0]))
			{
				return true;		
			}
		}		
		return false;
	}
}
