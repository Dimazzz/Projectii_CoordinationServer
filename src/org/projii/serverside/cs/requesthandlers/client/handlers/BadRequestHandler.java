package org.projii.serverside.cs.requesthandlers.client.handlers;

import org.jai.BSON.BSONDocument;
import org.projii.serverside.cs.SessionInfo;

import static org.projii.commons.net.CoordinationServerResponses.ERROR;

public class BadRequestHandler implements ClientRequestHandler {
    @Override
    public BSONDocument handle(BSONDocument request, SessionInfo sessionInfo) {
        return new BSONDocument().add("type", ERROR);
    }
}
