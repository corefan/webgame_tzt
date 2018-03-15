package com.snail.webgame.engine.component.scene.core;

import java.util.List;

import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.NpcHatredInfo;

/**
 * 怪物智能攻击相关逻辑处理类
 */
public abstract class NpcAIService implements INpcAIService {

	
	/**
	 * 获取仇恨值最高的方阵
	 * @param hatredList
	 * @return
	 */
	public String getMaxHatredPhalanxId(List<NpcHatredInfo> hatredList,ArmyPhalanx[] armyPhalanxList)
	{
		String maxHatredPhalanxId = null;
		double total = 0;
		int maxHatred = 0;
		//标记有效方阵
		if(hatredList.size() > 0)
		{
			for(NpcHatredInfo npcHatredInfo : hatredList)
			{
				boolean f = false;
				for(ArmyPhalanx armyPhalanx : armyPhalanxList)
				{
					if(armyPhalanx != null && armyPhalanx.getRoleId() > 0 && armyPhalanx.getStatus() != 1 
							&& armyPhalanx.getPhalanxId().equals(npcHatredInfo.getPhalanxId()))
					{
						npcHatredInfo.setFlag(1);
						f = true;
					}
				}
				if(!f)
				{
					npcHatredInfo.setFlag(0);
				}
			}
		}
		if(hatredList.size() > 0)
		{
			for(NpcHatredInfo npcHatredInfo : hatredList)
			{
				if(npcHatredInfo.getFlag() == 1)
				{
					total += npcHatredInfo.getTotalDamage();
				}
			}
		}
		if(total > 0)
		{
			for(NpcHatredInfo npcHatredInfo : hatredList)
			{
				if(npcHatredInfo.getFlag() == 1)
				{
					int temp = (int)((npcHatredInfo.getTotalDamage()/total)*100) + npcHatredInfo.getSkillHatredValue();
					if(temp > maxHatred)
					{
						maxHatred = temp;
						maxHatredPhalanxId = npcHatredInfo.getPhalanxId();
					}
				}
			}
		}
		return maxHatredPhalanxId;
	}
}
