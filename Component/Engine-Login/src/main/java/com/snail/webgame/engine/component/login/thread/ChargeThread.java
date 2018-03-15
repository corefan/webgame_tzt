package com.snail.webgame.engine.component.login.thread;

import org.apache.mina.common.IoSession;
import org.epilot.ccf.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.Flag;
import com.snail.webgame.engine.common.ServerName;
import com.snail.webgame.engine.component.login.cache.ServerMap;
import com.snail.webgame.engine.component.login.charge.service.ChargeMgtService;

/**
 * 链路保持线程
 * 
 * @author cici
 * 
 */
public class ChargeThread extends Thread {

	private static final Logger logger = LoggerFactory.getLogger("logs");

	private volatile boolean cancel = false;

	public void run() {
		while (!cancel) {
			long begTime = System.currentTimeMillis();
			IoSession session = ServerMap
					.getServerSession(ServerName.GAME_CHARGE_SERVER);

			if (session != null && session.isConnected()) {

				if (Flag.flag == 0) {
					// 发送激活链接
					ChargeMgtService.activeCharge(session);

				}

			}
			else {

				session = Client.getInstance().initConnect(
						ServerName.GAME_CHARGE_SERVER);

				if (session != null && session.isConnected()) {
					ChargeMgtService.sendReqCharge(session);

					ServerMap.addServer(ServerName.GAME_CHARGE_SERVER, session);
				}

			}
			// 线程耗时日志
			long endTime = System.currentTimeMillis();
			double costTime = (endTime - begTime) / 1000d;

			if (logger.isInfoEnabled()) {
				logger.info("[SYSTEM] ChargeThread cost time : " + costTime);
			}

			try {
				Thread.sleep(10000);
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
