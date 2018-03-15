package com.snail.webgame.engine.component.scene.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.util.XMLUtil4DOM;
import com.snail.webgame.engine.component.scene.cache.FightMapCache;
import com.snail.webgame.engine.component.scene.common.BattlePoint;
import com.snail.webgame.engine.component.scene.common.LandMapData;
import com.snail.webgame.engine.component.scene.common.MapUnit;

public class LoadMapData {

	private static final Logger logger = LoggerFactory.getLogger("logs");
	
	
	public static boolean init()
	{
		
		String mapPath= FightConfig.getInstance().getMapPath();
		File file = new File(LoadMapData.class.getResource(mapPath).getPath());
		
		if(file.isDirectory())
		{
			File [] files = file.listFiles();
			
			for(int i=0;i<files.length;i++)
			{
				if(files[i].isFile())
				{
					if(loadData(files[i]))
					{
						if(logger.isInfoEnabled())
						{
							logger.info("Load Map "+files[i].getName()+" success!");
						}
					}
					else
					{
						logger.error("Load Map "+files[i].getName()+" fail!");
						//return false;
					}
				}
				
			}
			
		}
		else
		{
			logger.error("Load Map "+mapPath+" fail!");
			return false;
		}
		
	 
		
		return true;
		
		
	}
	
