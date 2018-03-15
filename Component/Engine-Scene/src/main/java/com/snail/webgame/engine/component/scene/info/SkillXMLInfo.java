package com.snail.webgame.engine.component.scene.info;

import java.util.HashMap;

public class SkillXMLInfo {
	private int no;
	private String name;
	private int type;
 
	private int art;
	private int icon;
	//技能等级
	private HashMap<Integer,SkillUpgradeXMLInfo> levelMap;
	
	
	public SkillXMLInfo()
	{
		levelMap = new HashMap<Integer,SkillUpgradeXMLInfo>();
	}


	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


 


	public int getArt() {
		return art;
	}


	public void setArt(int art) {
		this.art = art;
	}


	public int getIcon() {
		return icon;
	}


	public void setIcon(int icon) {
		this.icon = icon;
	}


	public HashMap<Integer, SkillUpgradeXMLInfo> getLevelMap() {
		return levelMap;
	}


	public void setLevelMap(HashMap<Integer, SkillUpgradeXMLInfo> levelMap) {
		this.levelMap = levelMap;
	}
	
}
