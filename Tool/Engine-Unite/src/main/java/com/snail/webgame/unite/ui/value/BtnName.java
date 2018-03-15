package com.snail.webgame.unite.ui.value;

/**
 * 按钮名
 * @author panxj
 * @version 1.0 2010-8-18
 */


public enum BtnName {
	
	fromTestBtn,fromLogTestBtn,gotoTestBtn,gotoLogTestBtn,moveBtn,uniteBtn,closeBtn;

	public static boolean getName(String compName)
	{	
		BtnName[] btnName = BtnName.values();
		for(int i = 0; i<btnName.length ; i++)
		{
			if(compName.equals(btnName[i].name()))
			{
				return true;
			}			
		}
		return false;	
	}
}
