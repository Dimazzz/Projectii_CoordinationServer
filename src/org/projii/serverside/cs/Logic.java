package org.projii.serverside.cs;

import org.jai.BSON.BSONDocument;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.projii.commons.TimeLogger;
import org.projii.serverside.cs.requesthandlers.*;

import static org.projii.commons.net.CoordinationServerRequests.*;

class Logic extends SimpleChannelHandler {

    private final SessionsManager sessionsManager;
    private final DataStorage dataStorage;
    private final GamesManager gamesManager;
    private SessionInfo sessionInfo;
    private LogoutRequestHandler logoutRequestHandler;
    private GetGamesRequestHandler getGamesRequestHandler;
    private BadRequestHandler badRequestHandler;
    private GetMysSpaceshipsRequestHandler getMySpaceshipsHandler;
    private AuthorizationRequestHandler authorizationRequestHandler;

    Logic(SessionsManager sessionsManager, DataStorage dataStorage, GamesManager gamesManager) {
        this.sessionsManager = sessionsManager;
        this.dataStorage = dataStorage;
        this.gamesManager = gamesManager;

        this.sessionInfo = null;
        this.logoutRequestHandler = new LogoutRequestHandler();
        this.getGamesRequestHandler = new GetGamesRequestHandler(gamesManager);
        this.badRequestHandler = new BadRequestHandler();
        this.getMySpaceshipsHandler = new GetMysSpaceshipsRequestHandler(dataStorage);
        this.authorizationRequestHandler = new AuthorizationRequestHandler(sessionsManager);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

        TimeLogger.d("Logic: ", "Message received");
        BSONDocument requestDocument = (BSONDocument) e.getMessage();
        TimeLogger.d("Logic: ", "I'v got request document");
        BSONDocument responseDocument = processRequest(requestDocument);
        TimeLogger.d("Logic: ", "I'v proceed request");

        if (responseDocument == null) {
            ctx.getChannel().close();
            TimeLogger.d("Logic: ", "I'v closed connection");
        } else {
            TimeLogger.d("Logic: ", "I'm sending response down stream");
            ctx.getChannel().write(responseDocument);
            TimeLogger.d("Logic: ", "Response has been send");
        }
    }

    private BSONDocument processRequest(BSONDocument request) {
        int requestType = (Integer) request.get("type");
        TimeLogger.d("Logic: ", "I'v start request processing for", "client #", "" + (sessionInfo == null ? -1 : sessionInfo.userId));

        if (sessionInfo == null && requestType == AUTHORIZATION) {
            authorizationRequestHandler = new AuthorizationRequestHandler(sessionsManager);
            BSONDocument response = authorizationRequestHandler.handle(request, null);
            if ((boolean) response.get("result")) {
                sessionInfo = sessionsManager.getSessionInfo((int) response.get("sessionId"));
            }
            return response;
        } else {
            RequestHandler requestHandler = badRequestHandler;
            switch (requestType) {
                case GET_MY_SHIPS:
                case GET_MY_SHIPS_FULL:
                case GET_MY_SHIP:
                case GET_MY_SHIP_FULL:
                    requestHandler = getMySpaceshipsHandler;
                    break;
                case GET_GAMES:
                    requestHandler = getGamesRequestHandler;
                    break;
                case JOIN_GAME:
                    break;
                case LOG_OUT:
                    this.sessionInfo = null;
                    requestHandler = logoutRequestHandler;
            }


            return requestHandler.handle(request, sessionInfo);
        }

    }
}