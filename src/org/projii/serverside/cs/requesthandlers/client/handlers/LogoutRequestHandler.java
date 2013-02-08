package org.projii.serverside.cs.requesthandlers.client.handlers;

import org.jai.BSON.BSONDocument;
import org.projii.serverside.cs.SessionInfo;

public class LogoutRequestHandler implements ClientRequestHandler {
    @Override
    public BSONDocument handle(BSONDocument request, SessionInfo sessionInfo) {
        return null;
    }
}
