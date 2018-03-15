package com.snail.webgame.unite.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
import com.snail.webgame.unite.common.value.DBPath;
import com.snail.webgame.unite.ui.core.MakeCompService;
import com.snail.webgame.unite.ui.info.CompCfgInfo;
import com.snail.webgame.unite.ui.info.CompUnitInfo;
import com.snail.webgame.unite.ui.value.CompType;
import com.snail.webgame.unite.ui.value.PanName;
import com.snail.webgame.unite.util.ParamUtil2Change;
import com.snail.webgame.unite.util.ParamUtil4Verify;

/**
 * 界面配置
 * @author panxj
 * @version 1.0 2010-7-22
 */

public class UIConfig {
	private static UIConfig me = null;	
	private HashMap<String, CompUnitInfo> compUnitMap = new HashMap<String, CompUnitInfo>();
	private static HashMap<String,HashMap<Integer,String>> compTabMap = new HashMap<String, HashMap<Integer,String>>();
	private static final Logger log =LoggerFactory.getLogger("logs");
	private UIConfig()
	{		
		init();		
	}
	
	public synchronized static UIConfig getInstance()
	{
		if(me==null)
		{
			me = new UIConfig();
		}
		return me;
	}
	
	/**
	 * 组件映射
	 * @return
	 */
	public HashMap<String, CompUnitInfo> getCompUnitMap()
	{
		return compUnitMap;
	}	
	
	/**
	 * tab页的组件
	 * @param compName
	 * @return
	 */
	public static HashMap<Integer, String> getCompTabMap(String compName)
	{
		return compTabMap.get(compName);
	}
	
	/**
	 * 初始化配置
	 */
	private void init()
	{
		InputStream is = UIConfig.class.getClass().getResourceAsStream(DBPath.UI_CONFIG_PATH);
		if(is == null)
		{
			log.error("ui-config.xml can not be finded,system will exit!");
			System.exit(0);
		}
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		if (doc != null)
		{
			rootEle = doc.getRootElement();
			List<?> configProperty = rootEle.elements("Property");
			if (configProperty != null&& configProperty.size()>0)
			{
				for(int i = 0; i< configProperty.size(); i++)
				{
					Element tempElement = (Element) configProperty.get(i);
					String type = tempElement.elementTextTrim("Type");
					if(CompType.getType(type))
					{
						CompCfgInfo compCfgInfo = new CompCfgInfo();
						String compName = tempElement.elementTextTrim("Name");
						compCfgInfo.setName(compName);
						compCfgInfo.setType(type);
						compCfgInfo.setText(tempElement.elementTextTrim("Text"));
						compCfgInfo.setWinX(ParamUtil2Change.getInt(tempElement.elementTextTrim("WinX")));
						compCfgInfo.setWinY(ParamUtil2Change.getInt(tempElement.elementTextTrim("WinY")));
						compCfgInfo.setWidth(ParamUtil2Change.getInt(tempElement.elementTextTrim("Width")));
						compCfgInfo.setHeight(ParamUtil2Change.getInt(tempElement.elementTextTrim("Height")));
						compCfgInfo.setVisible(ParamUtil2Change.getInt(tempElement.elementTextTrim("Visible")));					
						Object obj =  MakeCompService.makeComp(compCfgInfo);						
						if(obj != null)
						{
							CompUnitInfo compUnitInfo = new CompUnitInfo();
							compUnitInfo.setCompName(compName);
							compUnitInfo.setCompType(type);
							compUnitInfo.setCompInfo(obj);
							compUnitInfo.setReserve(compCfgInfo.getText());
							compUnitInfo.setAddSite(tempElement.elementTextTrim("AddSite"));					
							compUnitMap.put(compName, compUnitInfo);
							switch (CompType.pneT)
							{
								case pneT:
									String index = compUnitInfo.getReserve();
									if(!ParamUtil4Verify.isDecNum(index))
									{
										continue;
									}
									if(!PanName.getName(compName))
									{
										continue;
									}
									String addSite = compUnitInfo.getAddSite();
									if(!compTabMap.containsKey(addSite))
									{
										compTabMap.put(addSite, new HashMap<Integer, String>());
									}
									compTabMap.get(addSite).put(Integer.valueOf(index), compName);
									break;
								default:
									break;
							}
						}						
					}					
				}
			}
		}
	}
}
