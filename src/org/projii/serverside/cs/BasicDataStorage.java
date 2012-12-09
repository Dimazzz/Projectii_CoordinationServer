package org.projii.serverside.cs;

import org.projii.commons.space.Map;
import org.projii.commons.spaceship.Spaceship;

import java.util.List;

public class BasicDataStorage {

    String databaseURL = "jdbc:postgresql://host/database";


    public List<Spaceship> getUserShips(int userid) {
        return null;
    }

    public List<Map> getGames() {
        return null;
    }

    public Map getGame(long id) {
        return null;
    }

}
