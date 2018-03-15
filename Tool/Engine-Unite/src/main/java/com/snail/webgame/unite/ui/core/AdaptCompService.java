package com.snail.webgame.unite.ui.core;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.snail.webgame.unite.common.value.ComValue;
import com.snail.webgame.unite.common.value.DBMSGCode;
import com.snail.webgame.unite.common.value.DBPath;
import com.snail.webgame.unite.common.value.DBValue;
import com.snail.webgame.unite.config.MSGConfig;
import com.snail.webgame.unite.config.UIConfig;
import com.snail.webgame.unite.ui.DBGUI;
import com.snail.webgame.unite.ui.apdate.BtnAdapter;
import com.snail.webgame.unite.ui.apdate.FormAdapter;
import com.snail.webgame.unite.ui.apdate.TxtAdapter;
import com.snail.webgame.unite.ui.info.CompUnitInfo;
import com.snail.webgame.unite.ui.value.BtnName;
import com.snail.webgame.unite.ui.value.DBSource;
import com.snail.webgame.unite.ui.value.LabPName;
import com.snail.webgame.unite.ui.value.PanName;
import com.snail.webgame.unite.ui.value.TxtName;
import com.snail.webgame.unite.util.ParamUtil4Verify;

/**
 * 窗体监听服务
 * @author panxj
 * @version 1.0 2010-8-19
 */

public class AdaptCompService {	
	private static HashMap<String, JLabel> compLab = new HashMap<String, JLabel>();	
	
	/**
	 * 初始化窗体
	 * @param form
	 */
	public static void initForm(JFrame frm)
	{		
		if(frm != null)
		{
			//重画窗体
			frm.repaint();
			//居中
			frm.setLocationRelativeTo(null);
			frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frm.setResizable(false);
			frm.addWindowListener(new FormAdapter());	
			showMessage();
		}		
	}

	/**
	 * 初始化关于
	 * @param about
	 */
	public static void initAbout(JTextPane about)
	{
//		File file = new File(AdaptCompService.class.getClass().getResource(DBPath.ABOUT_TXT_PATH).getPath());
//		if(file.isFile())
//		{			
			BufferedReader br = null ;
//			FileReader fr = null ;
			try
			{
//				fr = new FileReader(file);				
				br = new BufferedReader(new InputStreamReader(AdaptCompService.class.getClass().getResourceAsStream(DBPath.ABOUT_TXT_PATH), "UTF-8"));
				String str = br.readLine();
				while(str != null)
				{
					Document document = about.getDocument();
					if(document != null)
					{
						document.insertString(document.getLength(), str+'\n', null);						
					}	
					str =  br.readLine();
				}				
			}			
			catch (FileNotFoundException e)
			{				
			}
			catch (IOException e)
			{				
			}
			catch (BadLocationException e)
			{				
			}
			finally
			{
				try
				{
					if(br!=null)
					{
						br.close();
					}
//					if(fr!=null)
//					{
//						fr.close();
//					}
				}
				catch (IOException e)
				{					
				}
			}
//		}		
	}
	
	/**
	 * 初始化按钮
	 * @param btn
	 * @param compName
	 */
	public static void initBtn(JButton btn ,BtnName btnName)
	{		
		switch (btnName)
		{
			case closeBtn:
				btn.addActionListener(new BtnAdapter(btnName));				
				break;
			case fromTestBtn:
				btn.addActionListener(new BtnAdapter(btnName));				
				break;
			case gotoTestBtn:
				btn.addActionListener(new BtnAdapter(btnName));				
				break;
			case fromLogTestBtn:
				btn.addActionListener(new BtnAdapter(btnName));				
				break;
			case gotoLogTestBtn:
				btn.addActionListener(new BtnAdapter(btnName));				
				break;
			case moveBtn:
				btn.addActionListener(new BtnAdapter(btnName));				
				break;
			case uniteBtn:
				btn.addActionListener(new BtnAdapter(btnName));				
				break;
			default:
				break;
		}		
	}	
	
	/**
	 * 初始化文本
	 * @param txt
	 * @param compName
	 */
	public static void initTxt(JTextField txt,TxtName txtName)
	{		
		txt.addFocusListener(new TxtAdapter(UIService.initTxtValue(txt,txtName),txtName));				
	}
	
	/**
	 * 初始化翻页
	 * @param tPane
	 * @param compName
	 */
	public static void initTPane(JTabbedPane tPane,String compName)
	{
		HashMap<Integer, String> compTabMap = UIConfig.getCompTabMap(compName);
		if(compTabMap != null && compTabMap.size() > 0)
		{
			for(int i = 1; i<= compTabMap.size(); i++)
			{				
				 CompUnitInfo compUnitInfo = UIConfig.getInstance().getCompUnitMap().get(compTabMap.get(i));
				 if(compUnitInfo != null)
				 {						
					 tPane.addTab(getSpaceName(compUnitInfo.getCompName()), (JComponent) compUnitInfo.getCompInfo());				
				 }
			}
			compTabMap.clear();
		}
	}

