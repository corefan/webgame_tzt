package com.snail.webgame.unite.ui.value;

/**
 * 枚举
 * @author panxj
 * @version 1.0 2010-8-18
 */


public enum PanName {
	
	fromTab,fromLogTab,gotoTab,gotoLogTab,aboutTab;

	public static boolean getName(String compName)
	{	
		PanName[] panName = PanName.values();
		for(int i = 0; i<panName.length ; i++)
		{
			if(compName.equals(panName[i].name()))
			{
				return true;
			}			
		}
		return false;	
	}
}
