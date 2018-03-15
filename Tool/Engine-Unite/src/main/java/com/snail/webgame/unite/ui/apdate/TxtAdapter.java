package com.snail.webgame.unite.ui.apdate;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

import com.snail.webgame.unite.common.value.DBMSGCode;
import com.snail.webgame.unite.ui.core.AdaptCompService;
import com.snail.webgame.unite.ui.value.TxtName;
import com.snail.webgame.unite.util.ParamUtil4Verify;

/**
 * 按键接听
 * @author panxj
 * @version 1.0 2010-8-19
 */

public class TxtAdapter extends FocusAdapter{
	private JTextField txt;
	private TxtName txtName ;
	private String tmp;
	
	
	public TxtAdapter(JTextField txt ,TxtName txtName)
	{		
		this.txt = txt;
		this.txtName = txtName;
		this.tmp = txt.getText();				
	}

	@Override
	public void focusLost(FocusEvent focusEvent)
	{		
		super.focusLost(focusEvent);
		if(!tmp.equals(txt.getText()))
		{		
			boolean flag = true;
			switch (txtName)
			{
				case fromUrl:	
				case gotoUrl:
				case fromLogUrl:			
				case gotoLogUrl:
					if(!ParamUtil4Verify.isAddress(txt.getText()))
					{	
						flag = false;
						txt.setText(tmp);	
						AdaptCompService.addConsole(2, AdaptCompService.getCompSpaceName(txtName.name()),DBMSGCode.URL_ERROR_CODE);
					}					
					break;
				case fromPort:
				case gotoPort:
				case fromLogPort:
				case gotoLogPort:
					if(!ParamUtil4Verify.isPort(txt.getText()))
					{	
						flag = false;
						txt.setText(tmp);	
						AdaptCompService.addConsole(2, AdaptCompService.getCompSpaceName(txtName.name()),DBMSGCode.PORT_ERROR_CODE);
					}					
					break;
				case fromDBName:
				case gotoDBName:
				case fromLogDBName:
				case gotoLogDBName:
					if(!ParamUtil4Verify.isUnNull(txt.getText()))
					{	
						flag = false;
						txt.setText(tmp);	
						AdaptCompService.addConsole(2, AdaptCompService.getCompSpaceName(txtName.name()),DBMSGCode.DBNAME_NULL_CODE);
					}					
					break;
				case fromUser:
				case gotoUser:
				case fromLogUser:
				case gotoLogUser:
					if(!ParamUtil4Verify.isUnNull(txt.getText()))
					{	
						flag = false;
						txt.setText(tmp);	
						AdaptCompService.addConsole(2, AdaptCompService.getCompSpaceName(txtName.name()),DBMSGCode.USER_NULL_CODE);
					}					
					break;
				case fromPassword:
				case gotoPassword:
				case fromLogPassword:
				case gotoLogPassword:
					if(!ParamUtil4Verify.isUnNull(txt.getText()))
					{	
						flag = false;
						txt.setText(tmp);	
						AdaptCompService.addConsole(2, AdaptCompService.getCompSpaceName(txtName.name()),DBMSGCode.PASSWORD_NULL_CODE);
					}					
					break;
				default:
					break;
			}
			if(flag)
			{
				tmp = txt.getText();
				AdaptCompService.setLabP(0, AdaptCompService.getBtnName(txtName));
				AdaptCompService.addConsole(4, AdaptCompService.getCompSpaceName(txtName.name()) ,DBMSGCode.DBCONFIG_CHANGE_CODE);
			}			
		}		
	}
}