	/**
	 * 初始化特殊标签
	 * @param compInfo
	 * @param compName
	 */
	public static void initLabT(JLabel compInfo, LabPName labPName)
	{
		String compName = null;
		switch (labPName)
		{
			case fromPanLab:	
				compName = BtnName.fromTestBtn.name();
				break;					
			case fromLogPanLab:
				compName = BtnName.fromLogTestBtn.name();
				break;	
			case gotoPanLab:
				compName = BtnName.gotoTestBtn.name();
				break;
			case gotoLogPanLab:	
				compName = BtnName.gotoLogTestBtn.name();
				break;						
			default:
				break;
		}		
		compLab.put(compName, compInfo);
	}		

	/**
	 * 测试数据库
	 * @param btnName
	 */
	public static void testConn(BtnName btnName)
	{		
		boolean flag = true; 
		switch (btnName)
		{
			case fromTestBtn:				
				flag = UIService.changeDBConfig(getDBSource(btnName), getTxt(TxtName.fromUrl), getTxt(TxtName.fromPort), 
						getTxt(TxtName.fromDBName), getTxt(TxtName.fromUser), getTxt(TxtName.fromPassword));
				break;
			case gotoTestBtn:
				flag = UIService.changeDBConfig(getDBSource(btnName), getTxt(TxtName.gotoUrl), getTxt(TxtName.gotoPort),
						getTxt(TxtName.gotoDBName), getTxt(TxtName.gotoUser), getTxt(TxtName.gotoPassword));
				break;
			case fromLogTestBtn:
				flag = UIService.changeDBConfig(getDBSource(btnName), getTxt(TxtName.fromLogUrl), getTxt(TxtName.fromLogPort), 
						getTxt(TxtName.fromLogDBName), getTxt(TxtName.fromLogUser), getTxt(TxtName.fromLogPassword));
				break;
			case gotoLogTestBtn:
				flag = UIService.changeDBConfig(getDBSource(btnName), getTxt(TxtName.gotoLogUrl), getTxt(TxtName.gotoLogPort),
						getTxt(TxtName.gotoLogDBName), getTxt(TxtName.gotoLogUser), getTxt(TxtName.gotoLogPassword));
				break;
			default:
				break;
		}		
		if(flag)
		{				
			setLabP(1,btnName.name());
		}
		else
		{
			setLabP(2,btnName.name());
		}
	}
	
	/**
	 * 设置标签
	 * @param type
	 * @param compName
	 */
	public static void setLabP(int type,String compName)	
	{
		JLabel labP = compLab.get(compName);
		if(labP==null)
		{
			return;
		}
		switch(type)
		{
			case 1:
				labP.setText(ComValue.test_succ_value);
				labP.setForeground(new Color(DBValue.LABP_SUCFONT_COLOR));
				break;
			case 2:
				labP.setText(ComValue.test_wrong_value);
				labP.setForeground(new Color(DBValue.LABP_ERRFONT_COLOR));
				break;			
			default:
				labP.setText(ComValue.test_untest_value);
				labP.setForeground(new Color(DBValue.LABP_COMFONT_COLOR));
				break;
		}
	}
	
	/**
	 * 得到对应按钮的名字
	 * @param txtName
	 * @return
	 */
	public static String getBtnName(TxtName txtName)
	{		
		switch(txtName)
		{
			case fromUrl:
			case fromPort:
			case fromDBName:
			case fromUser:
			case fromPassword:				
				return BtnName.fromTestBtn.name();									
			case gotoUrl:
			case gotoPort:
			case gotoDBName:
			case gotoUser:
			case gotoPassword:			
				return BtnName.gotoTestBtn.name();													
			case fromLogUrl:
			case fromLogPort:	
			case fromLogDBName:
			case fromLogUser:
			case fromLogPassword:			
				return BtnName.fromLogTestBtn.name();				
			case gotoLogUrl:				
			case gotoLogPort:
			case gotoLogDBName:
			case gotoLogUser:
			case gotoLogPassword:			
				return BtnName.gotoLogTestBtn.name();					
			default:
				break;
		}
		return null;		
	}
	
	/**
	 * 得到对应的数据源
	 * @param txtName
	 * @return
	 */
	public static String getDBSource(BtnName btnName)
	{
		switch (btnName)
		{
			case fromTestBtn:				
				return DBSource.FROM_DB.name();
			case fromLogTestBtn:				
				return DBSource.FROM_LOG_DB.name();
			case gotoTestBtn:				
				return DBSource.GOTO_DB.name();
			case gotoLogTestBtn:				
				return DBSource.GOTO_LOG_DB.name();	
			default:
				break;
		}
		return null;	 			 
	}
	
	/**
	 * 获得组件对应的空间
	 * @param compName
	 * @return
	 */
	public static String getCompSpaceName(String compName)
	{
		return getSpaceName(DBGUI.getLabPanName(compName));
	}	
	
