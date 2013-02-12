package org.projii.serverside.cs.interaction.client.requests;

import org.projii.commons.net.CoordinationServerRequests;

public class JoinGameRequest extends ClientRequest {
    private final int type = CoordinationServerRequests.JOIN_GAME;

    @Override
    public int getType() {
        return type;
    }
}
