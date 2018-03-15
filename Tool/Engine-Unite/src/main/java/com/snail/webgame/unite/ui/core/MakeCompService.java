package com.snail.webgame.unite.ui.core;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.snail.webgame.unite.common.value.DBValue;
import com.snail.webgame.unite.ui.info.CompCfgInfo;
import com.snail.webgame.unite.ui.info.ImagePane;
import com.snail.webgame.unite.ui.value.CompType;

/**
 * 生成组件
 * @author panxj
 * @version 1.0 2010-7-23
 */

public class MakeCompService {
	
	/**
	 * 根据配置生成组件
	 * @param compInfo
	 * @return
	 */
	public static Object makeComp(CompCfgInfo compCfgInfo)
	{
		if(compCfgInfo != null)
		{
			CompType type= CompType.valueOf(compCfgInfo.getType());
			switch(type)
			{	
				case frm :
					return getForm(compCfgInfo);
				case tab :
					return getTPane(compCfgInfo);
				case pneT :
					return getPaneT(compCfgInfo);				
				case img :
					return getImagePanel(compCfgInfo);
				case labT :
					return getLabelT(compCfgInfo);
				case labF :
					return getLabelF(compCfgInfo);
				case labP :
					return getLabelP(compCfgInfo);
				case txt :
					return getTxtField(compCfgInfo);
				case pwd :
					return getPwdField(compCfgInfo);
				case btn :
					return getButton(compCfgInfo);
				case sle :
					return getScrollPane(compCfgInfo);	
				case cse :
					return getTextPaneC(compCfgInfo);				
				case abt :
					return getTextPaneA(compCfgInfo);					
				default :
					break;
			}
		}
		return null;
	}		
	
	/**
	 * 组件窗体
	 * @param compInfo
	 * @return
	 */
	private static JFrame getForm(CompCfgInfo compCfgInfo)
	{	
		JFrame frame = null;
		if(compCfgInfo!=null)
		{		
			frame = new JFrame(compCfgInfo.getText());			
			//布局为空
			frame.setLayout(null);			
			//设置背景颜色
			frame.getContentPane().setBackground(new Color(DBValue.FRM_BACK_COLOR));
			frame.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
			frame.setVisible(compCfgInfo.getVisible()==0);				
		}		
		return frame;
	}
	
	private static JTabbedPane getTPane(CompCfgInfo compCfgInfo)
	{
		JTabbedPane tabPanel = new JTabbedPane();
		tabPanel.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		tabPanel.setVisible(compCfgInfo.getVisible()==0);
		return tabPanel;
	}
	
	private static JPanel getPaneT(CompCfgInfo compCfgInfo)
	{
		JPanel panel = new JPanel();	
		//设置透明
		panel.setOpaque(false);
		panel.setLayout(null);
		panel.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		panel.setVisible(compCfgInfo.getVisible()==0);
		return panel;
	}	

	private static JPanel getImagePanel(CompCfgInfo compCfgInfo)
	{
		ImagePane panel = new ImagePane(compCfgInfo);
		panel.setLayout(null);		
		panel.setOpaque(false);
		panel.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(),compCfgInfo.getHeight());
		return panel;
	}
	
	private static JLabel getLabelT(CompCfgInfo compCfgInfo)
	{
		JLabel label = new JLabel(compCfgInfo.getText());
		//设置字体
		label.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,12));
		//设置颜色	
		label.setForeground(new Color(DBValue.LABT_FONT_COLOR));
		//设置对齐方式
		label.setHorizontalAlignment(DBValue.LAB_FONT_ALIGN);
		label.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		label.setVisible(compCfgInfo.getVisible()==0);
		return label;
	}
	
	private static JLabel getLabelF(CompCfgInfo compCfgInfo)
	{
		JLabel label = new JLabel(compCfgInfo.getText());
		label.setForeground(new Color(DBValue.LABF_FONT_COLOR));
		label.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		label.setVisible(compCfgInfo.getVisible()==0);
		return label;
	}
	
	private static JLabel getLabelP(CompCfgInfo compCfgInfo)
	{
		JLabel label = new JLabel(compCfgInfo.getText());
		label.setForeground(new Color(DBValue.LABP_COMFONT_COLOR));
		label.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		label.setVisible(compCfgInfo.getVisible()==0);
		return label;
	}
	
	private static JTextField getTxtField(CompCfgInfo compCfgInfo)
	{
		JTextField textField = null;
		if(compCfgInfo.getText() != null)
		{
			textField = new JTextField(compCfgInfo.getText());
		}	
		else
		{
			textField = new JTextField();
		}
		textField.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		textField.setVisible(compCfgInfo.getVisible()==0);
		return textField;
	}
	
	private static JPasswordField getPwdField(CompCfgInfo compCfgInfo)
	{
		JPasswordField pwdField = null;
		if(compCfgInfo.getText() != null)
		{
			pwdField = new JPasswordField(compCfgInfo.getText());
		}	
		else
		{
			pwdField = new JPasswordField();
		}
		pwdField.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		pwdField.setVisible(compCfgInfo.getVisible()==0);
		return pwdField;
	}
	
	private static JButton getButton(CompCfgInfo compCfgInfo)
	{
		JButton button = new JButton(compCfgInfo.getText());
		button.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		button.setVisible(compCfgInfo.getVisible()==0);
		return button;
	}
	
	private static JScrollPane getScrollPane(CompCfgInfo compCfgInfo)
	{
		JScrollPane scrollPane = new JScrollPane();		
		scrollPane.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		scrollPane.setVisible(compCfgInfo.getVisible()==0);
		return scrollPane;
	}
	
	private static JTextPane getTextPaneC(CompCfgInfo compCfgInfo)
	{
		JTextPane textPane = new JTextPane();
		textPane.setBackground(new Color(DBValue.CSE_BACK_COLOR));
		textPane.setForeground(new Color(DBValue.CSE_FONT_COLOR));			
		textPane.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		textPane.setVisible(compCfgInfo.getVisible()==0);
		return textPane;
	}
	
	private static JTextPane getTextPaneA(CompCfgInfo compCfgInfo)
	{
		JTextPane textPane = new JTextPane();
		textPane.setBackground(new Color(DBValue.ABT_BACK_COLOR));
		textPane.setForeground(new Color(DBValue.ABT_FONT_COLOR));			
		textPane.setBounds(compCfgInfo.getWinX(), compCfgInfo.getWinY(), compCfgInfo.getWidth(), compCfgInfo.getHeight());
		textPane.setVisible(compCfgInfo.getVisible()==0);
		return textPane;
	}
}
