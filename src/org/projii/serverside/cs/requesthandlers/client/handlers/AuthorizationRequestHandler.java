package org.projii.serverside.cs.requesthandlers.client.handlers;

import org.jai.BSON.BSONDocument;
import org.projii.serverside.cs.SessionInfo;
import org.projii.serverside.cs.SessionsManager;

import static org.projii.commons.net.CoordinationServerResponses.AUTHORIZATION_RESULT;

public class AuthorizationRequestHandler implements ClientRequestHandler {

    private SessionsManager sessionsManager;

    public AuthorizationRequestHandler(SessionsManager sessionsManager) {
        this.sessionsManager = sessionsManager;
    }

    @Override
    public BSONDocument handle(BSONDocument request, SessionInfo sessionInfo) {
        String login = (String) request.get("login");
        String password = (String) request.get("password");

        if (sessionsManager.isAuthorized(login)) {
            return new BSONDocument().
                    add("type", AUTHORIZATION_RESULT).
                    add("result", true).
                    add("sessionId", sessionsManager.getSessionId(login));
        }

        sessionInfo = sessionsManager.logIn(login, password);
        if (sessionInfo != null) {
            return new BSONDocument().
                    add("type", AUTHORIZATION_RESULT).
                    add("result", true).
                    add("sessionId", sessionInfo.getSessionId());
        }

        return new BSONDocument().add("type", AUTHORIZATION_RESULT).add("result", false);
    }
}
