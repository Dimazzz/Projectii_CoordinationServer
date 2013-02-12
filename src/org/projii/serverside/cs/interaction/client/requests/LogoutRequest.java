package org.projii.serverside.cs.interaction.client.requests;

import org.jai.BSON.BSONSerializable;
import org.projii.commons.net.CoordinationServerRequests;

public class LogoutRequest extends ClientRequest {
    @BSONSerializable
    private final int type = CoordinationServerRequests.LOGOUT;

    @Override
    public int getType() {
        return type;
    }
}
