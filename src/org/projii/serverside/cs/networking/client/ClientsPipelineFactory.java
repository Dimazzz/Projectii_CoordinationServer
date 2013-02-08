package org.projii.serverside.cs.networking.client;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.projii.serverside.cs.DataStorage;
import org.projii.serverside.cs.GamesManager;
import org.projii.serverside.cs.SessionsManager;
import org.projii.serverside.cs.networking.ProtocolDecoder;
import org.projii.serverside.cs.networking.ProtocolEncoder;

public class ClientsPipelineFactory implements ChannelPipelineFactory {

    private final SessionsManager sessionManager;
    private final DataStorage dataStorage;
    private final GamesManager gamesManager;

    public ClientsPipelineFactory(int clientsIncomingPort, SessionsManager sessionManager, DataStorage dataStorage, GamesManager gamesManager) {
        this.sessionManager = sessionManager;
        this.dataStorage = dataStorage;
        this.gamesManager = gamesManager;
    }


    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        channelPipeline.addLast("Protocol decoder", new ProtocolDecoder());
        channelPipeline.addLast("Logic", new ClientServiceLogic(sessionManager, dataStorage, gamesManager));
        channelPipeline.addLast("Protocol encoder", new ProtocolEncoder());
        return channelPipeline;
    }
}
