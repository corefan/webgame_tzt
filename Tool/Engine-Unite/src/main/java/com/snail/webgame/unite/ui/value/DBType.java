package com.snail.webgame.unite.ui.value;

/**
 * 数据库选项
 * @author panxj
 * @version 1.0 2010-8-18
 */


public enum DBType {
	
	url,port,dbName,user,password;

	public static boolean getName(String txtName)
	{	
		DBType[] dbType = DBType.values();
		for(int i = 0; i<dbType.length ; i++)
		{
			if(txtName.equals(dbType[i].name()))
			{
				return true;
			}			
		}
		return false;	
	}
}
