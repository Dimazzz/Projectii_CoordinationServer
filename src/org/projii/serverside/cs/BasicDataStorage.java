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
        this.username = username;
        this.password = password;
        this.connectionURL = "jdbc:postgresql://" + host + '/' + database;
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pii_sship_mdls");
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String modelName = resultSet.getString("name");
                int weaponSlotNumber = resultSet.getInt("wpn_slot_num");
                int health = resultSet.getInt("hp");
                int armor = resultSet.getInt("armor");
                int width = resultSet.getInt("width");
                int length = resultSet.getInt("length");

                spaceshipModels.put(id, new SpaceshipModel(id, modelName, health, armor, weaponSlotNumber, length, width));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadEngineModels() {
        if (!isConnected()) {
            return;
        }
        try {
            engines = new HashMap<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pii_ngn_mdls");
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int maxSpeed = resultSet.getInt("max_spd");
                int acceleration = resultSet.getInt("accl");
                int maneuverability = resultSet.getInt("mbility");

                engines.put(id, new SpaceshipEngine(id, maxSpeed, acceleration, maneuverability, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadEnergyGeneratorModels() {
        if (!isConnected()) {
            return;
        }
        try {
            energyGeneratorModels = new HashMap<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pii_egen_mdls");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int maxEnergyLevel = resultSet.getInt("max_nrg_lvl");
                int regenerationSpeed = resultSet.getInt("reg_spd");
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pii_eshld_mdls");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int maxEnergyLevel = resultSet.getInt("max_nrg_lvl");
                int regenerationSpeed = resultSet.getInt("reg_spd");
                int regenerationDelay = resultSet.getInt("reg_dly");

                energyShieldModels.put(id, new EnergyShieldModel(id, name, maxEnergyLevel, regenerationSpeed, regenerationDelay));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadWeaponModels() {
        if (!isConnected()) {
            return;
        }
        try {
            weaponModels = new HashMap<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pii_wpn_mdls");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int type = resultSet.getInt("type");
                int rate = resultSet.getInt("rate");
                int projectileSpeed = resultSet.getInt("pspeed");
                int damage = resultSet.getInt("damage");
                int energyConsumption = resultSet.getInt("nrgcons");
                int distance = resultSet.getInt("distance");
                int range = resultSet.getInt("range");
                int cooldown = resultSet.getInt("cd");

                weaponModels.put(id, new WeaponModel(id, name, rate, type, projectileSpeed, damage, energyConsumption, distance, range, cooldown));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                    ps_selectEngineModelWhereId = connection.prepareStatement("SELECT * FROM pii_ngn_mdls WHERE id = ?");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                ps_selectEngineModelWhereId.setInt(1, modelId);
                ResultSet resultSet = ps_selectEngineModelWhereId.executeQuery();
                resultSet.next();

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int maxSpeed = resultSet.getInt("max_spd");
                int acceleration = resultSet.getInt("accl");
                int maneuverability = resultSet.getInt("mbility");


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
                ps_selectUserWhereId = connection.prepareStatement("SELECT * FROM pii_gamers WHERE id = ?");
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
                ps_selectUserWhereEmail = connection.prepareStatement("SELECT * FROM pii_gamers WHERE email = ?");
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
            String password = resultSet.getString("passwd");
            String nickname = resultSet.getString("nickname");
            int experience = resultSet.getInt("exp");

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
                ps_selectSpaceshipWhereOwner = connection.prepareStatement("SELECT * FROM pii_sships WHERE owner = ?");
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
                int spaceshipModelId = resultSet.getInt("mdl");
                int engineId = resultSet.getInt("ngn");
                int energyGeneratorModelId = resultSet.getInt("nrg_gen");
                int energyShieldModelId = resultSet.getInt("nrg_shld");
                int firstWeaponModelId = resultSet.getInt("wpn_a");
                int secondWeaponModelId = resultSet.getInt("wpn_b");
                int thirdWeaponModelId = resultSet.getInt("wpn_c");

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
