package org.projii.serverside.cs;

import org.projii.commons.spaceship.Spaceship;
import org.projii.commons.spaceship.SpaceshipModel;
import org.projii.commons.spaceship.equipment.EnergyGenerator;
import org.projii.commons.spaceship.equipment.EnergyShield;
import org.projii.commons.spaceship.equipment.SpaceshipEngine;
import org.projii.commons.spaceship.weapon.Weapon;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class BasicDataStorage implements DataStorage {

    private String connectionURL;
    private String host;
    private String database;
    private String username;
    private String password;
    private Connection connection;

    private HashMap<Integer, SpaceshipModel> spaceshipModels;
    private HashMap<Integer, EnergyGenerator> energyGenerators;
    private HashMap<Integer, EnergyShield> energyShields;
    private HashMap<Integer, SpaceshipEngine> engines;

    private PreparedStatement ps_selectUserWhereId;
    private PreparedStatement ps_selectUserWhereEmail;
    private PreparedStatement ps_selectSpaceshipWhereOwner;
    private PreparedStatement ps_selectEngineModelWhereId;

    public BasicDataStorage(String host, String database, String username, String password) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.connectionURL = new StringBuilder("jdbc:postgresql://").append(host).append('/').append(database).toString();
    }

    public boolean connect() throws ClassNotFoundException, SQLException {

        if (!isConnected()) {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(connectionURL, username, password);
        }

        return isConnected();
    }

    public void disconnect() {
        if (!isConnected()) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        try {
            return !(connection == null || connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void loadSpaceshipModels() {
        if (!isConnected()) {
            return;
        }
        try {
            spaceshipModels = new HashMap<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM spaceship_models");
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int weaponAmount = resultSet.getInt("id");
                int health = resultSet.getInt("hp");
                int armor = resultSet.getInt("hp");
                String modelName = resultSet.getString("name");
                int width = resultSet.getInt("hp");
                int length = resultSet.getInt("hp");
                int master = resultSet.getInt("master");

                spaceshipModels.put(id, new SpaceshipModel(modelName, id, health, armor, weaponAmount, length, width));
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void loadEngineModels() {
        if (!isConnected()) {
            return;
        }
        try {
            engines = new HashMap<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM engine_models");
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                int maxSpeed = resultSet.getInt("maxSpeed");
                int acceleration = resultSet.getInt("acceleration");
                int maneuverability = resultSet.getInt("maneuverability");
                String name = resultSet.getString("name");

                engines.put(id, new SpaceshipEngine(id, maxSpeed, acceleration, maneuverability, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void loadEnergyGeneratorModels() {
        if (!isConnected()) {
            return;
        }
        try {
            energyGenerators = new HashMap<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM energy_generators");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int maxEnergyLevel = resultSet.getInt("maxEnergyLevel");
                int regenerationSpeed = resultSet.getInt("regenerationSpeed");
                String name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void loadEneryShieldModels() {
        if (!isConnected()) {
            return;
        }
        try {
            energyShields = new HashMap<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM energy_shield_models");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int maxHPLevel = resultSet.getInt("maxHPLevel");
                int regenerationSpeed = resultSet.getInt("regenerationSpeed");
                int regenerationDelay = resultSet.getInt("regenerationDelay");
                String name = resultSet.getString("name");

            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    private SpaceshipEngine getEngineModel(int modelid) {
        if (!isConnected()) {
            return null;
        }

        SpaceshipEngine engine = null;

        if (engines != null) {
            engine = engines.get(modelid);
        } else {
            engines = new HashMap<>();
        }

        if (engine == null) {
            if (ps_selectEngineModelWhereId == null) {
                try {
                    ps_selectEngineModelWhereId = connection.prepareStatement("SELECT * FROM engine_models WHERE id = ?");
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            try {
                ps_selectEngineModelWhereId.setInt(1, modelid);
                ResultSet resultSet = ps_selectEngineModelWhereId.executeQuery();
                resultSet.next();

                int id = resultSet.getInt("id");
                int maxSpeed = resultSet.getInt("maxSpeed");
                int acceleration = resultSet.getInt("acceleration");
                int maneuverability = resultSet.getInt("maneuverability");
                String name = resultSet.getString("name");

                engine = new SpaceshipEngine(id, maxSpeed, acceleration, maneuverability, name);
                engines.put(id, engine);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return engine;
    }


    @Override
    public UserInfo getUserInfo(int userid) {
        try {
            if (ps_selectUserWhereId == null) {
                ps_selectUserWhereId = connection.prepareStatement("SELECT * FROM gamers WHERE id = ?");
            }
            ps_selectUserWhereId.setInt(1, userid);
            ResultSet resultSet = ps_selectUserWhereId.executeQuery();
            return getUserInfo(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public UserInfo getUserInfo(String username) {
        try {
            if (ps_selectUserWhereEmail == null) {
                ps_selectUserWhereEmail = connection.prepareStatement("SELECT * FROM gamers WHERE email = '?'");
            }
            ps_selectUserWhereEmail.setString(1, username);
            ResultSet resultSet = ps_selectUserWhereEmail.executeQuery();
            return getUserInfo(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    private UserInfo getUserInfo(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String nickname = resultSet.getString("nickname");
            int experience = resultSet.getInt("experience");

            return new UserInfo(id, email, password, nickname, experience);
        }
        return null;
    }


    @Override
    public List<Spaceship> getUserSpaceships(int userid) {
        if (!isConnected()) {
            return null;
        }

        if (ps_selectSpaceshipWhereOwner == null) {
            try {
                ps_selectSpaceshipWhereOwner = connection.prepareStatement("SELECT * FROM spaceship WHERE owner = ?");
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        try {
            ps_selectSpaceshipWhereOwner.setInt(1, userid);
            ResultSet resultSet = ps_selectSpaceshipWhereOwner.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int owner = resultSet.getInt(2);
                int modelid = resultSet.getInt(3);
                int engineid = resultSet.getInt(4);
                int energyGeneratorid = resultSet.getInt(5);
                int energyShieldid = resultSet.getInt(6);
                int firstWeapon = resultSet.getInt(7);
                int secondWeapon = resultSet.getInt(8);
                int thirdWeapon = resultSet.getInt(9);


                SpaceshipModel model = spaceshipModels.get(modelid);
                EnergyGenerator generator = energyGenerators.get(energyGeneratorid);
                EnergyShield energyShield = energyShields.get(energyShieldid);
                SpaceshipEngine engine = engines.get(engineid);


                Weapon[] weapons = {};
                new Spaceship(model, generator, engine, energyShield, weapons);

            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public SpaceshipModel getSpaceshipModel(int modelid) {
        if (!isConnected()) {
            return null;
        }

        if (spaceshipModels == null) {
            loadSpaceshipModels();
        }
        return spaceshipModels.get(modelid);
    }

    @Override
    public List<GameInfo> getGames() {
        if (!isConnected()) {
            return null;
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
