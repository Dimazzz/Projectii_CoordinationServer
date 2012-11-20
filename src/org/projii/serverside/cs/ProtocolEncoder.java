package org.projii.serverside.cs;

import org.jai.BSON.BSONDocument;
import org.jai.BSON.BSONEncoder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import java.nio.ByteBuffer;

class ProtocolEncoder extends SimpleChannelHandler {
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        BSONDocument document = (BSONDocument) e.getMessage();
        ByteBuffer buffer = BSONEncoder.encode(document);
        Channels.write(ctx, e.getFuture(), org.jboss.netty.buffer.ChannelBuffers.wrappedBuffer(buffer));
    }
}
