package org.projii.serverside.cs;

import org.projii.commons.GameInfo;

import java.util.ArrayList;
import java.util.List;

public class GamesManager {

    private List<GameInfo> games;

    public GamesManager() {
        games = new ArrayList<>();
        games.add(new GameInfo(1, "localhost", "map0", 0, 10));
        games.add(new GameInfo(2, "localhost", "map1", 4, 5));
        games.add(new GameInfo(3, "localhost", "map0", 2, 10));
        games.add(new GameInfo(4, "localhost", "map7", 0, 10));
        games.add(new GameInfo(5, "localhost", "map123", 1, 2));
    }

    public List<GameInfo> getGames() {
        return games;
    }
    public GameInfo[] getGamesArray() {
        return games.toArray(new GameInfo[games.size()]);
    }
    public void addGame(GameInfo gameInfo){
    	games.add(gameInfo);
    }
    public void removeGame(GameInfo gameInfo){
    	games.remove(gameInfo);
    }
    public GameInfo getGameById(int id){
    	for(GameInfo gameInfo : games){
    		if (gameInfo.gameId == id){
    			return gameInfo;
    		}
    	}
    	return null;
    }

}
