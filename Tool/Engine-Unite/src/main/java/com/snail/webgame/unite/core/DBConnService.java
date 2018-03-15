package com.snail.webgame.unite.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.unite.common.value.DBMSGCode;
import com.snail.webgame.unite.config.DBConfig;
import com.snail.webgame.unite.config.MSGConfig;

/**
 * DB数据池
 * @author panxj
 * @version 1.0 2010-7-22
 */

public class DBConnService{
	private static final Logger logger=LoggerFactory.getLogger("logs");	
	private static HashMap<String, BasicDataSource> map = new HashMap<String, BasicDataSource>();

	private DBConnService()
	{
	}
	
	/**
	 * 初始化DB连接池
	 * @param name
	 */
	private static void init(String name)
	{
		BasicDataSource dataSource = null;
		if (dataSource != null)
		{
			try
			{
				dataSource.close();
			}
			catch (Exception e)
			{
				if(logger.isErrorEnabled())
				{
				}
			}
			dataSource = null;
		}
		try
		{
			Properties p = DBConfig.getInstance().getDBPoolConfig(name);
			dataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);
			map.put(name, dataSource);
		}
		catch (Exception e)
		{
			if(logger.isErrorEnabled())
			{
			}
		}
	}

	/**
	 * 获取DB连接池
	 * @param name
	 * @return
	 */
	private static synchronized DataSource getDataSource(String name)
	{
		if (!map.containsKey(name))
		{
			init(name);
		}
		return map.get(name);
	}

	/**
	 * 连接到数据库
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(String name) throws SQLException	
	{
		DataSource dataSource = getDataSource(name);
		if(dataSource==null)
		{
			throw new SQLException(MSGConfig.getInstance().getErrorCode(DBMSGCode.DBCONFIG_ERROR_CODE));
		}
		return dataSource.getConnection();
	}
	
	/**
	 * 移除某个链接
	 * @param name
	 */
	public static void removeConnection(String name)
	{
		map.remove(name);
	}
	
	/**
	 * 关闭DB连接池
	 * @param name
	 */
	public synchronized static void closePool(String name)
	{
		if (map.containsKey(name))
		{
			try
			{
				map.get(name).close();
			}
			catch (SQLException e)
			{
				if(logger.isErrorEnabled())
				{
				}
			}
		}
	}
}
