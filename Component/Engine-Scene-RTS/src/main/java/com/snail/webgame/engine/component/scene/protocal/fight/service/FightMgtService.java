package com.snail.webgame.engine.component.scene.protocal.fight.service;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import com.snail.webgame.engine.common.ErrorCode;
import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.common.pathfinding.astar.GameMap;
import com.snail.webgame.engine.common.pathfinding.astar.Mover;
import com.snail.webgame.engine.common.pathfinding.astar.Path;
import com.snail.webgame.engine.common.pathfinding.astar.UnitMover;
import com.snail.webgame.engine.component.scene.cache.FightDataCache;
import com.snail.webgame.engine.component.scene.cache.RoleFightMap;
import com.snail.webgame.engine.component.scene.cache.RoleInFightCache;
import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.RoleFight;
import com.snail.webgame.engine.component.scene.core.FightCmdSend;
import com.snail.webgame.engine.component.scene.core.policy.Policy;
import com.snail.webgame.engine.component.scene.protocal.fight.control.ControlReq;
import com.snail.webgame.engine.component.scene.protocal.fight.control.ControlResp;
import com.snail.webgame.engine.component.scene.protocal.fight.manual.ManualReq;
import com.snail.webgame.engine.component.scene.protocal.fight.out.RoleOutFightReq;
import com.snail.webgame.engine.component.scene.protocal.fight.out.RoleOutFightResp;
import com.snail.webgame.engine.component.scene.protocal.fight.prop.UsePropReq;
import com.snail.webgame.engine.component.scene.protocal.fight.prop.UsePropResp;


public abstract class FightMgtService implements IFightMgtService{

	
	/**
	 * 移除用户登录
	 * @param req
	 * @param roleId
	 * @return
	 */
	public  RoleOutFightResp roleOutFightControl(RoleOutFightReq req,int roleId)
	{
		RoleFight roleFight = FightDataCache.getRoleFight(req.getFightId());
		RoleOutFightResp resp = new RoleOutFightResp();
		if(roleFight!=null)
		{
			synchronized(roleFight)
			{
				resp.setResult(1);
				resp.setFightId(req.getFightId());
				roleFight.getControlMap().put(roleId, 0);//自动
				RoleFightMap.removeRoleFight(roleId, req.getFightId());
				RoleInFightCache.removeRoleFight(req.getFightId(),roleId);
				
			
			}
		}
		else
		{
			resp.setResult(ErrorCode.SYSTEM_ERROR);
		}
		return resp;
	}
	
	
	private int maxGridNum(ArmyPhalanx[] armyPhalanxs, List<String> idList){
		int result = 0;
		for(ArmyPhalanx phalanx : armyPhalanxs){
			if(phalanx != null && idList.contains(phalanx.getPhalanxId())){
				if(phalanx.getGridNum() > result)
					result = phalanx.getGridNum();
			}
		}
		return result;
	}
	/**
	 * 处理玩家各种策略
	 * @param roleId
	 * @param req
	 */
	public ControlResp changeRoleAim(int roleId ,ControlReq req)
	{
		long fightId = req.getFightId();
		
		String idStr = req.getId();
		
		if(idStr == null)
		{
			return null;
		}
		String[] str = idStr.split(",");
		
		List<String> idList = new LinkedList<String>();
		
		for(int i=0;i<str.length;i++)
		{
			if(str[i]!=null&&str[i].length()>0)
			{
				idList.add(str[i]);
			}
		}
		
		if(idList.size()==0)
		{
			return null;
		}
		
		
		if(FightDataCache.isExistRoleFight(fightId))
		{
			RoleFight roleFight = FightDataCache.getRoleFight(fightId);
			roleFight.getControlMap().put(roleId, 1);
			ArmyPhalanx  armyPhalanxs[] = roleFight.getArmyPhalanxs();
			
			if(armyPhalanxs!=null)
			{
				List<Point> aimPoint = roleFight.getMap().findAreaEmptyPoint(req.getAimX(), req.getAimY(), idList, idList.size(), maxGridNum(armyPhalanxs, idList));
				
				for(int i=0;i<armyPhalanxs.length;i++)
				{
					ArmyPhalanx armyPhalanx = armyPhalanxs[i];
					if(armyPhalanx!=null&&armyPhalanx.getRoleId()==roleId&&idList.contains(armyPhalanx.getPhalanxId())
							&& armyPhalanx.getItemSort() != 3)
					{
						
			 
						synchronized(roleFight)
						{
							
							armyPhalanx.setCurrMinAttackGrid(armyPhalanx.getMinAttackGrid());
							armyPhalanx.setCurrMaxAttackGrid(armyPhalanx.getMaxAttackGrid());
							
							
							if(req.getPolicy()==0)//待机
							{
								armyPhalanx.setSkillType(0);
								armyPhalanx.setAimX(-1);
								armyPhalanx.setAimY(-1);
								armyPhalanx.setAimPhalanxId(null);
								armyPhalanx.setPath(null);
							 
							}
							else if(req.getPolicy()==4)//技能攻击
							{
								ControlResp resp = roleFight.getFightCalculate().getHeroSkillService().phalanxReleaseSkill(fightId,roleFight.getRoleList(),
										armyPhalanx,req.getSkillType(),req.getSkillLevel(),req.getAimX(),
										req.getAimY(),req.getAimPhalanxId(),armyPhalanxs);
								
								return resp;
								
								
							}
							else
							{
								if(req.getPolicy()==2&&req.getAimPhalanxId()!=null&&req.getAimPhalanxId().length()>0)
								{
									
									if(armyPhalanx.getAimPhalanxId()!=null&&armyPhalanx.getAimPhalanxId().equals(req.getAimPhalanxId()))
									{
										
									}
									else
									{
										armyPhalanx.setAimPhalanxId(req.getAimPhalanxId());
										armyPhalanx.setAimX(-1);
										armyPhalanx.setAimY(-1);
										armyPhalanx.setPath(null);
										armyPhalanx.setSkillType(0);
									}	
								}
								else if(req.getPolicy()==1||req.getPolicy()==3)
								{
									
									if(!aimPoint.isEmpty()){
										Point point = aimPoint.get(0);
										aimPoint.remove(0);
										if(armyPhalanx.getAimX() != point.x || armyPhalanx.getAimY() != point.y){
													
											armyPhalanx.setAimX(point.x);
											armyPhalanx.setAimY(point.y);
												
										}
									}
									armyPhalanx.setAimPhalanxId(null);
									armyPhalanx.setSkillType(0);
									armyPhalanx.setPath(null);
								}
							}
							
							armyPhalanx.setPolicy(req.getPolicy());
							
						}
					}
				}
			}
		}
		
		
		return null;
	}
	

