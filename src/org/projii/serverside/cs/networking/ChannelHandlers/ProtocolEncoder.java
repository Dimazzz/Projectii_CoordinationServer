package org.projii.serverside.cs.networking.ChannelHandlers;

import org.jai.BSON.BSONDocument;
import org.jai.BSON.BSONEncoder;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.projii.commons.TimeLogger;

import java.nio.ByteBuffer;

public class ProtocolEncoder extends SimpleChannelHandler {
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        BSONDocument document = (BSONDocument) e.getMessage();

        TimeLogger.d("ProtocolEncoder: ", "I'v got a message");
        TimeLogger.d("ProtocolEncoder: ", "I'v start encoding it to bytes ");
        ByteBuffer buffer = BSONEncoder.encode(document);
        TimeLogger.d("ProtocolEncoder: ", "I'v finished encoding", "Size of message = " + buffer.array().length, "Now I'm sending");
        Channels.write(ctx, e.getFuture(), org.jboss.netty.buffer.ChannelBuffers.wrappedBuffer(buffer));
        TimeLogger.d("ProtocolEncoder: ", "I'v send it to client");
    }
}
