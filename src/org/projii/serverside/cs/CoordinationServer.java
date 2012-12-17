package org.projii.serverside.cs;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class CoordinationServer {

    private final int incomingPort;
    private final SessionsManager sessionManager;
    private final BasicDataStorage dataStorage;
    private final GamesManager gamesManager;

    private CoordinationServer(int incomingPort) {
        this.incomingPort = incomingPort;
        this.dataStorage = new BasicDataStorage("192.168.56.100", "projectiidb", "projectii", "p5o73Ct3");
        this.sessionManager = new SessionsManager(dataStorage);
        this.gamesManager = new GamesManager();
    }

    private void run() {
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline channelPipeline = Channels.pipeline();

                channelPipeline.addLast("Protocol decoder", new ProtocolDecoder());

                dataStorage.connect();
                channelPipeline.addLast("Logic", new Logic(sessionManager, dataStorage, gamesManager));
                channelPipeline.addLast("Protocol encoder", new ProtocolEncoder());
                return channelPipeline;
            }
        });


        bootstrap.bind(new InetSocketAddress(incomingPort));
    }


    public static void main(String... args) {
        int port = 6666;

        CoordinationServer coordinationServer = new CoordinationServer(port);
        coordinationServer.run();
    }

}
