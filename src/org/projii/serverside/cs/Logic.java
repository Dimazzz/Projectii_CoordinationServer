package org.projii.serverside.cs;

import org.jai.BSON.BSONDocument;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import java.nio.ByteBuffer;

import static org.projii.serverside.cs.CoordinationServerRequests.*;
import static org.projii.serverside.cs.CoordinationServerResponses.AUTHORIZATION_RESULT;
import static org.projii.serverside.cs.CoordinationServerResponses.ERROR;

class Logic extends SimpleChannelHandler {

    private final SessionsManager sessionsManager;
    private long userid;

    public Logic(SessionsManager sessionsManager) {
        System.out.println("sessionsManager = [" + sessionsManager + "]");
        this.userid = -1;
        this.sessionsManager = sessionsManager;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("Logic: message received");
        System.out.println("Userid: " + userid);
        BSONDocument requestDocument = (BSONDocument) e.getMessage();
        BSONDocument responseDocument = processRequest(requestDocument);

        if (responseDocument == null) {
            ctx.getChannel().close();
        } else {
            ctx.getChannel().write(responseDocument);
        }
    }

    private BSONDocument processRequest(BSONDocument document) {
        int requestType = (Integer) document.get("type");
        switch (requestType) {
            case AUTHORIZATION:
                System.out.println("[Authorization]");

                String username = (String) document.get("login");
                ByteBuffer password = (ByteBuffer) document.get("password");

                if (sessionsManager.isAuthorized(username)) {
                    return new BSONDocument().add("type", AUTHORIZATION_RESULT).add("result", true);
                }

                if (sessionsManager.logIn(username, password)) {
                    this.userid = sessionsManager.getUserId(username);
                    return new BSONDocument().add("type", AUTHORIZATION_RESULT).add("result", true);
                }

                return new BSONDocument().add("type", AUTHORIZATION_RESULT).add("result", false);
            case GET_MY_SHIPS_FULL:
            case GET_MY_SHIPS:
                break;
            case GET_MY_SHIP_FULL:
            case GET_MY_SHIP:
                break;

            case GET_GAMES:
            case JOIN_GAME:
                break;
            case LOGOUT:
                System.out.println("[Logout]");
                if (userid != -1) {
                    sessionsManager.logOut(userid);
                    userid = -1;
                }

                return null;
        }
        System.out.println("[UNK]");
        return new BSONDocument().add("type", ERROR);
    }
}