package com.snail.webgame.engine.component.login.util;


public class Sequence {

 
	
	private static int id =1 ;
	
	
 
	
	public static synchronized int getSequenceId()
	{
		return id++;
	}
	
 

}
