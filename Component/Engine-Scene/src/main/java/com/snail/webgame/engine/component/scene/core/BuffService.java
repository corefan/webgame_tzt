package com.snail.webgame.engine.component.scene.core;

import java.util.List;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.core.protocol.Message;

import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.common.info.AIInfo;
import com.snail.webgame.engine.common.info.BuffInfo;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.common.pathfinding.mesh.GameMap3D;
import com.snail.webgame.engine.component.scene.GameMessageHead;
import com.snail.webgame.engine.component.scene.cache.BuffXMlInfoMap;
import com.snail.webgame.engine.component.scene.cache.ServerMap;
import com.snail.webgame.engine.component.scene.cache.SkillXMLInfoMap;
import com.snail.webgame.engine.component.scene.config.GameConfig;
import com.snail.webgame.engine.component.scene.info.BuffXMlInfo;
import com.snail.webgame.engine.component.scene.info.SkillUpgradeXMLInfo;
import com.snail.webgame.engine.component.scene.protocal.buff.BuffResp;
import com.snail.webgame.engine.component.scene.util.UniqueId;

public class BuffService {

	/**
	 * 添加buffer
	 * 
	 * @param attack
	 *            进攻者
	 * @param defendInfo
	 *            目标
	 * @param skillType
	 *            技能类型
	 * @param skillLevel
	 *            技能等级
	 */
	public static void addPhalanxBuff(AIInfo attack, AIInfo defendInfo, int skillNo, int skillLevel) {

		switch (skillNo) {

			case 36404:
				addSkillBuff_36404(attack, defendInfo, skillNo, skillLevel);
			case 36405:
				addSkillBuff_36405(attack, defendInfo, skillNo, skillLevel);
				break;
			default:
				break;
		}
	}

	/**
	 * 
	 * @param attack
	 * @param defendInfo
	 * @param skillNo
	 * @param skillLevel
	 * @author wangxf
	 * @date 2012-12-24
	 */
	private static void addSkillBuff_36405(AIInfo attack, AIInfo defendInfo,
			int skillNo, int skillLevel) {
		SkillUpgradeXMLInfo skillXmlInfo = SkillXMLInfoMap.getSkillXMLInfo(skillNo, skillLevel);

		List<Integer> buffList = skillXmlInfo.getBuffNo();
		if (buffList != null && buffList.size() > 0) {
			for (int i = 0; i < buffList.size(); i++) {
				BuffXMlInfo buffXMLInfo = BuffXMlInfoMap.getBuffXMLInfo(buffList.get(i));
				if(buffXMLInfo != null){
					BuffInfo buff = new BuffInfo();
					buff.setId(UniqueId.getUniqueId());
					buff.setBuffNo(buffXMLInfo.getBuffNo());
					buff.setType(buffXMLInfo.getType());
					buff.setBuffTime(buffXMLInfo.getKeepTime());
					buff.setCreateTime(System.currentTimeMillis());
					addBuff(defendInfo, buff);
				}
			}
		}
	}

	/**
	 * 移除buffer
	 * 
	 * @param defendInfo
	 *            目标
	 * @param skillType
	 *            技能类型
	 * @param skillLevel
	 *            技能等级
	 */
	public static void removeBuffAffect(AIInfo defendInfo, BuffInfo buff) {
		switch (buff.getBuffNo()) {
			case 40001:
				removeBuffAffect_40001(defendInfo, buff);
				break;
			default:
				break;
		}
	}

	public static void removeBuffAffect_40001(AIInfo defendInfo, BuffInfo buff) {

	}

	public static void addSkillBuff_36404(AIInfo attack, AIInfo defendInfo, int skillNo, int skillLevel) {
		SkillUpgradeXMLInfo skillXmlInfo = SkillXMLInfoMap.getSkillXMLInfo(skillNo, skillLevel);
		List<Integer> buffList = skillXmlInfo.getBuffNo();
		if (buffList != null && buffList.size() > 0) {
			for (int i = 0; i < buffList.size(); i++) {
				BuffXMlInfo buffXMLInfo = BuffXMlInfoMap.getBuffXMLInfo(buffList.get(i));
				if(buffXMLInfo != null){
					BuffInfo buff = new BuffInfo();
					buff.setId(UniqueId.getUniqueId());
					buff.setBuffNo(buffXMLInfo.getBuffNo());
					buff.setType(buffXMLInfo.getType());
					buff.setBuffTime(buffXMLInfo.getKeepTime());
					buff.setCreateTime(System.currentTimeMillis());
					addBuff(defendInfo, buff);
				}
			}
		}
	}

