package com.snail.webgame.unite.core;

import com.snail.webgame.unite.dao.DBUtilDao;
import com.snail.webgame.unite.ui.value.DBSource;

/**
 * 数据库和谐
 * @author panxj
 * @version 1.0 2010-10-18
 */

public class FixDBUtilService {

	/**
	 * 数据表自动比较添加
	 * @param DBName
	 * @param table
	 * @param field
	 * @param value
	 */
	public static void fixDB(String dbSource,String table,String field,String value)
	{
		if(DBSource.getName(dbSource))
		{
			DBUtilDao.isExistDB4Fix(dbSource,table,field,value);
		}		
	}

	/**
	 * 数据表自动添加
	 * @param dbSource
	 * @param sql
	 */
	public static void fixDB(String dbSource, String sql)
	{		
		if(DBSource.getName(dbSource))
		{
			DBUtilDao.isExistDB4Fix(dbSource,sql);
		}	
	}
}
