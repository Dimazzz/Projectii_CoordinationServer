package org.projii.serverside.cs.interaction.client.responses;

import org.jai.BSON.BSONSerializable;
import org.projii.commons.GameInfo;
import org.projii.commons.net.CoordinationServerResponses;
import org.projii.serverside.cs.interaction.Response;

public class GameInfoResponse implements Response {
    @BSONSerializable
    private final GameInfo games[];
    @BSONSerializable
    private final int type;

    public GameInfoResponse(GameInfo[] games) {
        this.type = CoordinationServerResponses.GAMES;
        this.games = games;
    }

    @Override
    public int getType() {
        return type;
    }
}