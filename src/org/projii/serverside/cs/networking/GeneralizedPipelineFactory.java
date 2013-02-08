package org.projii.serverside.cs.networking;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.projii.serverside.cs.ExecutionLayer;

import java.util.Map;

public class GeneralizedPipelineFactory implements ChannelPipelineFactory {


    private final int incomingPort;
    private final ExecutionLayer executionLayer;
    private final Map<Integer, Class> correspondenceTable;

    public GeneralizedPipelineFactory(int incomingPort, ExecutionLayer executionLayer, Map<Integer, Class> correspondenceTable) {
        this.incomingPort = incomingPort;
        this.executionLayer = executionLayer;
        this.correspondenceTable = correspondenceTable;
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = Channels.pipeline();
        channelPipeline.addLast("Protocol decoder", new ProtocolDecoder());
        channelPipeline.addLast("Message decoder", new MessageDecoder(correspondenceTable));
        channelPipeline.addLast("Request handling service", new RequestHandlingService(executionLayer, 0));
        channelPipeline.addLast("Message encoder", new MessageEncoder());
        channelPipeline.addLast("Protocol encoder", new ProtocolEncoder());
        return channelPipeline;
    }
}
