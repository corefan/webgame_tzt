package com.snail.webgame.engine.component.scene.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

import org.critterai.nav.TriNavMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.info.Point3D;
import com.snail.webgame.engine.component.scene.cache.SceneMapCache;
import com.snail.webgame.engine.component.scene.info.LandMapData;

public class LoadMapData {

	private static final Logger logger = LoggerFactory.getLogger("logs");

	public static int spacialDepth = 500;
	public static float planeTolerance = 0.5f;
	public static float offsetScale = 0.5f;
	
	public static boolean init()
	{
		
		String mapPath= LoadMapData.class.getResource(GameConfig.getInstance().getMapPath()).getPath();
		
		File file = new File(mapPath);
		
		if(file.isDirectory())
		{
			File [] files = file.listFiles(new FilenameFilter(){
				public boolean accept(File dir, String name) {
					return name.endsWith(".map");
				}
			});
			
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
						return false;
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


	/**
	 * 加载地图文件信息
	 * @param file
	 * @return
	 * @author wangxf
	 * @throws IOException 
	 * @date 2012-8-14
	 */
	private static boolean loadData(File file)  {
		String mapId = "";
		float[] bound = null;
		float[] vertexs = null;
		int[] indices = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			FileChannel inChannel = fis.getChannel();

			ByteBuffer lengthBuf = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);

			inChannel.read(lengthBuf);
			lengthBuf.flip();
			int length = lengthBuf.asIntBuffer().get();
			ByteBuffer buffer = ByteBuffer.allocate(length * 2).order(ByteOrder.LITTLE_ENDIAN);
			lengthBuf.clear();
			inChannel.read(buffer);
			buffer.flip();
			mapId = buffer.asCharBuffer().toString();

			inChannel.read(lengthBuf);
			lengthBuf.flip();
			length = lengthBuf.asIntBuffer().get();
			buffer = ByteBuffer.allocate(length * 4).order(ByteOrder.LITTLE_ENDIAN);
			lengthBuf.clear();
			inChannel.read(buffer);
			buffer.flip();
			FloatBuffer boundsBuffer = buffer.asFloatBuffer();
			bound = new float[length];
			int i = 0;
			while (boundsBuffer.hasRemaining()) {
				bound[i++] = boundsBuffer.get();
			}

			inChannel.read(lengthBuf);
			lengthBuf.flip();
			length = lengthBuf.asIntBuffer().get();
			buffer = ByteBuffer.allocate(length * 4).order(ByteOrder.LITTLE_ENDIAN);
			lengthBuf.clear();
			inChannel.read(buffer);
			buffer.flip();
			FloatBuffer vertexsBuffer = buffer.asFloatBuffer();
			vertexs = new float[length];
			i = 0;
			while (vertexsBuffer.hasRemaining()) {
				vertexs[i++] = vertexsBuffer.get();
			}

			inChannel.read(lengthBuf);
			lengthBuf.flip();
			length = lengthBuf.asIntBuffer().get();
			buffer = ByteBuffer.allocate(length * 4).order(ByteOrder.LITTLE_ENDIAN);
			lengthBuf.clear();
			inChannel.read(buffer);
			buffer.flip();
			IntBuffer indicesBuffer = buffer.asIntBuffer();
			indices = new int[length];
			i = 0;
			while (indicesBuffer.hasRemaining()) {
				indices[i++] = indicesBuffer.get();
			}

			inChannel.close();
			fis.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		// 生成导航网格算法处理类
		TriNavMesh mesh = TriNavMesh.build(vertexs, indices, 500, 0.5f, 0.5f);
		
		LandMapData mapData = new LandMapData();

		mapData.setMaxX((int)bound[3]);
		mapData.setMaxY((int)bound[4]);
		mapData.setMaxZ((int)bound[5]);
		mapData.setMesh(mesh);
		
		for(int i=0; i< vertexs.length; i+=3){
			mapData.addPoint(new Point3D(vertexs[i], vertexs[i+1], vertexs[i+2]));
		}
		SceneMapCache.addMap(mapId, mapData);
		
		return true;
	}
	
	/*public static boolean loadData(File file)
	{
	 
		Document doc = XMLUtil4DOM.file2Document(file);
		Element rootEle = null;
		if (doc != null) {
			rootEle = doc.getRootElement();
			
			String mapName = rootEle.attributeValue("name");
			
	 
			
			
			if(mapName==null||mapName.length()==0)
			{
				return false;
			}
		 
			
			Element element1 = rootEle.element("floor_direct");
			String mapDataStr1 = element1.getText().trim();
			try
			{
				int width1 = Integer.valueOf(element1.attributeValue("Width"));
				int height1 = Integer.valueOf(element1.attributeValue("Height"));
		
			 	
				byte mapData1[][] = new byte [width1][height1];
				
				
				LandMapData  landMapData1 = new LandMapData();
				
				
				String dataStr1[] = mapDataStr1.split(",");
				
			
 
				for(int i=0;i<width1;i++)
				{
					for(int j=0;j<height1;j++)
					{
						mapData1[i][j] = Byte.valueOf(dataStr1[j+i*height1]);
					 
					}
				}
				
				 
				landMapData1.setMapData(mapData1);
				 
				
	 
				SceneMapCache.addMap(mapName,landMapData1);
				
				
//				Element element2 = rootEle.element("Property");
//				
//				if(element2!=null)
//				{
//					String mapDataStr2 = element2.getText().trim();
//					
//					 
//					
//					int width2 = Integer.valueOf(element2.attributeValue("row"));
//					int height2 = Integer.valueOf(element2.attributeValue("col"));
//					int tileWidth = Integer.valueOf(element2.attributeValue("tileWidth"));
//					int tileHeight = Integer.valueOf(element2.attributeValue("tileHeight"));
//					
//					
//					byte mapData2[][] = new byte [width2][height2];
//					
//					
//					
//					String dataStr2[] = mapDataStr2.split(",");
//					
//			 
//	 
//					for(int i=0;i<width2;i++)
//					{
//						for(int j=0;j<height2;j++)
//						{
//							mapData2[i][j] = Byte.valueOf(dataStr2[i*height2+j]);
//						 
//						}
//					}
//					LandMapData  landMapData2 = new LandMapData();
//					landMapData2.setMapData(mapData2);
//					landMapData2.setTileHeight(tileHeight);
//					landMapData2.setTileWidth(tileWidth);
//					
//					
//					
//					SceneMapCache.addMap(2,mapName,landMapData2);
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
	}*/
}
