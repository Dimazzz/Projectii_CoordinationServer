package org.projii.serverside.cs.networking;

import org.jai.BSON.BSONDocument;
import org.jai.BSON.BSONSerializer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.projii.serverside.commons.crossServerRequests.CrossServerRequest;

import java.util.Map;

public class MessageDecoder extends SimpleChannelHandler {

    private final Map<Integer, Class> correspondenceTable;

    public MessageDecoder(Map<Integer, Class> correspondenceTable) {
        this.correspondenceTable = correspondenceTable;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        BSONDocument requestDocument = (BSONDocument) e.getMessage();
        Integer type = (Integer) requestDocument.get("type");
        Class requestClass = correspondenceTable.get(type);
        CrossServerRequest request = (CrossServerRequest) BSONSerializer.deserialize(requestClass, requestDocument);
    }

}