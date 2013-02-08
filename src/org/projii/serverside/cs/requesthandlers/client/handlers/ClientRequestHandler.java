package org.projii.serverside.cs.requesthandlers.client.handlers;

import org.jai.BSON.BSONDocument;
import org.projii.serverside.cs.SessionInfo;

public interface ClientRequestHandler {
    BSONDocument handle(BSONDocument request, SessionInfo sessionInfo);
}
