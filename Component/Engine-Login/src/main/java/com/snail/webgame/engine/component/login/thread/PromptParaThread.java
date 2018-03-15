package com.snail.webgame.engine.component.login.thread;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.GameValue;
import com.snail.webgame.engine.common.cache.RoleInfoMap;
import com.snail.webgame.engine.common.info.RoleInfo;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;
import com.snail.webgame.engine.component.login.config.GameOtherConfig;

/**
 * 国内防沉迷线程
 * 
 * @author cici
 * 
 */
public class PromptParaThread extends Thread {

	private static final Logger logger = LoggerFactory.getLogger("logs");

	private volatile boolean cancel = false;

	private ChargeMgtService chargeMgtService = new ChargeMgtService();

	public void run() {
		while (!cancel) {
			long begTime = System.currentTimeMillis();

			// 国内防沉迷功能 借助该线程 每两分钟向计费服务器发一次请求
			if (GameOtherConfig.getInstance().getPromptFlag() == 3) {
				Set<Long> set = RoleInfoMap.getRoleInfoSet();

				if (set != null && set.size() > 0) {
					for (long roleId : set) {
						RoleInfo roleInfo = RoleInfoMap.getRoleInfo(roleId);

						if (roleInfo != null) {
							if (roleInfo.getPromptStatus() != 1
									&& roleInfo.getLoginStatus() == 1) {
								chargeMgtService.sendRefreshReq(roleId);

							}
						}
					}
				}
			}

			// 线程耗时日志
			long endTime = System.currentTimeMillis();
			double costTime = (endTime - begTime) / 1000d;
			if (logger.isInfoEnabled()) {
				logger.info("[SYSTEM] PromptPara cost time : " + costTime);
			}

			try {
				Thread.sleep(GameValue.GAME_INDULGE_TIME * 1000);
			}
			catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
	}

	public void cancel() {
		cancel = true;
	}

}
