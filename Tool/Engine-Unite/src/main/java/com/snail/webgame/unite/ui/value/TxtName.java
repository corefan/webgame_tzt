package com.snail.webgame.unite.ui.value;

/**
 * 枚举
 * @author panxj
 * @version 1.0 2010-8-18
 */


public enum TxtName {
	
	fromUrl,fromPort,fromDBName,fromUser,fromPassword,fromLogUrl,fromLogPort,fromLogDBName,
	fromLogUser,fromLogPassword,gotoUrl,gotoPort,gotoDBName,gotoUser,gotoPassword,gotoLogUrl,
	gotoLogPort,gotoLogDBName,gotoLogUser,gotoLogPassword,moveType,uniteSign;

	public static boolean getName(String compName)
	{	
		TxtName[] txtName = TxtName.values();
		for(int i = 0; i<txtName.length ; i++)
		{
			if(compName.equals(txtName[i].name()))
			{
				return true;
			}
		}
		return false;
	}
}
