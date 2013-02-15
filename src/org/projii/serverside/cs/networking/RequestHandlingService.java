package org.projii.serverside.cs.networking;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.projii.commons.TimeLogger;
import org.projii.commons.net.Request;
import org.projii.serverside.cs.ExecutionLayer;

public class RequestHandlingService extends SimpleChannelUpstreamHandler {

    private final ExecutionLayer executionLayer;

    public RequestHandlingService(ExecutionLayer executionLayer) {
        this.executionLayer = executionLayer;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        TimeLogger.d("Request handling service : ", "I've got a message");
        Request request = (Request) e.getMessage();
        executionLayer.exec(request, ctx.getChannel());
        TimeLogger.d("Request handling service : ", "I've send a message to executors");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
    }
}