	public void controlArmy(int roleId ,ManualReq req)
	{
		
		long fightId = req.getFightId();
		int type = req.getType();//0-自动 1-手动
		
		if(FightDataCache.isExistRoleFight(fightId))
		{
			RoleFight roleFight = FightDataCache.getRoleFight(fightId);
		 
			 ArmyPhalanx[] armyPhalanxs = roleFight.getArmyPhalanxs();
			
			synchronized(roleFight)
			{
				 
				if(armyPhalanxs!=null)
				{
					if(type==0)
					{
						for(int i=0;i<armyPhalanxs.length;i++)
						{
								ArmyPhalanx armyPhalanx = armyPhalanxs[i];
						 
								if(armyPhalanx!=null&&armyPhalanx.getRoleId()==roleId)
										//&&armyPhalanx.getItemNo()!=GameValue.BUILD_TYPE_035
										//&&armyPhalanx.getItemNo()!=GameValue.COUNTRY_BUILD_12)
								{
									
									armyPhalanx.setPolicy(Policy.PURSUE_AUTO_ATTACK);
									armyPhalanx.setAimX(-1);
									armyPhalanx.setAimY(-1);
									armyPhalanx.setAimPhalanxId(null);
									armyPhalanx.setPath(null);
								}
						 
						}	
					 
					}
					else//切手动时通知停止
					{
						for(int i=0;i<armyPhalanxs.length;i++)
						{
							ArmyPhalanx armyPhalanx = armyPhalanxs[i];
					 
							if(armyPhalanx!=null&&armyPhalanx.getRoleId()==roleId)
									//&&armyPhalanx.getItemNo()!=GameValue.BUILD_TYPE_035
									//&&armyPhalanx.getItemNo()!=GameValue.COUNTRY_BUILD_12)
							{
								
								armyPhalanx.setPolicy(Policy.STOP_AUTO_ATTACK);
								armyPhalanx.setAimX(-1);
								armyPhalanx.setAimY(-1);
								armyPhalanx.setAimPhalanxId(null);
								roleFight.getFightCalculate().setArmyPhlanxStagePoint(armyPhalanx, fightId);
								FightCmdSend.sendRoleStopCmd(fightId, roleFight.getRoleList(), armyPhalanx);
							}
						}
					}
 
					roleFight.getControlMap().put(roleId, type);
				}
			
			}
		}
	}
	
