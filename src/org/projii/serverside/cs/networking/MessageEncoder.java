package org.projii.serverside.cs.networking;

import org.jai.BSON.BSONDocument;
import org.jai.BSON.BSONSerializer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelDownstreamHandler;
import org.projii.commons.TimeLogger;
import org.projii.serverside.cs.interaction.Response;

public class MessageEncoder extends SimpleChannelDownstreamHandler {
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        TimeLogger.d("MessageEncoder: ", "I'v got a message");
        Response message = (Response) e.getMessage();
        BSONDocument marshaledMessage = BSONSerializer.serialize(message);
        Channels.write(ctx, e.getFuture(), marshaledMessage);
        TimeLogger.d("MessageEncoder: ", "I'v sent it downstream");
    }


}
