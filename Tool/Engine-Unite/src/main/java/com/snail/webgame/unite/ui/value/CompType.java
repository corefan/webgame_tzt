package com.snail.webgame.unite.ui.value;

/**
 * 组件类型
 * @author panxj
 * @version 1.0 2010-8-18
 */


public enum CompType {
	
	frm,tab,pneT,img,labT,labF,labP,txt,pwd,btn,sle,cse,abt;

	public static boolean getType(String compName)
	{	
		CompType[] compType = CompType.values();
		for(int i = 0; i<compType.length ; i++)
		{	
			if(compName.equals(compType[i].name()))
			{
				return true;
			}			
		}
		return false;	
	}	
}
