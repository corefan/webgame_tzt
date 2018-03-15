package com.snail.webgame.game.util;

public class SequenceId {

	private static int id =0 ;
	
	public static synchronized int getSequenceId()
	{
		return id++;
	}
}
