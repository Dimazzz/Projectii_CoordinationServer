package org.projii.serverside.cs;

import org.jai.BSON.BSONDecoder;
import org.jai.BSON.BSONDocument;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import java.nio.ByteBuffer;

class ProtocolDecoder extends FrameDecoder {
    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer) throws Exception {
        if (channelBuffer.readableBytes() < 4) {
            return null;
        }

        int sequenceLength = channelBuffer.readInt();
        ByteBuffer bb = ByteBuffer.allocate(sequenceLength);
        bb.putInt(sequenceLength);

        if (channelBuffer.readableBytes() < sequenceLength - 4) {
            channelBuffer.resetReaderIndex();
            return null;
        }

        channelBuffer.readBytes(bb);
        bb.flip();

        return BSONDecoder.decode(bb);
    }

}
