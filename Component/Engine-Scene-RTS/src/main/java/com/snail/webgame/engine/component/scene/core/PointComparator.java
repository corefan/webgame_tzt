package com.snail.webgame.engine.component.scene.core;

import java.util.Comparator;

public class PointComparator implements Comparator<Object>{

 
	public int compare(Object o1, Object o2) {
		CompPoint point1 = (CompPoint) o1;
		CompPoint point2 = (CompPoint) o2;
		
		if(point1.getDistance()>point2.getDistance())
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}


}
