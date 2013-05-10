package org.projii.serverside.cs;

import org.projii.commons.net.CoordinationServerRequests;
import org.projii.commons.net.InteractionMessage;
import org.projii.serverside.commons.RequestHandler;
import org.projii.serverside.cs.interaction.client.handlers.AuthorizationRequestHandler;
import org.projii.serverside.cs.interaction.client.handlers.GetGamesRequestHandler;
import org.projii.serverside.cs.interaction.client.handlers.GetMysSpaceshipsRequestHandler;
import org.projii.serverside.cs.interaction.client.handlers.LogoutRequestHandler;
import org.projii.serverside.cs.interaction.client.requests.*;
import org.projii.serverside.cs.networking.Networking;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class CoordinationServer {
    public static void main(String[] args) throws FileNotFoundException {

    	String fileName = "CoordinationServer.txt";
        int clientsIncomingPort;
        int gameServerIncomingPort;
        String databaseAddress;
        String databaseName;
        String databaseLogin;
        String databasePassword;
    	try {
            BufferedReader in = new BufferedReader(new FileReader( new File(fileName).getAbsoluteFile()));
            try {
                    clientsIncomingPort = new Integer(in.readLine());
                    gameServerIncomingPort = new Integer(in.readLine());
                    databaseAddress = in.readLine();
                    databaseName = in.readLine();
                    databaseLogin = in.readLine();
                    databasePassword = in.readLine();
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        BasicDataStorage dataStorage = new BasicDataStorage(databaseAddress, databaseName, databaseLogin, databasePassword);
        try {
            dataStorage.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        Map<Integer, Class> gameServerRequestsCorrespondenceTable = new HashMap<>();
        Map<Integer, Class> clientRequestsCorrespondenceTable = new HashMap<>();
        clientRequestsCorrespondenceTable.put(CoordinationServerRequests.AUTHORIZATION, AuthorizationRequest.class);
        clientRequestsCorrespondenceTable.put(CoordinationServerRequests.LOGOUT, LogoutRequest.class);
        clientRequestsCorrespondenceTable.put(CoordinationServerRequests.GET_GAMES, GetGamesRequest.class);
        clientRequestsCorrespondenceTable.put(CoordinationServerRequests.GET_MY_SHIPS, GetMySpaceshipsRequest.class);
        clientRequestsCorrespondenceTable.put(CoordinationServerRequests.JOIN_GAME, JoinGameRequest.class);

        ClientsSessionsManager clientsSessionsManager = new ClientsSessionsManager(dataStorage);
        Map<Class<? extends InteractionMessage>, RequestHandler> handlers = new HashMap<>();
        handlers.put(
                AuthorizationRequest.class,
                new AuthorizationRequestHandler(clientsSessionsManager));
        handlers.put(
                LogoutRequest.class,
                new LogoutRequestHandler(clientsSessionsManager));
        handlers.put(
                GetGamesRequest.class,
                new GetGamesRequestHandler(new GamesManager()));
        handlers.put(
                GetMySpaceshipsRequest.class,
                new GetMysSpaceshipsRequestHandler(dataStorage, clientsSessionsManager));

        ExecutionLayer executionLayer = new ExecutionLayer(handlers, Executors.newFixedThreadPool(4));
        Networking networking = new Networking(
                clientsIncomingPort,
                gameServerIncomingPort,
                clientRequestsCorrespondenceTable,
                gameServerRequestsCorrespondenceTable,
                executionLayer);
        networking.run();
    }
}
