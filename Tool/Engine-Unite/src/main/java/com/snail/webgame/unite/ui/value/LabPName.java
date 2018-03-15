package com.snail.webgame.unite.ui.value;

/**
 * 枚举
 * @author panxj
 * @version 1.0 2010-8-23
 */

public enum LabPName {
	
	fromPanLab,fromLogPanLab,gotoPanLab,gotoLogPanLab;

	public static boolean getName(String compName)
	{	
		LabPName[] labPName = LabPName.values();
		for(int i = 0; i<labPName.length ; i++)
		{
			if(compName.equals(labPName[i].name()))
			{
				return true;
			}			
		}
		return false;	
	}
}
