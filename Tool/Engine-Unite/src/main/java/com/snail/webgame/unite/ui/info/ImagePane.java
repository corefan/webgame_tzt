package com.snail.webgame.unite.ui.info;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 画板
 * @author panxj
 * @version 1.0 2010-8-18
 */

@SuppressWarnings("serial")
public class ImagePane extends JPanel {
	private CompCfgInfo  compInfo;
	
	public ImagePane (CompCfgInfo compInfo)
	{	
		super();
		this.compInfo = compInfo;		
	}		
	
	@Override
	protected void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
//		File file = new File(ImagePane.class.getClass().getResource(compInfo.getText()).getPath());
//		if(file.isFile())
//		{
			try
			{
				BufferedImage image  = ImageIO.read(ImagePane.class.getClass().getResourceAsStream(compInfo.getText()));				
				int imgWH = image.getWidth();
				int imgHT = image.getHeight();
				int compWH = compInfo.getWidth();
				int compHT = imgHT*compWH/imgWH;
				if(image != null)
				{
					g.drawImage(image.getScaledInstance(compWH, compHT, Image.SCALE_SMOOTH),0,0, null);
					super.paintComponents(g);
				}
			}
			catch (IOException e)
			{						
			}
//		}		
	}
}
