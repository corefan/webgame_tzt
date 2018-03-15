package com.snail.webgame.engine.component.mail.cache;

import java.util.ArrayList;
import java.util.List;

public class GlossaryWordMap {
	
	public static List<String> list = new ArrayList<String>();
	
	public static void addWord(String word)
	{
		list.add(word);
	}
	
	public static void removeAll()
	{
		list.clear();
	}
	
	public static String getGlossaryWord(String content)
	{
		for(int i=0;i<list.size();i++)
		{
			String word  = list.get(i);
			if(content.indexOf(word)!=-1)
			{
				return word;
			}
		}
		
		return null;
		
	}

}
