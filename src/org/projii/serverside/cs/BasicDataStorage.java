package org.projii.serverside.cs;

import org.projii.commons.spaceship.Spaceship;
import org.projii.commons.spaceship.SpaceshipModel;
import org.projii.commons.spaceship.equipment.*;
import org.projii.commons.spaceship.weapon.Weapon;
import org.projii.commons.spaceship.weapon.WeaponModel;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BasicDataStorage implements DataStorage {

    private String connectionURL;
    private String host;
    private String database;
    private String username;
    private String password;
    private Connection connection;

    private HashMap<Integer, SpaceshipModel> spaceshipModels;
    private HashMap<Integer, EnergyGeneratorModel> energyGeneratorModels;
    private HashMap<Integer, EnergyShieldModel> energyShieldModels;
    private HashMap<Integer, SpaceshipEngine> engines;
    private HashMap<Integer, WeaponModel> weaponModels;

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

        loadSpaceshipModels();
        loadEngineModels();
        loadEnergyShieldModels();
        loadEnergyGeneratorModels();
        loadWeaponModels();

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

                spaceshipModels.put(id, new SpaceshipModel(id, modelName, health, armor, weaponAmount, length, width));
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
            energyGeneratorModels = new HashMap<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM energy_generator_models");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int maxEnergyLevel = resultSet.getInt("maxEnergyLevel");
                int regenerationSpeed = resultSet.getInt("regenerationSpeed");
                String name = resultSet.getString("name");
                energyGeneratorModels.put(id, new EnergyGeneratorModel(id, name, maxEnergyLevel, regenerationSpeed));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadEnergyShieldModels() {
        if (!isConnected()) {
            return;
        }
        try {
            energyShieldModels = new HashMap<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM energy_shield_models");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int maxEnergyLevel = resultSet.getInt("maxHPLevel");
                int regenerationSpeed = resultSet.getInt("regenerationSpeed");
                int regenerationDelay = resultSet.getInt("regenerationDelay");
                String name = resultSet.getString("name");
                energyShieldModels.put(id, new EnergyShieldModel(id, name, maxEnergyLevel, regenerationSpeed, regenerationDelay));
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void loadWeaponModels() {
        if (!isConnected()) {
            return;
        }
        try {
            weaponModels = new HashMap<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM weapon_models");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int type = resultSet.getInt("rate");
                int bulletSpeed = resultSet.getInt("bulletspeed");
                int damage = resultSet.getInt("demage");
                int cooldown = resultSet.getInt("energyconsumption");
                int range = resultSet.getInt("distance");
                int distance = resultSet.getInt("range");
                int energyConsumption = resultSet.getInt("cooldown");
                int rate = resultSet.getInt("type");

                weaponModels.put(id, new WeaponModel(id, name, rate, type, bulletSpeed, damage, energyConsumption, distance, range, cooldown));
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    private SpaceshipEngine getEngineModel(int modelId) {
        if (!isConnected()) {
            return null;
        }

        SpaceshipEngine engine = null;

        if (engines != null) {
            engine = engines.get(modelId);
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
                ps_selectEngineModelWhereId.setInt(1, modelId);
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
    public UserInfo getUserInfo(int userId) {
        try {
            if (ps_selectUserWhereId == null) {
                ps_selectUserWhereId = connection.prepareStatement("SELECT * FROM gamers WHERE id = ?");
            }
            ps_selectUserWhereId.setInt(1, userId);
            ResultSet resultSet = ps_selectUserWhereId.executeQuery();
            return getUserInfo(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserInfo getUserInfo(String username) {
        try {
            if (ps_selectUserWhereEmail == null) {
                ps_selectUserWhereEmail = connection.prepareStatement("SELECT * FROM gamers WHERE email = ?");
            }
            ps_selectUserWhereEmail.setString(1, username);
            ResultSet resultSet = ps_selectUserWhereEmail.executeQuery();
            return getUserInfo(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
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
    public List<Spaceship> getUserSpaceships(int userId) {
        if (!isConnected()) {
            return null;
        }

        if (ps_selectSpaceshipWhereOwner == null) {
            try {
                ps_selectSpaceshipWhereOwner = connection.prepareStatement("SELECT * FROM spaceships WHERE owner = ?");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        List<Spaceship> spaceshipList = new LinkedList<>();

        try {
            ps_selectSpaceshipWhereOwner.setInt(1, userId);
            ResultSet resultSet = ps_selectSpaceshipWhereOwner.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int owner = resultSet.getInt("owner");
                int spaceshipModelId = resultSet.getInt("model");
                int engineId = resultSet.getInt("engine");
                int energyGeneratorModelId = resultSet.getInt("energygenerator");
                int energyShieldModelId = resultSet.getInt("energyshield");
                int firstWeaponModelId = resultSet.getInt("firstweapon");
                int secondWeaponModelId = resultSet.getInt("secondweapon");
                int thirdWeaponModelId = resultSet.getInt("thirdweapon");

                SpaceshipModel model = spaceshipModels.get(spaceshipModelId);
                EnergyGenerator generator = new EnergyGenerator(energyGeneratorModels.get(energyGeneratorModelId));
                EnergyShield shield = new EnergyShield(energyShieldModels.get(energyShieldModelId));
                SpaceshipEngine engine = engines.get(engineId);


                Weapon[] weapons = {
                        new Weapon(weaponModels.get(firstWeaponModelId)),
                        new Weapon(weaponModels.get(secondWeaponModelId)),
                        new Weapon(weaponModels.get(thirdWeaponModelId))};


                spaceshipList.add(new Spaceship(id, model, weapons, generator, engine, shield));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spaceshipList;
    }


}
