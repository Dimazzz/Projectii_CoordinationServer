package org.projii.serverside.cs;

import org.jai.BSON.BSONDocument;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.projii.serverside.cs.requesthandlers.*;

import static org.projii.serverside.cs.CoordinationServerRequests.*;

class Logic extends SimpleChannelHandler {

    private final SessionsManager sessionsManager;
    private final DataStorage dataStorage;
    private final GamesManager gamesManager;
    private SessionInfo sessionInfo;
    private LogoutRequestHandler logoutRequestHandler;
    private GetGamesRequestHandler getGamesRequestHandler;
    private BadRequestHandler badRequsetHandler;
    private GetMysSpaceshipsRequestHandler getMySpaceshipsHandler;

    Logic(SessionsManager sessionsManager, DataStorage dataStorage, GamesManager gamesManager) {
        this.sessionsManager = sessionsManager;
        this.dataStorage = dataStorage;
        this.gamesManager = gamesManager;

        this.sessionInfo = null;
        this.logoutRequestHandler = new LogoutRequestHandler();
        this.getGamesRequestHandler = new GetGamesRequestHandler(gamesManager);
        this.badRequsetHandler = new BadRequestHandler();
        this.getMySpaceshipsHandler = new GetMysSpaceshipsRequestHandler(dataStorage);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        BSONDocument requestDocument = (BSONDocument) e.getMessage();
        BSONDocument responseDocument = processRequest(requestDocument);

        if (responseDocument == null) {
            ctx.getChannel().close();
        } else {
            ctx.getChannel().write(responseDocument);
        }
    }

    private BSONDocument processRequest(BSONDocument request) {
        int requestType = (Integer) request.get("type");

        if (sessionInfo == null) {
            if (requestType == AUTHORIZATION) {
                AuthorizationRequestHandler authorizationRequestHandler = new AuthorizationRequestHandler(sessionsManager);
                BSONDocument response = authorizationRequestHandler.handle(request, null);
                if ((boolean) response.get("result")) {
                    sessionInfo = sessionsManager.getSessionInfo((int) response.get("sessionId"));
                }
                return response;
            }

        } else {
            switch (requestType) {
                case GET_MY_SHIPS:
                case GET_MY_SHIPS_FULL:
                case GET_MY_SHIP:
                case GET_MY_SHIP_FULL:
                    return getMySpaceshipsHandler.handle(request, sessionInfo);
                case GET_GAMES:
                    return getGamesRequestHandler.handle(request, sessionInfo);
                case JOIN_GAME:
                    break;
                case LOGOUT:
                    return logoutRequestHandler.handle(request, sessionInfo);
            }
        }

        return badRequsetHandler.handle(request, sessionInfo);
    }
}