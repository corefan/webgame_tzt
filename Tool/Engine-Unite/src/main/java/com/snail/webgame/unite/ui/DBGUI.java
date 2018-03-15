package com.snail.webgame.unite.ui;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.snail.webgame.unite.config.UIConfig;
import com.snail.webgame.unite.ui.core.AdaptCompService;
import com.snail.webgame.unite.ui.info.CompUnitInfo;
import com.snail.webgame.unite.ui.value.BtnName;
import com.snail.webgame.unite.ui.value.CompType;
import com.snail.webgame.unite.ui.value.LabPName;
import com.snail.webgame.unite.ui.value.TxtName;

/**
 * 界面
 * @author panxj
 * @version 1.0 2010-7-22
 */

public class DBGUI {	
	private static DBGUI me = null;
	private static JFrame dbFrm;	
	private static JTextPane consle;
	private static HashMap<TxtName, JTextField> compTxtMap = new HashMap<TxtName, JTextField>();
	private static HashMap<String,String> compLocatMap = new HashMap<String,String>();
	
	private DBGUI()
	{
		init();
	}	
	
	public static DBGUI getInstance()
	{
		if(me == null)
		{
			me = new DBGUI();
		}
		return me;
	}
	
	/**
	 * 得到控制台
	 * @return
	 */
	public static JTextPane getConsle()
	{
		 return consle;
	}
	
	/**
	 * 得到窗体
	 * @return
	 */
	public static JFrame getDBFrm()
	{
		return dbFrm;		
	}
	
	/**
	 * 得到文本
	 * @param txtName
	 * @return
	 */
	public static JTextField getCompTxtInfo(TxtName txtName)
	{
		return compTxtMap.get(txtName);
	}
	
	/**
	 * 得到组件的位置
	 * @param compName
	 * @return
	 */
	public static String getLabPanName(String compName)
	{
		 return compLocatMap.get(compName);
	}	
	
	/**
	 * 窗口布局初始化(本加载方式适用于窗口和控制台是唯一的)
	 */
	private void init()
	{		
		HashMap<String, CompUnitInfo>  compUnitMap =  UIConfig.getInstance().getCompUnitMap();
		if(compUnitMap.size() >0)
		{	
			Object[] obj = compUnitMap.values().toArray();
			for(int i = 0;i<obj.length;i++)
			{
				CompUnitInfo compUnitInfo = (CompUnitInfo) obj[i];
				if(compUnitInfo == null)
				{
					continue;
				}
				String compName = compUnitInfo.getCompName();
				switch(CompType.valueOf(compUnitInfo.getCompType()))
				{	
					case frm:						
						dbFrm = (JFrame) compUnitInfo.getCompInfo();
						break;
					case tab:
						AdaptCompService.initTPane((JTabbedPane)compUnitInfo.getCompInfo(), compName);
						break;
					case cse:						
						consle = (JTextPane) compUnitInfo.getCompInfo();						
						break;
					case abt:
						AdaptCompService.initAbout((JTextPane) compUnitInfo.getCompInfo());
						break;						
					case labP:
						if(LabPName.getName(compName))
						{
							AdaptCompService.initLabT((JLabel) compUnitInfo.getCompInfo(),LabPName.valueOf(compName));
							compLocatMap.put(compName, compUnitInfo.getAddSite());							
						}						
						break;
					case btn:
						if(BtnName.getName(compName))
						{
							AdaptCompService.initBtn((JButton) compUnitInfo.getCompInfo(),BtnName.valueOf(compName));
							compLocatMap.put(compName, compUnitInfo.getAddSite());
						}						
						break;
					case txt:
					case pwd:						
						if(TxtName.getName(compName))
						{
							TxtName txtName = TxtName.valueOf(compName);
							AdaptCompService.initTxt((JTextField) compUnitInfo.getCompInfo(),txtName);					
							compTxtMap.put(txtName, (JTextField) compUnitInfo.getCompInfo());
							compLocatMap.put(compName, compUnitInfo.getAddSite());
						}						
						break;
					default:
						break;
				}				
				CompUnitInfo compAboveInfo = compUnitMap.get(compUnitInfo.getAddSite());
				if(compAboveInfo == null)
				{
					continue;
				}
				switch(CompType.valueOf(compAboveInfo.getCompType()))
				{		
					case frm: 
						JFrame frm = (JFrame)compAboveInfo.getCompInfo();
						frm.add((JComponent) compUnitInfo.getCompInfo());
						break;
					case sle:				
						JScrollPane sPane = (JScrollPane) compAboveInfo.getCompInfo();						
						sPane.setViewportView((JComponent) compUnitInfo.getCompInfo());
						break;
					//tab初始化时会自动加载
					case tab:								
						break;
					default:
						JComponent component = (JComponent) compAboveInfo.getCompInfo();
						component.add((JComponent) compUnitInfo.getCompInfo());
						break;					
				}			
			}	
			AdaptCompService.initForm(dbFrm);
			compUnitMap.clear();
		}
	}	
}