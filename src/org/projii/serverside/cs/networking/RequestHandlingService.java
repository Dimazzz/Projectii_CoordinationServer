package org.projii.serverside.cs.networking;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.projii.serverside.cs.requesthandlers.AbstractRequest;
import org.projii.serverside.cs.ExecutionLayer;

public class RequestHandlingService extends SimpleChannelHandler {

    private final ExecutionLayer executionLayer;
    private final int id;

    public RequestHandlingService(ExecutionLayer executionLayer, int id) {
        this.executionLayer = executionLayer;
        this.id = id;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        AbstractRequest request = (AbstractRequest) e.getMessage();
        executionLayer.exec(request, id);
    }
}
