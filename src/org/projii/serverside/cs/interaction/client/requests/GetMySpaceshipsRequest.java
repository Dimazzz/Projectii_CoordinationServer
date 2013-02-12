package org.projii.serverside.cs.interaction.client.requests;

import org.jai.BSON.BSONSerializable;
import org.projii.commons.net.CoordinationServerRequests;

public class GetMySpaceshipsRequest extends ClientRequest {
    @BSONSerializable
    private final int type = CoordinationServerRequests.GET_MY_SHIPS;

    @Override
    public int getType() {
        return type;
    }
}