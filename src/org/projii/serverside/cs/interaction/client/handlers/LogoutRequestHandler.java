package org.projii.serverside.cs.interaction.client.handlers;

import org.jboss.netty.channel.Channel;
import org.projii.commons.net.InteractionMessage;
import org.projii.serverside.commons.RequestHandler;
import org.projii.serverside.cs.SessionsManager;

public class LogoutRequestHandler implements RequestHandler {

    private final SessionsManager sessionsManager;

    public LogoutRequestHandler(SessionsManager sessionsManager) {
        this.sessionsManager = sessionsManager;
    }

    @Override
    public void handle(InteractionMessage request, Channel channel) {
        sessionsManager.destroySession(channel.getId());
        channel.close();
    }
}