	public static boolean loadData(File file)
	{
	 
		Document doc = XMLUtil4DOM.file2Document(file);
		Element rootEle = null;
		Element mapContent = null;
		if (doc != null)
		{
			rootEle = doc.getRootElement();
			mapContent = rootEle.element("Property");
			String mapName = mapContent.attributeValue("Name");
			
			if(mapName==null||mapName.length()==0)
			{
				return false;
			}
			
			try
			{
				int width = Integer.valueOf(mapContent.attributeValue("width"));//10
				int height = Integer.valueOf(mapContent.attributeValue("height"));//9
				
				int cell_w = Integer.valueOf(mapContent.attributeValue("cell_w"));
				int cell_h = Integer.valueOf(mapContent.attributeValue("cell_h"));
				
				LandMapData landMapData1 = new LandMapData();
				
				if(mapContent != null)
				{
					
					byte[][] mapData1 = new byte[width][height];
					float[][] elevation = new float[width][height];
					
					String mapDataStr1 = mapContent.getText().trim();
					String[] mapDataArr1 = mapDataStr1.split(",");
					
					for(int i=0; i<height; i++)
					{
						for(int j=0; j<width; j++)
						{
							mapData1[j][i] = Byte.valueOf(mapDataArr1[(width*i+j)*2]);
							elevation[j][i] = Float.valueOf(mapDataArr1[(width*i+j)*2]+1);
						}
					}
					
					landMapData1.setTileWidth(cell_w);
					landMapData1.setTileHeight(cell_h);
					landMapData1.setMapData(mapData1);
					landMapData1.setElevation(elevation);
				}
				
				
				Element element2 = rootEle.element("Layers");
				if(element2 != null)
				{
					List<MapUnit> mapUnitList = new ArrayList<MapUnit>();
					Element elem1 = element2.element("Destructible");
					if(elem1 != null)
					{
						List<?> eList = elem1.elements("Item");
						if(eList != null && eList.size() > 0)
						{
							for(int i=0; i<eList.size(); i++)
							{
								Element e = (Element) eList.get(i);
								String unitId = e.attributeValue("Id");
								int unitNo = Integer.valueOf(e.attributeValue("Type"));
								int layerType = Integer.valueOf(e.attributeValue("LayerType"));
								String name = e.attributeValue("Name");
								String direct = e.attributeValue("Direct");
								
								if(layerType == 0 || layerType == 2)
								{
									continue;
								}
								
								MapUnit unitInfo = new MapUnit();
								unitInfo.setUnitId(unitId);
								unitInfo.setUnitNo(unitNo);
								unitInfo.setLayerType(layerType);
								
								String[] mapUnitArr = direct.split(",");
								for(int j=0; j< mapUnitArr.length; j++)
								{
									String mapUnit = mapUnitArr[j];
									String[] unitArr = mapUnit.split("#");
									
									if(unitArr != null && unitArr.length > 0)
									{
										int x = Integer.valueOf(unitArr[0]);
										int y = Integer.valueOf(unitArr[1]);
										int unit = Integer.valueOf(unitArr[2]);
										int elevation = Integer.valueOf(unitArr[3]);
										
										if(unitInfo.getBuildPoints() == null || unitInfo.getBuildPoints().length() == 0)
										{
											unitInfo.setBuildPoints(unitArr[0] + "," + unitArr[1] + ",");
										}
										else
										{
											unitInfo.setBuildPoints(unitInfo.getBuildPoints() + unitArr[0] + "," + unitArr[1] + ",");
										}
										
										if(unit > 0)
										{
											unitInfo.setX(x);
											unitInfo.setY(y);
											unitInfo.setElevation(elevation);
										}
									}
								}
								
								if(layerType == 1)
								{
									landMapData1.getMapData()[unitInfo.getX()][unitInfo.getY()] = (byte)1;
									landMapData1.getElevation()[unitInfo.getX()][unitInfo.getY()] = landMapData1.getElevation()[unitInfo.getX()][unitInfo.getY()] + unitInfo.getElevation();
								}
								else if(layerType == 3)
								{
									mapUnitList.add(unitInfo);
								}
							}
						}
					}
					landMapData1.setMapUnitList(mapUnitList);
					
					
					Element elem2 = element2.element("BirthPoint");
					if(elem2 != null)
					{
						List<?> eList = elem2.elements("Item");
						if(eList != null && eList.size() > 0)
						{
							for(int i=0; i<eList.size(); i++)
							{
								Element e = (Element) eList.get(i);
								
								BattlePoint battlePoint = new BattlePoint();
								battlePoint.setSide(Integer.valueOf(e.attributeValue("Side")));
								battlePoint.setType(e.attributeValue("Type"));
								battlePoint.setX(Integer.valueOf(e.attributeValue("X")));
								battlePoint.setY(Integer.valueOf(e.attributeValue("Y")));
								
								String x1 = e.attributeValue("X1");
								String y1 = e.attributeValue("Y1");
								if(x1 != null)
								{
									battlePoint.setX1(Integer.valueOf(x1.trim()));
								}
								if(y1 != null)
								{
									battlePoint.setY1(Integer.valueOf(y1.trim()));
								}
								
								
								landMapData1.getBattlePointMap().put(battlePoint.getSide()+","+battlePoint.getType(), battlePoint);
							}
						}
					}
				}
				
				FightMapCache.addMap(mapName, landMapData1);
				
				
//				Element element3 = rootEle.element("LogicData");
//				if(element3 != null)
//				{
//					int height2 = Integer.valueOf(element3.attributeValue("MapCol"));
//					int width2 = Integer.valueOf(element3.attributeValue("MapRow"));
//					
//					int tileWidth = Integer.valueOf(element3.attributeValue("TileWidth"));
//					int tileHeight = Integer.valueOf(element3.attributeValue("TileHeight"));
//					
//					
//					byte[][] mapData2 = new byte[width2][height2];
//					byte[][] elevation = new byte[width2][height2];
//					
//					String mapDataStr2 = element3.getText().trim();
//					String[] mapDataArr2 = mapDataStr2.split(",");
//					
//					
//					for(int i=0; i<width2; i++)
//					{
//						for(int j=0; j<height2; j++)
//						{
//							String dataStr = mapDataArr2[j+i*height2];
//							String[] dataArr = dataStr.split("#");
//
//							mapData2[i][j] = Byte.valueOf(dataArr[0]);
//							elevation[i][j] = Byte.valueOf(dataArr[1]);
//							
//						}
//					}
//					
//					LandMapData landMapData2 = new LandMapData();
//					landMapData2.setTileWidth(tileWidth);
//					landMapData2.setTileHeight(tileHeight);
//					landMapData2.setMapData(mapData2);
//					landMapData2.setElevation(elevation);
//					
//					FightMapCache.addMap(2, mapName, landMapData2);
//				}
				
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		 
		}
		return false;
	}
}
