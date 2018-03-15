package com.snail.webgame.unite.ui.apdate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.snail.webgame.unite.common.value.DBMSGCode;
import com.snail.webgame.unite.common.value.DBValue;
import com.snail.webgame.unite.ui.DBGUI;
import com.snail.webgame.unite.ui.core.AdaptCompService;
import com.snail.webgame.unite.ui.core.UIService;
import com.snail.webgame.unite.ui.value.BtnName;
import com.snail.webgame.unite.ui.value.TxtName;

/**
 * 按键监听
 * @author panxj
 * @version 1.0 2010-8-18
 */

public class BtnAdapter implements ActionListener {
	private BtnName btnName;	
	private long time;	
	
	public BtnAdapter(BtnName btnName)
	{
		this.btnName = btnName;
	}

	public void actionPerformed(ActionEvent actionevent)
	{		
		if(time > System.currentTimeMillis())
		{
			AdaptCompService.addConsole(4, null,DBMSGCode.BTN_FAST_CODE+"#$"+DBValue.BTN_FAST_TIME);
			return;
		}
		else
		{		
			time = System.currentTimeMillis()+DBValue.BTN_FAST_TIME*1000;	
		}
		switch (btnName)
		{
			case closeBtn:
				FormAdapter.shutdown();
				break;
			case fromTestBtn:				
				AdaptCompService.testConn(btnName);
				break;
			case gotoTestBtn:
				AdaptCompService.testConn(btnName);
				break;
			case fromLogTestBtn:
				AdaptCompService.testConn(btnName);
				break;
			case gotoLogTestBtn:
				AdaptCompService.testConn(btnName);
				break;
			case moveBtn:
				String moveType = DBGUI.getCompTxtInfo(TxtName.moveType).getText();
				UIService.moveDB(moveType);
				break;
			case uniteBtn:	
				String uniteSign = DBGUI.getCompTxtInfo(TxtName.uniteSign).getText();
				UIService.uniteDB(uniteSign);
				break;
			default:
				break;
		}
	}	
}
