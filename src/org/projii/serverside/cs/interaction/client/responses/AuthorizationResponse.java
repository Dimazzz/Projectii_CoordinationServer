package org.projii.serverside.cs.interaction.client.responses;

import org.jai.BSON.BSONSerializable;
import org.projii.commons.net.CoordinationServerResponses;
import org.projii.serverside.cs.interaction.Response;

public class AuthorizationResponse implements Response {

    @BSONSerializable
    private final boolean result;
    @BSONSerializable
    private final int sessionId;
    @BSONSerializable
    private final int type;

    public AuthorizationResponse(boolean result, int sessionId) {
        this.type = CoordinationServerResponses.AUTHORIZATION_RESULT;
        this.result = result;
        this.sessionId = sessionId;
    }

    @Override
    public int getType() {
        return type;
    }
}
