package com.snail.webgame.engine.component.scene.core;

import java.util.Comparator;

public class PhalanxComparator implements Comparator<Object>{

 
	public int compare(Object o1, Object o2) {
	 
		CompPhalanx compPhalanx1 = (CompPhalanx) o1;
		CompPhalanx compPhalanx2 = (CompPhalanx) o2;
		
		if(compPhalanx1.getD()>compPhalanx2.getD())
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}

}
