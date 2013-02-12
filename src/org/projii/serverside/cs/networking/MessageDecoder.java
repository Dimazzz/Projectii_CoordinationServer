package org.projii.serverside.cs.networking;

import org.jai.BSON.BSONDocument;
import org.jai.BSON.BSONSerializer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.projii.commons.TimeLogger;
import org.projii.serverside.cs.interaction.Request;

import java.util.Map;

public class MessageDecoder extends SimpleChannelUpstreamHandler {

    private final Map<Integer, Class> correspondenceTable;

    public MessageDecoder(Map<Integer, Class> correspondenceTable) {
        this.correspondenceTable = correspondenceTable;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        TimeLogger.d("Message decoder: ", "Received a message");
        BSONDocument requestDocument = (BSONDocument) e.getMessage();
        Integer type = (Integer) requestDocument.get("type");
        TimeLogger.d("Message decoder: ", "Message type = " + type);
        Class requestClass = correspondenceTable.get(type);
        Request request = (Request) BSONSerializer.deserialize(requestClass, requestDocument);
        TimeLogger.d("Message decoder: ", "Sending a message upstream");
        Channels.fireMessageReceived(ctx, request);
    }

}