package org.projii.serverside.cs.requesthandlers;


import org.jai.BSON.BSONDocument;
import org.projii.commons.GameInfo;
import org.projii.serverside.cs.GamesManager;
import org.projii.serverside.cs.SessionInfo;

import java.util.List;

import static org.projii.commons.net.CoordinationServerResponses.GAMES_INFO;

public class GetGamesRequestHandler implements RequestHandler {

    private GamesManager gamesManager;

    public GetGamesRequestHandler(GamesManager gamesManager) {
        this.gamesManager = gamesManager;
    }

    @Override
    public BSONDocument handle(BSONDocument request, SessionInfo sessionInfo) {
        List<GameInfo> gameInfoList = gamesManager.getGames();
        BSONDocument games = new BSONDocument();

        int i = 0;
        for (GameInfo g : gameInfoList) {
            BSONDocument game = new BSONDocument();

            game.add("gameId", g.gameId);
            game.add("serverIP", g.serverIP);
            game.add("mapName", g.mapName);
            game.add("currentPlayersAmount", g.currentPlayersAmount);
            game.add("maxPlayersAmount", g.maxPlayersAmount);

            games.add(Integer.toString(i), game);
            i++;
        }

        return new BSONDocument().add("type", GAMES_INFO).add("games", games);
    }
}
