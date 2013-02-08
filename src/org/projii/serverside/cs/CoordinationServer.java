package org.projii.serverside.cs;

import org.projii.serverside.cs.networking.Networking;

import java.sql.SQLException;

public class CoordinationServer {
    public static void main(String[] args) {
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

        Networking networking = new Networking(
                clientsIncomingPort, gameServerIncomingPort, dataStorage, new SessionsManager(dataStorage), new GamesManager());

        networking.run();
    }
}
