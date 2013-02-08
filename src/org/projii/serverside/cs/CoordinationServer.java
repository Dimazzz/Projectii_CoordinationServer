package org.projii.serverside.cs;

import org.projii.serverside.cs.networking.Networking;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class CoordinationServer {
    public static void main(String[] args) {
        //TODO: Should me moved to external file
        int clientsIncomingPort = 6666;
        int gameServerIncomingPort = 6667;
        String databaseAdress = "192.168.56.100";
        String databaseName = "projectiidb";
        String databaseLogin = "projectii";
        String databasePassword = "p5o73Ct3";

        BasicDataStorage dataStorage = new BasicDataStorage(databaseAdress, databaseName, databaseLogin, databasePassword);
        try {
            dataStorage.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        ExecutionLayer executionLayer = new ExecutionLayer(Executors.newFixedThreadPool(4), new SessionsManager(dataStorage), dataStorage, new GamesManager());
        Map<Integer, Class> clientRequestsCorrespondenceTable = new HashMap<>();
        Map<Integer, Class> gameServerRequestsCorrespondenceTable = new HashMap<>();
        Networking networking = new Networking(clientsIncomingPort, gameServerIncomingPort, clientRequestsCorrespondenceTable, gameServerRequestsCorrespondenceTable, executionLayer);
        networking.run();
    }
}
