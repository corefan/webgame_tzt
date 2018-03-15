package com.snail.webgame.unite.ui.apdate;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

/**
 * 窗口关闭监视器
 * @author panxj
 * @version 1.0 2010-8-18
 */

public class FormAdapter extends WindowAdapter {

	@Override
	public void windowClosing(WindowEvent windowevent)
	{		
		super.windowClosing(windowevent);
		shutdown();
	}
	
	/**
	 * 关闭选择
	 */
	public static void shutdown()
	{
		int op = JOptionPane.showConfirmDialog(null, "您是否想退出", "消息提示☞", JOptionPane.YES_NO_OPTION);		
		if(op == JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}	
	}
}