	public UsePropResp useProp(int roleId ,UsePropReq req)
	{
		UsePropResp resp = new UsePropResp();
		
//		long fightId = req.getFightId();
//		String usePhalanxId = req.getPhalanxId();
//		int propNo = req.getPropNo();
//		String phalanxId = req.getPhalanxId();
//		
//		resp.setFightId(fightId);
//		resp.setUsePhalanxId(usePhalanxId);
//		resp.setPhalanxId(phalanxId);
//		resp.setPropNo(propNo);
//		PropXMLInfo propXMLInfo = PropXMLInfoMap.getPropXMLInfo(propNo);
//		
//		if(propXMLInfo==null)
//		{
//			resp.setResult(ErrorCode.PROP_USE_ERROR_2);
//			return resp;
//		}
//		if(FightDataCache.isExistRoleFight(fightId))
//		{
//			PropEffectReq propEffectReq  = new PropEffectReq();
//		
//			RoleFight roleFight = FightDataCache.getRoleFight(fightId);
//		 
//			 ArmyPhalanx[] armyPhalanxs = roleFight.getArmyPhalanxs();
//			
//			synchronized(roleFight)
//			{
//			 
//				int k = -1 ;
//				int g = -1;
//				for(int i=0;i<armyPhalanxs.length;i++)
//				{
//					ArmyPhalanx armyPhalanx = armyPhalanxs[i];
//				 
//					if(armyPhalanx!=null&&armyPhalanx.getPhalanxId().equals(phalanxId))
//					{
//						 
//						k = i;
//					 
//					}
//					if(armyPhalanx!=null&&armyPhalanx.getPhalanxId().equals(usePhalanxId))
//					{
//					 
//						g = i;
//					 
//					}
//					
//				}
//				long heroId = 0;
//				if(g>=0)
//				{
//					ArmyInfo armyInfos[] = roleFight.getArmyInfo();
//					for(int p=0;p<armyInfos.length;p++)
//					{
//						if(armyInfos[p]!=null&&armyInfos[p].getArmyId().equals(armyPhalanxs[g].getArmyId()))
//						{
////							heroId = armyInfos[p].getHeroId();
//							break;
//						}
//					}
//				}
//				else
//				{
//					resp.setResult(ErrorCode.GET_FIGHT_ERROR_5);
//					return resp;
//				}
//				
//				if(propXMLInfo.getFunction().equals(""+PropFunctionName.FUNCTION_40))
//				{
//					int effectFlag =1;
//					
//					if(roleFight.isBackFlag())
//					{
//						resp.setResult(ErrorCode.FIGHT_USE_ERROR_2);
//						return resp;
//					}
//					else 
//					{
//						if(roleFight.getFightType()==1||roleFight.getFightType()==3)
//						{
//							 
//						}
//						else
//						{
//							resp.setResult(ErrorCode.FIGHT_USE_ERROR_1);
//							return resp;
//						}
//					}
//					
//					
//					HashMap<Integer,HeroItemInfo> map = HeroItemMap.getHeroItemMap(heroId);
//					if(map!=null)
//					{
//						HeroItemInfo itemInfo = map.get(propNo);
//					 
//						
//						if(itemInfo!=null)
//						{
//							if(itemInfo.getItemNum()>0)
//							{
//								itemInfo.setItemNum(itemInfo.getItemNum()-1);
//				 
//							}
//							else
//							{
//								resp.setResult(ErrorCode.PROP_USE_ERROR_4);
//								return resp;
//							}
//							
//						}
//						else
//						{
//							resp.setResult(ErrorCode.PROP_USE_ERROR_4);
//							return resp;
//						}
//					}
//					else
//					{
//						resp.setResult(ErrorCode.PROP_USE_ERROR_4);
//						return resp;
//					}
//					
//					
//					roleFight.setBackFlag(true);//设置撤退标识
//					
//					
//					propEffectReq.setFightId(roleFight.getFightId());
//					propEffectReq.setFlag(effectFlag);
//					propEffectReq.setPhalanxId(armyPhalanxs[k].getPhalanxId());
//					propEffectReq.setAddHP((long) Math.ceil(armyPhalanxs[k].getCurrHP()));
//					propEffectReq.setAddMP((long) Math.ceil(armyPhalanxs[k].getCurrMP()));
//					
//					
//					FightCmdSend.sendPropUseEffect(fightId, roleFight.getRoleList(), propEffectReq);
//				}
//				else if(k>=0&&g>=0)
//				{
//					if(armyPhalanxs[k].getSide()==armyPhalanxs[g].getSide())
//					{
//						 
//					}
//					else
//					{
//						resp.setResult(ErrorCode.GET_FIGHT_ERROR_7);
//						return resp;
//					}
//					
//					HashMap<Integer,HeroItemInfo> map = HeroItemMap.getHeroItemMap(heroId);
//					if(map!=null)
//					{
//						HeroItemInfo itemInfo = map.get(propNo);
//						
//						
//						int coolSort = propXMLInfo.getCoolSort();//冷却分类
//						
//						Set<Integer> set = map.keySet();
//						
//						for(int key:set)
//						{
//							HeroItemInfo itemInfo1 = map.get(key);
//							
//							PropXMLInfo propXMLInfo1 = PropXMLInfoMap.getPropXMLInfo(itemInfo1.getItemNo());
//							if(propXMLInfo1!=null&&propXMLInfo1.getCoolSort()==coolSort)
//							{
//								long time = propXMLInfo1.getCoolTime()*1000L+itemInfo1.getUserTime();
//								
//								if(System.currentTimeMillis()>time)
//								{
//									
//								}
//								else
//								{
//									resp.setResult(ErrorCode.PROP_USE_ERROR_17);
//									return resp;
//								}
//							}
//						}
//						
//						
//						
//						
//						
//						if(itemInfo!=null)
//						{
//							if(itemInfo.getItemNum()>0)
//							{
//								itemInfo.setItemNum(itemInfo.getItemNum()-1);
//								itemInfo.setUserTime(System.currentTimeMillis());
//							}
//							else
//							{
//								resp.setResult(ErrorCode.PROP_USE_ERROR_4);
//								return resp;
//							}
//							
//						}
//						else
//						{
//							resp.setResult(ErrorCode.PROP_USE_ERROR_4);
//							return resp;
//						}
//					}
//					else
//					{
//						resp.setResult(ErrorCode.PROP_USE_ERROR_4);
//						return resp;
//					}
//					
//					int effectFlag = 0;
//					if(propXMLInfo.getFunction().equals(""+PropFunctionName.FUNCTION_9))
//					{
//						effectFlag =1 ;
//						int addHP = Integer.valueOf(propXMLInfo.getParameter1());
//						
//						if(armyPhalanxs[k].getCurrHP()+addHP>armyPhalanxs[k].getMaxHP())
//						{
//							armyPhalanxs[k].setCurrHP(armyPhalanxs[k].getMaxHP());
//						}
//						else
//						{
//							armyPhalanxs[k].setCurrHP(armyPhalanxs[k].getCurrHP()+addHP);
//						}
//					
//						
//						if(armyPhalanxs[k].getCurrHP()%armyPhalanxs[k].getEveHP()==0)
//						{
//							armyPhalanxs[k].setItemNum((int) (armyPhalanxs[k].getCurrHP()/armyPhalanxs[k].getEveHP()));
//						}
//						else
//						{
//							armyPhalanxs[k].setItemNum((int) (armyPhalanxs[k].getCurrHP()/armyPhalanxs[k].getEveHP())+1);
//						}
//						
//						
//					}
//					else if(propXMLInfo.getFunction().equals(""+PropFunctionName.FUNCTION_10))
//					{
//						effectFlag = 2 ;
//						int addMP = Integer.valueOf(propXMLInfo.getParameter1());
//						if(armyPhalanxs[k].getCurrMP()+addMP>armyPhalanxs[k].getMaxMP())
//						{
//							armyPhalanxs[k].setCurrMP(armyPhalanxs[k].getMaxMP());
//						}
//						else
//						{
//							armyPhalanxs[k].setCurrMP(armyPhalanxs[k].getCurrMP()+addMP);
//						}
//						
//						
//					}
//					else
//					{
//						resp.setResult(ErrorCode.GET_FIGHT_ERROR_6);
//						return resp;
//					}
//					
//					propEffectReq.setFightId(roleFight.getFightId());
//					propEffectReq.setFlag(effectFlag);
//					propEffectReq.setPhalanxId(armyPhalanxs[k].getPhalanxId());
//					propEffectReq.setAddHP((long) Math.ceil(armyPhalanxs[k].getCurrHP()));
//					propEffectReq.setAddMP(armyPhalanxs[k].getCurrMP());
//					
//					
//					FightCmdSend.sendPropUseEffect(fightId, roleFight.getRoleList(), propEffectReq);
//				}
//				else
//				{
//					resp.setResult(ErrorCode.GET_FIGHT_ERROR_5);
//					return resp;
//				}
//				
//			}
//		}
//		else
//		{
//			resp.setResult(ErrorCode.GET_FIGHT_ERROR_4);
//			return resp;
//		}
//		
		resp.setResult(1);
		
 
		return resp;
	}
	
//	public void mgtGetArmyQueue(SysReq req)
//	{
////		long fightId = req.getFightId();
////		if(FightDataCache.isExistRoleFight(fightId))
////		{
////			RoleFight roleFight = FightDataCache.getRoleFight(fightId);
////			synchronized(roleFight)
////			{
////				
////		 
////				List list = req.getList();
////				
////				if(list!=null&&list.size()>0)
////				{
////					roleFight.setQueueStatus(0);
////					
////					List<ArmyInfo> addList = new ArrayList<ArmyInfo>();
////					
////					int side0LiveNum = 0;
////					int side1LiveNum = 0;
////					ArmyInfo armyInfos [] = roleFight.getArmyInfo();
////					int g = 0 ;
////					int u = 0 ;
////					for(int p=0;p<armyInfos.length;p++)
////					{
////						if(armyInfos[p]==null)
////						{
////							g = p ;
////							u = p;
////							break;
////						}
////						else
////						{
////							if(armyInfos[p].getSide() == 0 && armyInfos[p].getStatus() != 1)
////							{
////								side0LiveNum ++ ;
////							}
////							else if(armyInfos[p].getSide() == 1 && armyInfos[p].getStatus() != 1)
////							{
////								side1LiveNum ++;
////							}
////						}
////					}
////					
////					HashMap<Integer,Integer> controlMap = roleFight.getControlMap();
////			 
////					LandMapData landMapData = FightMapCache.getLandMapData(roleFight.getFightMapType());
////					for(int k=0;k<list.size();k++)
////					{
////						SysArmyReq sysArmyReq = (SysArmyReq) list.get(k);
////						
////						int controlType = 0;
////						 
////						if(controlMap.containsKey(sysArmyReq.getRoleId()))
////						{
////							 controlType = controlMap.get(sysArmyReq.getRoleId());
////						}
////						
////						if(sysArmyReq.getSide()==0)
////						{
////							if(side0LiveNum + 1 > roleFight.getMaxAttackNum())
////							{
////								SendJoinArmyQueueMessage(ErrorCode.GET_FIGHT_ERROR_8, sysArmyReq.getRoleId(), fightId, req.getFightType());
////								return;
////							}
////							
////							int side0 = roleFight.getSide0();
////							armyInfos[g] = SysMgtService.getArmyInfo(sysArmyReq,roleFight.getRoleList(),
////									roleFight,battleConfig,side0, controlType, landMapData);
////							side0++;
////							if(side0>=roleFight.getMaxAttackNum())
////							{
////								side0 = 0;
////							}
////							roleFight.setSide0(side0);
////							
////						}
////						else
////						{
////							if(side1LiveNum + 1 > roleFight.getMaxDefendNum())
////							{
////								SendJoinArmyQueueMessage(ErrorCode.GET_FIGHT_ERROR_8, sysArmyReq.getRoleId(), fightId, req.getFightType());
////								return;
////							}
////							int side1 = roleFight.getSide1();
////							armyInfos[g] = ISysMgtService.getArmyInfo(sysArmyReq,roleFight.getRoleList(),
////									roleFight,battleConfig,side1, controlType, landMapData);
////							side1++;
////							
////							if(side1>=roleFight.getMaxDefendNum()-1)//有一只城市建筑物
////							{
////								side1 = 0;
////							}
////							roleFight.setSide1(side1);
////						}
////					 
////						//中途进入战场部队，修改所属玩家的side，用于战场聊天
////						RoleInfo roleInfo = RoleInFightCache.getRoleInfo(fightId, sysArmyReq.getRoleId());
////						if(roleInfo!=null)
////						{
////							roleInfo.setSide(armyInfos[g].getSide());
////						}
////						
////						addList.add(armyInfos[g] );
////						g++;
////				 
////					}
////					
////					int totalNum = roleFight.getTotalNum();
////					 
////					
////				
////				 
////					for(int k = u ;k<armyInfos.length;k++)
////					{
////						if(armyInfos[k]==null)
////						{
////							continue;
////						}
////						ArmyPhalanx[] phalanxs = armyInfos[k].getArmyPhalanx();
////						
////						int type = controlMap.get(armyInfos[k].getRoleId());
////						
////						for(int l=0;l<phalanxs.length;l++)
////						{
////							if(type==0)//自动
////							{
////								phalanxs[l] .setPolicy(3);
////							}
////							setPhalanxsXY(phalanxs[l], roleFight.getMap(), roleFight);
////						}
////						
////						
////					
////						
////						
////						ArmyPhalanx[] armyPhalanxs = 	roleFight.getArmyPhalanxs();
////						
////						
////						for(int m = 0;m<phalanxs.length;m++)
////						{
////							armyPhalanxs[totalNum] = phalanxs[m];
////							
////							
////							totalNum++;
////						}
////					}
////					
////					roleFight.setTotalNum(totalNum);
////					
////
////					//发送中途加入提醒
////					
////					FightCmdSend.sendArmyAdd(fightId, roleFight, addList);
////					
////					
////					for(int k = u ;k<armyInfos.length;k++)
////					{
////						if(armyInfos[k]==null)
////						{
////							continue;
////						}
////						
////						ISysMgtService.sendATCityAwoke(fightId, FightConfig.getInstance().getFightServerId(), armyInfos[k], req.getFightType());
////						
////					}
////					
////				}
////				else 
////				{
////					if(roleFight.getQueueStatus()==1)
////					{
////						roleFight.setQueueStatus(2);
////					}
////				}
////
////				roleFight.setBool(false);
////				
////			}
////		
////		}
////		else
////		{
////			List list = req.getList();
////			for(int i=0; i<list.size(); i++)
////			{
////				SysArmyReq sysArmyReq = (SysArmyReq)list.get(i);
////				if(sysArmyReq != null && sysArmyReq.getRoleId() > 0)
////				{
////					SendJoinArmyQueueMessage(ErrorCode.GET_FIGHT_ERROR_8, sysArmyReq.getRoleId(), fightId, req.getFightType());
////				}
////			}
////			
////		}
//	}
	
	
	private void setPhalanxsXY(ArmyPhalanx phalanxs,GameMap map, RoleFight roleFight)
	{
 
		byte [][] mapData = map.getTerrain();
		
		int currX = phalanxs.getCurrX();
		int currY = phalanxs.getCurrY();
		
		
		for(int x=currX;x<mapData.length;x++)	
		{
			for( int y =currY;y<mapData[0].length;y++)
			{
				if(mapData[x][y]!=0||map.getUnit(x, y)!=null)
				{
					//有阻挡
					
				}
				else
				{
					phalanxs.setCurrX(x);
					phalanxs.setCurrY(y);
					phalanxs.setPursuePreX(phalanxs.getCurrX());
					phalanxs.setPursuePreY(phalanxs.getCurrY());
					roleFight.getFightCalculate().setArmyPhlanxStagePoint(phalanxs, map);

					if(roleFight.getFightCalculate().isRoleBlock()){
						map.setUnit(x, y,phalanxs.getPhalanxId(),phalanxs.getGridNum());
					}
					roleFight.getFightCalculate().resetPhalanxPoint(x, y, phalanxs, map);
					return;
				}
			}
			
		}
		for(int x=currX;x>=0;x--)	
		{
			for( int y =currY;y>=0;y--)
			{
				if(mapData[x][y]!=0||map.getUnit(x, y)!=null)
				{
					//有阻挡
					
				}
				else
				{
					phalanxs.setCurrX(x);
					phalanxs.setCurrY(y);
					
					roleFight.getFightCalculate().setArmyPhlanxStagePoint(phalanxs, map);
					if(roleFight.getFightCalculate().isRoleBlock()){
						map.setUnit(x, y, phalanxs.getPhalanxId(),phalanxs.getGridNum());
					}
					roleFight.getFightCalculate().resetPhalanxPoint(x, y, phalanxs, map);
					return ;
				}
			}
			
		}
		 
			
		 
	}
	
}
