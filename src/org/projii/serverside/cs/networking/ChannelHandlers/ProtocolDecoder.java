package org.projii.serverside.cs.networking.ChannelHandlers;

import org.jai.BSON.BSONDecoder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.projii.commons.TimeLogger;

import java.nio.ByteBuffer;

public class ProtocolDecoder extends FrameDecoder {
    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer) throws Exception {
        TimeLogger.d("ProtocolDecoder: ", "I'v started decoding a message");
        if (channelBuffer.readableBytes() < 4) {
            TimeLogger.d("ProtocolDecoder: ", "Not enough data");
            return null;
        }

        int sequenceLength = channelBuffer.readInt();
        TimeLogger.d("ProtocolDecoder: ", "Allocating a byte buffer");
        ByteBuffer bb = ByteBuffer.allocate(sequenceLength);
        TimeLogger.d("ProtocolDecoder: ", "I'v allocated byte buffer");
        bb.putInt(sequenceLength);

        if (channelBuffer.readableBytes() < sequenceLength - 4) {
            TimeLogger.d("ProtocolDecoder: ", "Not enough data");
            channelBuffer.resetReaderIndex();
            return null;
        }

        channelBuffer.readBytes(bb);
        bb.flip();

        TimeLogger.d("ProtocolDecoder: ", "I'v decoded a message");
        return BSONDecoder.decode(bb);
    }

}
