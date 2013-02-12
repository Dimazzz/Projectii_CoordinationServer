package org.projii.serverside.cs.interaction.client;

import org.jboss.netty.channel.Channel;
import org.projii.serverside.cs.interaction.Request;

public interface RequestHandler {
    void handle(Request request, Channel channel);
}
