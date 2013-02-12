package org.projii.serverside.cs;

import org.projii.commons.net.CoordinationServerRequests;
import org.projii.serverside.cs.interaction.Request;
import org.projii.serverside.cs.interaction.client.RequestHandler;
import org.projii.serverside.cs.interaction.client.handlers.AuthorizationRequestHandler;
import org.projii.serverside.cs.interaction.client.requests.AuthorizationRequest;
import org.projii.serverside.cs.networking.Networking;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class CoordinationServer {
    public static void main(String[] args) {
        int clientsIncomingPort = 6666;
        int gameServerIncomingPort = 6667;
        String databaseAddress = "192.168.56.100";
        String databaseName = "projectiidb";
        String databaseLogin = "projectii";
        String databasePassword = "p5o73Ct3";
        BasicDataStorage dataStorage = new BasicDataStorage(databaseAddress, databaseName, databaseLogin, databasePassword);
        try {
            dataStorage.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        Map<Integer, Class> gameServerRequestsCorrespondenceTable = new HashMap<>();
        Map<Integer, Class> clientRequestsCorrespondenceTable = new HashMap<>();
        clientRequestsCorrespondenceTable.put(CoordinationServerRequests.AUTHORIZATION, AuthorizationRequest.class);
        Map<Class<? extends Request>, RequestHandler> handlers = new HashMap<>();
        handlers.put(
                AuthorizationRequest.class,
                new AuthorizationRequestHandler(new ClientsSessionsManager(dataStorage)));
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
