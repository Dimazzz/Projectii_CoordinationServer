package org.projii.serverside.cs.interaction.client.responses;

import org.jai.BSON.BSONSerializable;
import org.projii.commons.net.CoordinationServerResponses;
import org.projii.commons.net.InteractionMessage;

public class ErrorResponse implements InteractionMessage {
    @BSONSerializable
    private final int type;

    public ErrorResponse() {
        this.type = CoordinationServerResponses.ERROR;
    }

    @Override
    public int getType() {
        return type;
    }
}
