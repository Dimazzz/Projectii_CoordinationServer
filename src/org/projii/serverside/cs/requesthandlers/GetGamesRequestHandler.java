package org.projii.serverside.cs.requesthandlers;


import org.jai.BSON.BSONArray;
import org.jai.BSON.BSONDocument;
import org.jai.BSON.BSONSerializer;
import org.projii.commons.GameInfo;
import org.projii.commons.net.CoordinationServerResponses;
import org.projii.serverside.cs.GamesManager;
import org.projii.serverside.cs.SessionInfo;

import java.util.List;

public class GetGamesRequestHandler implements RequestHandler {

    private GamesManager gamesManager;

    public GetGamesRequestHandler(GamesManager gamesManager) {
        this.gamesManager = gamesManager;
    }

    @Override
    public BSONDocument handle(BSONDocument request, SessionInfo sessionInfo) {
        List<GameInfo> gameInfoList = gamesManager.getGames();
        BSONArray games = new BSONArray();
        for (GameInfo gameInfo : gameInfoList) {
            BSONDocument gi = BSONSerializer.serialize(gameInfo);
            games.add(gi);
        }
        return new BSONDocument().add("type", CoordinationServerResponses.GAMES_INFO).add("games", games);
    }
}
