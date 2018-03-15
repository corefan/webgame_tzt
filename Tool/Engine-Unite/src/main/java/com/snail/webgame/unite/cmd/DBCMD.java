package com.snail.webgame.unite.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.snail.webgame.unite.cmd.core.CmdService;
import com.snail.webgame.unite.cmd.value.CmdText;
import com.snail.webgame.unite.ui.core.UIService;
import com.snail.webgame.unite.util.ParamUtil4Verify;

/**
 * 命令行
 * @author windflyboy
 * @version 1.0 2010-9-23
 */

public class DBCMD {
	
	private static DBCMD me = null;
	
	private DBCMD()
	{
		init();
	}
	
	public static DBCMD getInstance()
	{
		if(me == null)
		{
			new DBCMD();
		}
		return me;		
	}

	private void init() 
	{		
		System.out.println(CmdText.WELCOME_CMD_TXT);
		boolean flag = true;
		while(flag)
		{
			System.out.println(CmdText.MENU_CMD_TXT);
			String input = getString();
			if(ParamUtil4Verify.isDecNum(input))
			{
				int choose = Integer.valueOf(input);
				switch (choose)
				{
					case 1:						
						System.out.println(CmdService.getDBView());					
						break;
					case 2:						
						if(choose())
						{
							System.out.println(CmdText.CHANGE_DB_TXT);
							if(CmdService.changDB(getString()))
							{
								System.out.println(CmdText.CHANGE_SUC_TXT);
							}
							else
							{
								System.out.println(CmdText.CHANGE_FAIL_TXT);
							}
						}
						break;
					case 3:
						if(choose())
						{
							//合并
							System.out.println(CmdText.UNITE_DB_TXT);
							UIService.uniteDB(getString());
						}
						break;
					case 4:
						if(choose())
						{
							//迁移
							System.out.println(CmdText.MOVE_DB_TXT);
							UIService.moveDB(getString());
						}	
						break;
					case 5:
						if(choose())
						{
							flag = false;
						}					
						break;
					default:
						break;
				}				
			}
			else
			{
				System.out.println(CmdText.ERROR_CMD_TXT);				
			}
		}
	}
	
	/**
	 * 选择确认
	 * @return
	 */
	private static boolean choose()
	{
		System.out.println(CmdText.CONFIRM_CMD_TXT);
		String input = getString();
		if(input.equalsIgnoreCase("y"))
		{
			return true;
		}
		else if(input.equalsIgnoreCase("n"))
		{
			return false;
		}
		else
		{
			System.out.println(CmdText.ERROR_CMD_TXT);
		}
		return false;
	}
	
	/**
	 * 得到输入流
	 * @return
	 */
	private static String getString()
	{		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try 
		{
			String line = reader.readLine();
			return line;
		} catch (IOException e) {			
			e.printStackTrace();
		}			
		return null;		
	}
	
	
}
