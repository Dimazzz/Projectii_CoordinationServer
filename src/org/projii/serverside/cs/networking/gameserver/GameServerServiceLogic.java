package org.projii.serverside.cs.networking.gameserver;

import org.jai.BSON.BSONDocument;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import static org.projii.serverside.commons.CrossServerRequests.*;

public class GameServerServiceLogic extends SimpleChannelHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

        BSONDocument request = (BSONDocument) e;
        switch ((int) request.get("type")) {
            case AUTHORIZATION:
                break;
            case MY_INFO:
                break;
            case CLIENT_CAME:
                break;
            case CLIENT_LEFT:
                break;
            case LOG_OUT:
                break;
        }

        // send to clients
        // send info about clients to server
        // logout
    }


}
