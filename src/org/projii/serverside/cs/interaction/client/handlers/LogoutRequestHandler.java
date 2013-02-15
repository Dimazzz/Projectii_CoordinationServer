package org.projii.serverside.cs.interaction.client.handlers;

import org.jboss.netty.channel.Channel;
import org.projii.commons.net.Request;
import org.projii.serverside.cs.SessionsManager;
import org.projii.serverside.cs.interaction.client.RequestHandler;

public class LogoutRequestHandler implements RequestHandler {

    private final SessionsManager sessionsManager;

    public LogoutRequestHandler(SessionsManager sessionsManager) {
        this.sessionsManager = sessionsManager;
    }

    @Override
    public void handle(Request request, Channel channel) {
        sessionsManager.destroySession(channel.getId());
        channel.close();
    }
}