	/**
	 * 增加BUFF
	 * @param aiInfo
	 * @param buff
	 * @return
	 */
	public static boolean addBuff(AIInfo aiInfo, BuffInfo buff) {
		if (buff != null && aiInfo.getStatus() != 2) {

			List<BuffInfo> buffList = aiInfo.getBuffList();
			//boolean flag = true;
			for (int n = 0; n < buffList.size(); n++) {
				BuffInfo b = buffList.get(n);

				if (b.getType() == buff.getType()) {
					removeBuffAffect(aiInfo, b);
					buffList.remove(b);
					sendPhalanxBuff(aiInfo, 1, b);
					n--;
				}
				/*else {
					flag = false;
				}*/

			}

			/*if (flag) {

				buffList.add(buff);

			}*/
			buffList.add(buff);

			sendPhalanxBuff(aiInfo, 0, buff);

			return true;
		}

		return false;
	}

	/**
	 * 检查BUFF
	 * @param fightId
	 * @param roleList
	 * @param armyPhalanx
	 * @param errorTime
	 */
	public static void checkPhalanxBuff(AIInfo aiInfo)
	{
		List<BuffInfo> buffList = aiInfo.getBuffList();
	 
		for(int n=0;n<buffList.size();n++)
		{
			BuffInfo b = buffList.get(n);
			
			if((System.currentTimeMillis()-b.getCreateTime())/1000>=b.getBuffTime())
			{
				removeBuffAffect(aiInfo, b);
				buffList.remove(b);
				n--;
				sendPhalanxBuff(aiInfo, 1, b);
			}
		}
	}
	/**
	 * 发送buff
	 * 
	 * @param flag
	 *            0-增加buff 1-移除Buff
	 */
	public static void sendPhalanxBuff(AIInfo aiInfo, int flag, BuffInfo buff) {

		GameMap3D gameMap = SceneGameMapFactory.getGameMap(aiInfo.getMapId());
		if(gameMap == null)
			return;
		int type = 0;
		if(aiInfo instanceof RoleInfo)
			type = 1;
		int refreshRadiiX = GameConfig.getInstance().getRefreshRadiiX();
		int refreshRadiiY = GameConfig.getInstance().getRefreshRadiiY();
		int refreshRadiiZ = GameConfig.getInstance().getRefreshRadiiZ();
		List<Long> roleIds = gameMap.getAreaAllRole((int)aiInfo.getCurrX(), (int)aiInfo.getCurrY(), (int)aiInfo.getCurrZ(), 
				refreshRadiiX, refreshRadiiY, refreshRadiiZ);
		for(long roleId : roleIds){
			RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);
			if(roleInfo != null){
				BuffResp resp = new BuffResp();
				resp.setBuffId(buff.getId());
				resp.setBuffNo(buff.getBuffNo());
				resp.setBuffAim(aiInfo.getId());
				resp.setBuffAimType(type);
				resp.setBuffType(buff.getType());
				resp.setBuffTime(buff.getBuffTime()-(int)((System.currentTimeMillis()-buff.getCreateTime())/1000));
				resp.setFlag(flag);
				
				Message message = new Message();
				GameMessageHead header = new GameMessageHead();
				header.setMsgType(0xB028);
				header.setUserID0((int)roleId);
				message.setHeader(header);
				
				message.setBody(resp);
				
				IoSession session = ServerMap.getServerSession(ServerName.GATE_SERVER_NAME + 
						"-" + roleInfo.getGateServerId());
				if(session != null && session.isConnected())
				{ 
					session.write(message);
				}
			}
		}
		
		

	}
	
	/**
	 * 判断是否存在某类型BUFF
	 * @param aiInfo
	 * @param buffType
	 */
	public static boolean existBuff(AIInfo aiInfo, int buffType){
		boolean bool = false;
		List<BuffInfo> list = aiInfo.getBuffList();
		
		if(list!=null&&list.size()>0)
		{
			for(int k =0 ;k < list.size();k++)
			{
				BuffInfo buff = list.get(k);
				if(buff!=null&&buff.getType()==buffType)
				{
					bool = true;
					break;
				}
			}
			
		}
		return bool;
	}
}
