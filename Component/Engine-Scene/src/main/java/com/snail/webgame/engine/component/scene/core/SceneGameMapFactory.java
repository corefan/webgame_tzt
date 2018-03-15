package com.snail.webgame.engine.component.scene.core;

import java.util.HashMap;
import java.util.Map;

import org.critterai.nav.TriNavMesh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snail.webgame.engine.common.info.MapPoint;
import com.snail.webgame.engine.common.pathfinding.mesh.GameMap3D;
import com.snail.webgame.engine.common.pathfinding.mesh.LocationHelper;
import com.snail.webgame.engine.component.scene.cache.SceneMapCache;
import com.snail.webgame.engine.component.scene.info.LandMapData;

public class SceneGameMapFactory {
	private static final Logger logger = LoggerFactory.getLogger("logs");
	private static Map<String, GameMap3D> gameMap = new HashMap<String, GameMap3D>();

	/**
	 * 获得地图
	 * 
	 * @param type
	 * @param mapId
	 * @return
	 */
	public static GameMap3D getGameMap(String mapId) {
		if (gameMap.containsKey(mapId)) {
			return gameMap.get(mapId);
		}
		else {
			GameMap3D game = createGameMap(mapId);
			if (game != null) {
				gameMap.put(mapId, game);
				return game;
			}
		}
		return null;
	}

	private static GameMap3D createGameMap(String mapId) {
		LandMapData mapData = SceneMapCache.getMapData(mapId);

		if (mapData == null) {
			if (logger.isErrorEnabled()) {
				logger.error("map is not found! mapId = " + mapId);
			}
			return null;
		}

		int maxX = mapData.getMaxX();
		int maxY = mapData.getMaxY();
		int maxZ = mapData.getMaxZ();

		TriNavMesh mesh = mapData.getMesh();
		LocationHelper helper = new LocationHelper(mesh);

		MapPoint[][][] mapPoints = new MapPoint[maxX][maxY][maxZ];
		GameMap3D gameMap = new GameMap3D(helper, mapPoints, maxX, maxY, maxZ, mapData.getPoints());

		return gameMap;
	}
}
