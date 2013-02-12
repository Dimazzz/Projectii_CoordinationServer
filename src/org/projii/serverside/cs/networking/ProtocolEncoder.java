package org.projii.serverside.cs.networking;

import org.jai.BSON.BSONDocument;
import org.jai.BSON.BSONEncoder;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelDownstreamHandler;
import org.projii.commons.TimeLogger;

import java.nio.ByteBuffer;

public class ProtocolEncoder extends SimpleChannelDownstreamHandler {
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        TimeLogger.d("ProtocolEncoder: ", "I'v got a message");
        BSONDocument document = (BSONDocument) e.getMessage();
        ByteBuffer buffer = BSONEncoder.encode(document);
        Channels.write(ctx, e.getFuture(), ChannelBuffers.wrappedBuffer(buffer));
        TimeLogger.d("ProtocolEncoder: ", "I'v send it to client");
    }
}
