package org.projii.serverside.cs.networking;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.projii.serverside.cs.DataStorage;
import org.projii.serverside.cs.GamesManager;
import org.projii.serverside.cs.SessionsManager;
import org.projii.serverside.cs.networking.channelHandlers.ProtocolDecoder;
import org.projii.serverside.cs.networking.channelHandlers.ProtocolEncoder;

public class GameServersPipelineFactory implements ChannelPipelineFactory {

    private final int gameServersIncomingPort;
    private final SessionsManager sessionManager;
    private final DataStorage dataStorage;
    private final GamesManager gamesManager;

    public GameServersPipelineFactory(int gameServersIncomingPort, SessionsManager sessionManager, DataStorage dataStorage, GamesManager gamesManager) {
        this.gameServersIncomingPort = gameServersIncomingPort;
        this.sessionManager = sessionManager;
        this.dataStorage = dataStorage;
        this.gamesManager = gamesManager;
    }


    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        channelPipeline.addLast("Protocol decoder", new ProtocolDecoder());
        channelPipeline.addLast("Protocol encoder", new ProtocolEncoder());
        return channelPipeline;
    }
}
