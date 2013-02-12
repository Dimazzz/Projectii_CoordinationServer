package org.projii.serverside.cs.interaction.client.responses;

import org.jai.BSON.BSONSerializable;
import org.projii.commons.net.CoordinationServerResponses;
import org.projii.serverside.cs.interaction.Response;

public class ShipsInfoResponse implements Response {

    @BSONSerializable
    private final int type;

    public ShipsInfoResponse() {
        this.type = CoordinationServerResponses.SPACESHIPS;
    }

    @Override
    public int getType() {
        return type;
    }
}
