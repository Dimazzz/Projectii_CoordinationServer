package org.projii.serverside.cs.networking;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.ServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.projii.serverside.cs.ExecutionLayer;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Networking {
    private final int clientsIncomingPort;
    private final int gameServersIncomingPort;
    private final ChannelPipelineFactory clientsPipelineFactory;
    private final ChannelPipelineFactory gameServersPipelineFactory;

    public Networking(int clientsIncomingPort, int gameServerIncomingPort,
                      Map<Integer, Class> clientRequestsCorrespondenceTable,
                      Map<Integer, Class> gameServerRequestsCorrespondenceTable,
                      ExecutionLayer executionLayer) {

        this.clientsIncomingPort = clientsIncomingPort;
        this.gameServersIncomingPort = gameServerIncomingPort;
        clientsPipelineFactory = new GeneralizedPipelineFactory(clientsIncomingPort, executionLayer, clientRequestsCorrespondenceTable);
        gameServersPipelineFactory = new GeneralizedPipelineFactory(clientsIncomingPort, executionLayer, gameServerRequestsCorrespondenceTable);
    }

    public void run() {
        ExecutorService bossThreadsPool = Executors.newFixedThreadPool(2);
        ExecutorService workersThreadsPool = Executors.newFixedThreadPool(2);
        ServerSocketChannelFactory channelFactory = new NioServerSocketChannelFactory(bossThreadsPool, workersThreadsPool);
        Channel gameServersChannel = bind(channelFactory, gameServersPipelineFactory, gameServersIncomingPort);
        Channel clientsChannel = bind(channelFactory, clientsPipelineFactory, clientsIncomingPort);
//        ChannelFuture future = channel.close();
//        future.awaitUninterruptibly();
    }

    private Channel bind(ChannelFactory channelFactory, ChannelPipelineFactory pipelineFactory, int incomingPort) {
        ServerBootstrap serverBootstrap = new ServerBootstrap(channelFactory);
        serverBootstrap.setPipelineFactory(pipelineFactory);
        return serverBootstrap.bind(new InetSocketAddress(incomingPort));
    }

}
