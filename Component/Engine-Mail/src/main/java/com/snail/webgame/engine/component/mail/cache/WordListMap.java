package com.snail.webgame.engine.component.mail.cache;

import java.util.ArrayList;

public class WordListMap {

	private static ArrayList<String> list = new ArrayList<String>();
 
	
	public static void addWordList(String str)
	{
		list.add(str);
	}
	 
	public static boolean isExistWord(String str)
	{
		
		for(int i=0;i<list.size();i++)
		{
			String word  = list.get(i);
			if(str.indexOf(word)!=-1)
			{
				return true;
			}
		}
		return false;
	}
	
	public static String replaceWord(String content,int type)
	{
	 
		if(type==1)
		{
			for(int i=0;i<list.size();i++)
			{
				String word  = list.get(i);
				if(content.indexOf(word)!=-1)
				{
					String str = "";
					
					for(int k=0;k<word.length();k++)
					{
						str = str +"*";
					}
					content = content.replaceAll(word, str);
				}
			}
		}
		else if(type==2)
		{
			String str[] = content.split(" ");
			
 
			for(int j=0;j<str.length;j++)
			{
				if(str[j]!=null&&str[j].trim().length()>0)
				{
					for(int i=0;i<list.size();i++)
					{
						String word  = list.get(i);
						if(str[j].equalsIgnoreCase(word))
						{
							String temp = "";
							for(int k=0;k<str[j].length();k++)
							{
								temp = temp +"*";
							}
							str[j] = temp;
						}
					}
				}
			}
			
			content = "";
			
			for(int j=0;j<str.length;j++)
			{
				if(str[j]!=null&&str[j].trim().length()>0)
				{
					content = content +str[j]+" ";
				}
			}
		}
		
		
		return content;
	}
	
}
