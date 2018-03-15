package com.snail.webgame.unite.ui.value;

/**
 * 数据库标签
 * @author panxj
 * @version 1.0 2010-8-18
 */


public enum DBSource {
	
	FROM_DB,FROM_LOG_DB,GOTO_DB,GOTO_LOG_DB;

	public static boolean getName(String name)
	{	
		DBSource[] dbSource = DBSource.values();
		for(int i = 0; i<dbSource.length ; i++)
		{
			if(name.equals(dbSource[i].name()))
			{
				return true;
			}			
		}
		return false;	
	}
}
