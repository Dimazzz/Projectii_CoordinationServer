package org.projii.serverside.cs.requesthandlers;

import org.jai.BSON.BSONDocument;
import org.projii.serverside.cs.SessionInfo;

public interface RequestHandler {
    BSONDocument handle(BSONDocument request, SessionInfo sessionInfo);
}
