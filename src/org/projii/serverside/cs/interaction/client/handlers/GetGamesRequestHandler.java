package org.projii.serverside.cs.interaction.client.handlers;

import org.jboss.netty.channel.Channel;
import org.projii.commons.GameInfo;
import org.projii.commons.TimeLogger;
import org.projii.serverside.cs.GamesManager;
import org.projii.serverside.cs.interaction.Request;
import org.projii.serverside.cs.interaction.client.RequestHandler;
import org.projii.serverside.cs.interaction.client.responses.GameInfoResponse;

import java.lang.reflect.Array;

public class GetGamesRequestHandler implements RequestHandler {

    private GamesManager gamesManager;

    public GetGamesRequestHandler(GamesManager gamesManager) {
        this.gamesManager = gamesManager;
    }

    @Override
    public void handle(Request request, Channel channel) {
        GameInfo[] games = gamesManager.getGamesArray();
        channel.write(new GameInfoResponse(games));
    }

}
