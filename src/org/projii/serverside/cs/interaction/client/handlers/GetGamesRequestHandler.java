package org.projii.serverside.cs.interaction.client.handlers;

import org.jboss.netty.channel.Channel;
import org.projii.commons.GameInfo;
import org.projii.commons.net.InteractionMessage;
import org.projii.serverside.commons.RequestHandler;
import org.projii.serverside.cs.GamesManager;
import org.projii.serverside.cs.interaction.client.responses.GameInfoResponse;

public class GetGamesRequestHandler implements RequestHandler {

    private GamesManager gamesManager;

    public GetGamesRequestHandler(GamesManager gamesManager) {
        this.gamesManager = gamesManager;
    }

    @Override
    public void handle(InteractionMessage request, Channel channel) {
        GameInfo[] games = gamesManager.getGamesArray();
        channel.write(new GameInfoResponse(games));
    }

}
