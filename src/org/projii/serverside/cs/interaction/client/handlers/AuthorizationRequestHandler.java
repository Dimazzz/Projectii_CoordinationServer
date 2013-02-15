package org.projii.serverside.cs.interaction.client.handlers;

import org.jboss.netty.channel.Channel;
import org.projii.commons.net.Request;
import org.projii.serverside.cs.SessionInfo;
import org.projii.serverside.cs.SessionsManager;
import org.projii.serverside.cs.interaction.client.RequestHandler;
import org.projii.serverside.cs.interaction.client.requests.AuthorizationRequest;
import org.projii.serverside.cs.interaction.client.responses.AuthorizationResponse;

public class AuthorizationRequestHandler implements RequestHandler {

    private final SessionsManager sessionsManager;

    public AuthorizationRequestHandler(SessionsManager sessionsManager) {
        this.sessionsManager = sessionsManager;
    }

    @Override
    public void handle(Request request, Channel channel) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) request;
        SessionInfo sessionInfo = sessionsManager.createSession(authorizationRequest.getLogin(), authorizationRequest.getPassword(), channel.getId());
        AuthorizationResponse response = sessionInfo == null ? new AuthorizationResponse(false, -1) : new AuthorizationResponse(true, sessionInfo.getSessionId());
        channel.write(response);
    }
}
