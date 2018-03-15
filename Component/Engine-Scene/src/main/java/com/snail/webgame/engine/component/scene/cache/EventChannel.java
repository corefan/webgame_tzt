package com.snail.webgame.engine.component.scene.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.snail.webgame.engine.component.scene.event.AttackEvent;
import com.snail.webgame.engine.component.scene.event.Event;

/**
 * 事件通道 按顺序执行事件
 * 
 * @author zenggy
 * 
 */
public class EventChannel {

	/**
	 * 非战斗状态接收事件队列
	 */
	private static Map<String, Queue<Event>> inEventList = new HashMap<String, Queue<Event>>();
	/**
	 * 非战斗状态输出事件队列
	 */
	private static Map<String, Queue<Event>> outEventList = new HashMap<String, Queue<Event>>();

//	/**
//	 * 战斗状态接收事件队列
//	 */
//	private static Map<String, Queue<Event>> inBattleEventList = new HashMap<String, Queue<Event>>();
//
//	/**
//	 * 战斗状态输出事件队列
//	 */
//	private static Map<String, Queue<Event>> outBattleEventList = new HashMap<String, Queue<Event>>();

	/**
	 * 添加事件到队列
	 * 
	 * @param mapId
	 *            地图ID
	 * @param e
	 *            事件
	 * @param battleStatus
	 *            战斗状态 0-非战斗 1-战斗状态
	 */
	public static void addEvent(String mapId, Event e, int battleStatus) {
//		if (battleStatus == 0) {
			synchronized (inEventList) {
				if (inEventList.containsKey(mapId)) {
					Queue<Event> eventQueue = inEventList.get(mapId);
					if (e.isReplace() && eventQueue.contains(e))
						eventQueue.remove(e);
					eventQueue.offer(e);
				}
				else {
					Queue<Event> eventQuery = new LinkedBlockingQueue<Event>();
					eventQuery.offer(e);
					inEventList.put(mapId, eventQuery);
				}
			}

//		}
//		else if (battleStatus == 1) {
//			synchronized (inBattleEventList) {
//				if (inBattleEventList.containsKey(mapId)) {
//					Queue<Event> eventQueue = inBattleEventList.get(mapId);
//					if (e.isReplace() && eventQueue.contains(e))
//						eventQueue.remove(e);
//					eventQueue.offer(e);
//				}
//				else {
//					Queue<Event> eventQuery = new LinkedBlockingQueue<Event>();
//					eventQuery.offer(e);
//					inBattleEventList.put(mapId, eventQuery);
//				}
//			}
//
//		}
	}

	/**
	 * 获取事件队列
	 * 
	 * @param mapId
	 *            地图ID
	 * @param battleStatus
	 *            战斗状态 0-非战斗 1-战斗状态
	 */
	public static Queue<Event> getEvent(String mapId, int battleStatus) {
//		if (battleStatus == 0) {
			if (outEventList.containsKey(mapId)
					&& !outEventList.get(mapId).isEmpty()) {
				return outEventList.get(mapId);
			}
			else {
				synchronized (inEventList) {
					if (inEventList.containsKey(mapId)) {

						LinkedBlockingQueue<Event> tmp = (LinkedBlockingQueue<Event>)inEventList.get(mapId);
						if (!outEventList.containsKey(mapId)) {
							Queue<Event> outEvent = new LinkedBlockingQueue<Event>();
							outEventList.put(mapId, outEvent);
						}
						Queue<Event> outEvent = outEventList.get(mapId);
						tmp.drainTo(outEvent);
						return outEvent;
					}
					else
						return null;
				}
			}

//		}
//		else if (battleStatus == 1) {
//			if (outBattleEventList.containsKey(mapId)
//					&& !outBattleEventList.get(mapId).isEmpty()) {
//				return outBattleEventList.get(mapId);
//			}
//			else {
//				synchronized (inBattleEventList) {
//					if (inBattleEventList.containsKey(mapId)) {
//
//						LinkedBlockingQueue<Event> tmp = (LinkedBlockingQueue<Event>)inBattleEventList.get(mapId);
//						if (!outBattleEventList.containsKey(mapId)) {
//							Queue<Event> outEvent = new LinkedBlockingQueue<Event>();
//							outBattleEventList.put(mapId, outEvent);
//						}
//						Queue<Event> outEvent = outBattleEventList.get(mapId);
//						tmp.drainTo(outEvent);
//						return outEvent;
//					}
//					else
//						return null;
//				}
//			}
//		}
//		return null;
	}

	/**
	 * 判断队列中是否存在某事件
	 * @param event
	 * @param mapId
	 * @param battleStatus
	 * @return
	 */
	public static boolean existEvent(Event event, String mapId, int battleStatus) {
//		if (battleStatus == 0) {

			if (inEventList.containsKey(mapId)) {

				Queue<Event> tmp = inEventList.get(mapId);
				Queue<Event> tmp1 = outEventList.get(mapId);
				if ((tmp != null && tmp.contains(event)) || (tmp1 != null && tmp1.contains(event)))
					return true;

			}

//		}
//		else if (battleStatus == 1) {
//			if (inBattleEventList.containsKey(mapId)) {
//
//				Queue<Event> tmp = inBattleEventList.get(mapId);
//				Queue<Event> tmp1 = outBattleEventList.get(mapId);
//				if ((tmp != null && tmp.contains(event)) || (tmp1 != null && tmp1.contains(event)))
//					return true;
//			}
//		}
		return false;
	}

	/**
	 * 移除某玩家的攻击事件
	 * @param event
	 * @author wangxf
	 * @param mapId 
	 * @date 2013-1-16
	 */
	public static void removeEvent(Event event, String mapId) {
		synchronized (inEventList) {
			if (inEventList.containsKey(mapId)) {

				Queue<Event> tmp = inEventList.get(mapId);
				Queue<Event> tmp1 = outEventList.get(mapId);
				if (tmp != null && tmp.size() > 0) {
					Queue<Event> removeEvent = new LinkedBlockingQueue<Event>();
					for (Event tmpEvent : tmp) {
						if (tmpEvent.equals(event)) {
							removeEvent.add(tmpEvent);
						}
					}
					
					if (removeEvent.size() > 0) {
						tmp.removeAll(removeEvent);
					}
				}
				
				if (tmp1 != null && tmp1.size() > 0) {
					Queue<Event> removeEvent = new LinkedBlockingQueue<Event>();
					for (Event tmpEvent : tmp1) {
						if (tmpEvent.equals(event)) {
							removeEvent.add(tmpEvent);
						}
					}
					
					if (removeEvent.size() > 0) {
						tmp1.removeAll(removeEvent);
					}
				}
			}
		}
	}
}
