package org.projii.serverside.cs.networking;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.ServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.projii.serverside.cs.DataStorage;
import org.projii.serverside.cs.GamesManager;
import org.projii.serverside.cs.SessionsManager;
import org.projii.serverside.cs.networking.client.ClientsPipelineFactory;
import org.projii.serverside.cs.networking.gameserver.GameServersPipelineFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Networking {
    private final int clientsIncomingPort;
    private final int gameServersIncomingPort;
    private final ChannelPipelineFactory clientsPipleneFactory;
    private final ChannelPipelineFactory gameServersPipleneFactory;

    public Networking(int clientsIncomingPort, int gameServersIncomingPort, DataStorage dataStorage, SessionsManager sessionManager, GamesManager gamesManager) {
        this.clientsIncomingPort = clientsIncomingPort;
        this.gameServersIncomingPort = gameServersIncomingPort;
        clientsPipleneFactory = new ClientsPipelineFactory(clientsIncomingPort, sessionManager, dataStorage, gamesManager);
        gameServersPipleneFactory = new GameServersPipelineFactory(gameServersIncomingPort, sessionManager, dataStorage, gamesManager);
    }

    public void run() {
        ExecutorService bossThreadsPool = Executors.newFixedThreadPool(2);
        ExecutorService workersThreadsPool = Executors.newFixedThreadPool(2);

        ServerSocketChannelFactory channelFactory = new NioServerSocketChannelFactory(bossThreadsPool, workersThreadsPool);

        ServerBootstrap gameServersBootstrap = new ServerBootstrap(channelFactory);
        gameServersBootstrap.setPipelineFactory(gameServersPipleneFactory);
        Channel gameServersChannel = gameServersBootstrap.bind(new InetSocketAddress(gameServersIncomingPort));

        ServerBootstrap clientsBootstrap = new ServerBootstrap(channelFactory);
        clientsBootstrap.setPipelineFactory(clientsPipleneFactory);
        Channel clientsChannel = clientsBootstrap.bind(new InetSocketAddress(clientsIncomingPort));


//        ChannelFuture future = channel.close();
//        future.awaitUninterruptibly();
    }

}
