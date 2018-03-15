package com.snail.webgame.engine.component.scene.config;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
import com.snail.webgame.engine.component.scene.cache.MapDoorXMlInfoMap;
import com.snail.webgame.engine.component.scene.info.MapDoorXMLInfo;

public class LoadMapDoorData {

	private static final Logger logger = LoggerFactory.getLogger("logs");

	
	public static boolean load()
	{
		InputStream is = LoadMapDoorData.class.getResourceAsStream(XMLPathConfig.MAP_DOOR_XML_PATH);
		Document doc = XMLUtil4DOM.file2Dom(is);
		Element rootEle = null;
		boolean flag = false;
		if(doc != null)
		{
			rootEle = doc.getRootElement();
			
			List<?> elem = rootEle.elements("Property");
			if(elem != null && elem.size()>0)
			{
				for(int i=0;i<elem.size();i++)
				{
					
					Element e = (Element)elem.get(i);
					int no  = Integer.valueOf(e.attributeValue("No").trim());
					String name = e.attributeValue("Name").trim();
					int sceneId  = Integer.valueOf(e.attributeValue("SceneId").trim());
					String mapId = e.attributeValue("MapId").trim();
					float minX  = Float.parseFloat(e.attributeValue("MinX").trim());
					float maxX  = Float.parseFloat(e.attributeValue("MaxX").trim());
					float minY  = Float.parseFloat(e.attributeValue("MinY").trim());
					float maxY  = Float.parseFloat(e.attributeValue("MaxY").trim());
					float minZ  = Float.parseFloat(e.attributeValue("MinZ").trim());
					float maxZ  = Float.parseFloat(e.attributeValue("MaxZ").trim());
					int toSceneId  = Integer.valueOf(e.attributeValue("ToSceneId").trim());
					String toMapId = e.attributeValue("ToMapId").trim();
					float toX  = Float.parseFloat(e.attributeValue("ToX").trim());
					float toY  = Float.parseFloat(e.attributeValue("ToY").trim());
					float toZ  = Float.parseFloat(e.attributeValue("ToZ").trim());
					
					
					MapDoorXMLInfo info = new MapDoorXMLInfo();
					info.setMapId(mapId);
					info.setSceneId(sceneId);
					info.setMinX(minX);
					info.setMaxX(maxX);
					info.setMinY(minY);
					info.setMaxY(maxY);
					info.setMinZ(minZ);
					info.setMaxZ(maxZ);
					info.setToMapId(toMapId);
					info.setToSceneId(toSceneId);
					info.setToX(toX);
					info.setToY(toY);
					info.setToZ(toZ);
					MapDoorXMlInfoMap.addMapDoor(info);
				}
			}
			if(logger.isInfoEnabled())
			{
				logger.info("Load [MapDoor.xml] infomation successful!");
			
			}
			flag = true;
		}
		else
		{
			if(logger.isErrorEnabled())
			{
				logger.error("Load [MapDoor.xml] infomation failure!");
			}
			flag = false;
		}
		return flag;
	}


}
