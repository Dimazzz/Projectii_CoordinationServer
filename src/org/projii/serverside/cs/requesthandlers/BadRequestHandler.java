package org.projii.serverside.cs.requesthandlers;

import org.jai.BSON.BSONDocument;
import org.projii.serverside.cs.SessionInfo;

import static org.projii.commons.net.CoordinationServerResponses.ERROR;

public class BadRequestHandler implements RequestHandler {
    @Override
    public BSONDocument handle(BSONDocument request, SessionInfo sessionInfo) {
        return new BSONDocument().add("type", ERROR);
    }
}
