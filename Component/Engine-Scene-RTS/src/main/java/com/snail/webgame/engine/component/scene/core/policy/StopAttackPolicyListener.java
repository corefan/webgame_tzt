package com.snail.webgame.engine.component.scene.core.policy;

import java.util.List;

import com.snail.webgame.engine.common.pathfinding.astar.GameMap;
import com.snail.webgame.engine.component.scene.common.ArmyPhalanx;
import com.snail.webgame.engine.component.scene.common.DefendInfo;
import com.snail.webgame.engine.component.scene.common.RoleFight;
import com.snail.webgame.engine.component.scene.core.FightCalculate;
import com.snail.webgame.engine.component.scene.core.FightCmdSend;
/**
 * 不移动，自动攻击范围内的目标
 */
public class StopAttackPolicyListener implements PolicyListener {

	public void execute(RoleFight roleFight, ArmyPhalanx armyPhalanx, ArmyPhalanx[] armyPhalanxList, int errorTime) {
		
		int controlType = roleFight.getControlMap().get(armyPhalanx.getRoleId());
		GameMap gameMap = roleFight.getMap();
		FightCalculate calculate = roleFight.getFightCalculate();
		List<Integer> roleList = roleFight.getRoleList();
		
		if (armyPhalanx.getStatus() == 2 || armyPhalanx.getStatus() == 3) {
			calculate.setArmyPhlanxStagePoint(armyPhalanx, roleFight.getFightId());
			FightCmdSend.sendRoleStopCmd(roleFight.getFightId(), roleList, armyPhalanx);
			armyPhalanx.setStatus(0);
			armyPhalanx.setCurrMoveTime(0);
			armyPhalanx.setCurrPursueTime(0);
		}
		
		// 处于攻击状态
		if (armyPhalanx.getCurrAttackTime() >= armyPhalanx.getAttackMaxTime()) {
			ArmyPhalanx armyAimPhalanx = null;
			if (armyPhalanx.getAimPhalanxId() == null) {
				armyAimPhalanx = calculate.getAttackAim(armyPhalanx, armyPhalanxList, gameMap);
				if (armyAimPhalanx != null) {
					armyPhalanx.setAimPhalanxId(armyAimPhalanx.getPhalanxId());
				}
			}
			if (armyPhalanx.getAimPhalanxId() != null) {
				int aimStatus = calculate.judeAppointAim(armyPhalanx, armyPhalanxList, 0, false, gameMap);

				if (aimStatus == 1) {
					for (int i = 0; i < armyPhalanxList.length; i++) {
						if (armyPhalanxList[i] != null) {
							if (armyPhalanxList[i].getPhalanxId().equals(armyPhalanx.getAimPhalanxId())) {
								DefendInfo defendInfo = null;

								// 进行攻击
								defendInfo = calculate.getFightCore().calculateCommAttrack(roleFight, armyPhalanx,
											armyPhalanxList[i], armyPhalanxList);


								if (defendInfo != null) {
									String attackSlogan = calculate.getFightCore().getSlogan(armyPhalanx, 1);
									FightCmdSend.sendRoleAttackCmd(roleFight.getFightId(), roleList, armyPhalanx,
											armyPhalanxList[i], attackSlogan, defendInfo.getFace(), defendInfo.getTime(),
											0, 0);

									armyPhalanxList[i].getDefendList().add(defendInfo);// 遭受攻击了
								}

								break;
							}
						}
					}
					armyPhalanx.setCurrAttackTime(0);
				}
				else {
					armyPhalanx.setAimPhalanxId(null);
//					calculate.setArmyPhlanxStagePoint(armyPhalanx, roleFight.getFightId());
//					FightCmdSend.sendRoleStopCmd(roleFight.getFightId(), roleList, armyPhalanx);

				}
			}
			else {
//				calculate.setArmyPhlanxStagePoint(armyPhalanx, roleFight.getFightId());
//				FightCmdSend.sendRoleStopCmd(roleFight.getFightId(), roleList, armyPhalanx);
			}
			
		}
		else {

		}
	}

}
