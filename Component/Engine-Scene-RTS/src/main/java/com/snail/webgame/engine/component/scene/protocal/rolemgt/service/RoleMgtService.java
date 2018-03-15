package com.snail.webgame.engine.component.scene.protocal.rolemgt.service;

import java.util.List;

import com.snail.webgame.engine.component.scene.cache.FightDataCache;
import com.snail.webgame.engine.component.scene.cache.RoleFightMap;
import com.snail.webgame.engine.component.scene.cache.RoleInFightCache;
import com.snail.webgame.engine.component.scene.common.RoleFight;

public class RoleMgtService {

	public void userLogout(int roleId) {
	 
		List<Long> list = RoleFightMap.getRoleInfo(roleId);
		
		if(list!=null)
		{
			for(int i=0;i<list.size();i++)
			{
				
				RoleFight roleFight = FightDataCache.getRoleFight(list.get(i));
				roleFight.getControlMap().put(roleId, 0);//自动
				RoleInFightCache.removeRoleFight(list.get(i),roleId);
			}
			RoleFightMap.removeRoleFight(roleId);
		}
	}

}