	/**
	 * 获得数据库对应的空间
	 * @param dbSpace
	 * @return
	 */
	public static String getDBSpaceName(String dbSource)
	{		
		if(DBSource.getName(dbSource))
		{
			switch (DBSource.valueOf(dbSource))
			{
				case FROM_DB:
					return  ComValue.from_tab_value;		
				case GOTO_DB:
					return  ComValue.goto_tab_value;			
				case FROM_LOG_DB:	
					return  ComValue.fromLog_tab_value;				
				case GOTO_LOG_DB:
					return  ComValue.gotoLog_tab_value;				
				default:
					break;
			}				
		}
		return null;		
	}
	
	/**
	 * 向控制台打印数据
	 * @param type 1-成功 2-错误 3-运行异常 4-提醒	
	 * @param obj
	 */
	public static void addConsole(int type,String space,String msg)
	{
		String title = "";		
		String msgCode = null;
		int colorRGB = 0;		
		if(msg != null)
		{
			String[] param = msg.split("#\\$");				
			if(param!=null)
			{		
				if(ParamUtil4Verify.isHexNum(param[0]))
				{
					msgCode = param[0];	
				}
				else
				{
					msgCode = DBMSGCode.UNKNOW_ERROR_CODE;
				}				
				String tmp = MSGConfig.getInstance().getErrorCode(param[0]);				
				if(tmp != null)
				{					
					msg = tmp;
				}
				else
				{
					tmp = param[0];					
				}				
				for(int i = 1 ; i<param.length; i++)
				{
					msg = tmp.replace("{@"+i+"}","$# "+param[i]+" $#");
					tmp = msg;
				}				
			}			
		}		
		if(ParamUtil4Verify.isUnNull(msg))
		{		
			if(space != null)
			{ 
				msg = "< "+ space+" > "+ msg;
			}			
			switch(type)
			{
				case 1:
					colorRGB = DBValue.CSE_SUCFONT_COLOR;
					title = "Success： "+msgCode;	
					break;
				case 2:	
					colorRGB = DBValue.CSE_ERRFONT_COLOR;
					title = "Error： "+msgCode;;
					JOptionPane.showMessageDialog(DBGUI.getDBFrm(),msg.replace("$#", ""),title,JOptionPane.OK_OPTION);
					break;
				case 3:					
					colorRGB = DBValue.CSE_EXCFONT_COLOR;
					title = "Cause： "+DBMSGCode.UNKNOW_ERROR_CODE;
					break;
				case 4:
					colorRGB = DBValue.CSE_FONT_COLOR;
					title = "Prompt： "+msgCode;
					break;
				default:
					break;
			}			
			consoleAppend(title +"："+msg+'\n',colorRGB);
		}
	}	
	
	/**
	 * 添加带格式的字符串
	 * @param msg
	 * @param attr
	 */
	private static void consoleAppend(String msg,int colorRGB)
	{	
		JTextPane console =	DBGUI.getConsle();
		if(console!= null)
		{
			Document document = console.getDocument();
			//让滚动条自动下拉
			console.setCaretPosition(document.getLength());
			if(document != null)
			{
				try
				{
					String[] msgs = msg.split("\\$#");
					if(msgs!=null)
					{
						for(int i = 0;i<msgs.length;i++)
						{
							SimpleAttributeSet attrSet = new SimpleAttributeSet();
							if(i%2 == 0)
							{
								StyleConstants.setForeground(attrSet, new Color(colorRGB));
								document.insertString(document.getLength(), msgs[i], attrSet);
							}
							else
							{
								StyleConstants.setForeground(attrSet, new Color(DBValue.CSE_LHTFONT_COLOR));
								document.insertString(document.getLength(), msgs[i], attrSet);	
							}
						}
					}
				}
				catch (BadLocationException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 初始化空间
	 * @param compName
	 * @param reserve
	 */
	private static String getSpaceName(String compName)
	{
		if(PanName.getName(compName))
		{
			PanName panName = PanName.valueOf(compName);
			switch(panName)
			{
				case fromTab:
					return ComValue.from_tab_value;					
				case fromLogTab:
					return ComValue.fromLog_tab_value;				
				case gotoTab:
					return ComValue.goto_tab_value;					
				case gotoLogTab:
					return ComValue.gotoLog_tab_value;	
				case aboutTab:
					return ComValue.about_tab_value;	
				default :
					break;
			}
		}	
		return null;	
	}

	/**
	 * 得到文本
	 * @param compName
	 * @return
	 */
	private static String getTxt(TxtName txtName)
	{
		return DBGUI.getCompTxtInfo(txtName).getText();		
	}	
	
	/**
	 * 弹出界面
	 */
	private static void showMessage()
	{
		JOptionPane.showMessageDialog(DBGUI.getDBFrm(),ComValue.prompt_msg_value,"友情提示☞",JOptionPane.OK_OPTION);		
	}
}
