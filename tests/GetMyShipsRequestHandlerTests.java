import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import org.jai.BSON.BSONDecoder;
import org.jai.BSON.BSONDocument;
import org.jai.BSON.BSONEncoder;
import org.projii.commons.net.CoordinationServerResponses;
import org.projii.commons.spaceship.Spaceship;
import org.projii.commons.spaceship.SpaceshipModel;
import org.projii.commons.spaceship.equipment.*;
import org.projii.commons.spaceship.weapon.Weapon;
import org.projii.commons.spaceship.weapon.WeaponModel;
import org.projii.serverside.cs.BasicDataStorage;
import org.projii.serverside.cs.SessionInfo;
import org.projii.serverside.cs.requesthandlers.GetMysSpaceshipsRequestHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GetMyShipsRequestHandlerTests extends TestCase {
    private BasicDataStorage dataStorage;
    private SessionInfo sessionInfo;

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(GetMyShipsRequestHandlerTests.class);
    }

    public void setUp() {
        this.dataStorage = new BasicDataStorage("192.168.56.100", "projectiidb", "projectii", "p5o73Ct3");
        try {
            this.dataStorage.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        int userId = 0;
        int sessionId = 1;
        this.sessionInfo = new SessionInfo(userId, sessionId);
    }

    public void tearDown() {
        this.dataStorage.disconnect();
    }

    public void testGetMyShipsRequestHandler() {
        GetMysSpaceshipsRequestHandler handler = new GetMysSpaceshipsRequestHandler(dataStorage);
        BSONDocument preResponse = handler.handle(null, sessionInfo);
        BSONDocument response = BSONDecoder.decode(BSONEncoder.encode(preResponse));

        int type = (int) response.get("type");
        assertTrue("Response type should be SHIPS_FULL_INFO", type == CoordinationServerResponses.SHIPS_FULL_INFO);

        //Getting base information about spaceship
        BSONDocument spaceships = (BSONDocument) response.get("spaceships");

        assertEquals("Unexpected count of spaceships", 1, spaceships.size());

        BSONDocument ship = (BSONDocument) spaceships.get("0");

        int spaceshipId = (int) ship.get("id");
        assertEquals("Unexpected ship id", 0, spaceshipId);

        int modelId = (int) ship.get("modelId");
        SpaceshipModel spaceshipModel = getSpaceshipModel(response);
        assertTrue("Spaceship model id's are not the same", modelId == spaceshipModel.id);

        int engineId = (int) ship.get("engineId");
        SpaceshipEngine engine = getSpaceshipEngine(response);
        assertTrue("Spaceship engine model id's are not the same", engineId == spaceshipModel.id);

        int generatorId = (int) ship.get("generatorId");
        EnergyGenerator energyGenerator = getEnergyGenerator(response);
        assertTrue("Spaceship generator model id's are not the same", generatorId == spaceshipModel.id);

        int shieldId = (int) ship.get("shieldId");
        EnergyShield energyShield = getEnergyShield(response);
        assertTrue("Spaceship shield model id's are not the same", shieldId == spaceshipModel.id);


        BSONDocument weaponModelIds = (BSONDocument) ship.get("weapons");
        int firstWeaponModelId = (int) weaponModelIds.get("0");
        int secondWeaponModelId = (int) weaponModelIds.get("1");
        int thirdWeaponModelId = (int) weaponModelIds.get("2");

        assertEquals("Unexpected first weapon model id", 1, firstWeaponModelId);
        assertEquals("Unexpected second weapon model id", 2, secondWeaponModelId);
        assertEquals("Unexpected third weapon model id", 2, thirdWeaponModelId);

        Weapon[] weapons = getWeapons(response);
        assertEquals("Unexpected first weapon model id", firstWeaponModelId, weapons[0].getModel().getId());
        assertEquals("Unexpected second weapon model id", secondWeaponModelId, weapons[1].getModel().getId());
        assertEquals("Unexpected third weapon model id", thirdWeaponModelId, weapons[2].getModel().getId());


        //Testing if exception thrown
        new Spaceship(spaceshipId, spaceshipModel, weapons, energyGenerator, engine, energyShield);
    }


    private Weapon[] getWeapons(BSONDocument response) {

        List<WeaponModel> weaponModelList = new ArrayList<>();
        BSONDocument weaponModels = (BSONDocument) response.get("weapons");

        assertEquals("Unexpected count of unique weapons models", 2, weaponModels.size());


        Weapon[] weapons = new Weapon[3];
        for (int i = 0; i < 2; i++) {
            BSONDocument wm = (BSONDocument) weaponModels.get(i + "");
            int weaponModelId = (int) wm.get("id");
            String name = (String) wm.get("name");
            int rate = (int) wm.get("rate");
            int weaponType = (int) wm.get("type");
            int bulletSpeed = (int) wm.get("bulletSpeed");
            int damage = (int) wm.get("damage");
            int energyConsumption = (int) wm.get("energyConsumption");
            int distance = (int) wm.get("distance");
            int range = (int) wm.get("range");
            int cooldown = (int) wm.get("cooldown");

            weapons[i] = new Weapon(new WeaponModel(weaponModelId, name, rate, weaponType, bulletSpeed, damage, energyConsumption,
                    distance, range, cooldown));
        }
        weapons[2] = weapons[1];
        return weapons;
    }

    private SpaceshipModel getSpaceshipModel(BSONDocument response) {
        BSONDocument spaceshipModelsInfo = (BSONDocument) response.get("spaceshipModels");
        BSONDocument spaceshipModelInfo = (BSONDocument) spaceshipModelsInfo.get("0");

        int spaceshipModelId = (int) spaceshipModelInfo.get("id");
        String spaceshipModelName = (String) spaceshipModelInfo.get("name");
        int health = (int) spaceshipModelInfo.get("health");
        int width = (int) spaceshipModelInfo.get("width");
        int length = (int) spaceshipModelInfo.get("length");
        int armor = (int) spaceshipModelInfo.get("armor");
        int weaponSlotCount = (int) spaceshipModelInfo.get("weaponSlotCount");

        return new SpaceshipModel(spaceshipModelId, spaceshipModelName, length, width, health, weaponSlotCount, armor);
    }

    private SpaceshipEngine getSpaceshipEngine(BSONDocument response) {

        BSONDocument engineModels = (BSONDocument) response.get("engineModels");

        assertEquals("Unexpected count of engine models", 1, engineModels.size());
        BSONDocument engineModel = (BSONDocument) engineModels.get("0");
        int engineModelId = (int) engineModel.get("id");
        int maneuverability = (int) engineModel.get("maneuverability");
        int maxSpeed = (int) engineModel.get("maxSpeed");
        int acceleration = (int) engineModel.get("acceleration");
        String engineModelName = (String) engineModel.get("name");
        return new SpaceshipEngine(engineModelId, maxSpeed, acceleration, maneuverability, engineModelName);
    }

    private EnergyShield getEnergyShield(BSONDocument response) {
        BSONDocument shieldModels = (BSONDocument) response.get("shieldModels");

        assertEquals("Unexpected count of engine models", 1, shieldModels.size());
        BSONDocument shieldModel = (BSONDocument) shieldModels.get("0");
        int shieldModelId = (int) shieldModel.get("id");
        int maxEnergyLevel = (int) shieldModel.get("maxEnergyLevel");
        int regenerationDelay = (int) shieldModel.get("regenerationDelay");
        int regenerationSpeed = (int) shieldModel.get("regenerationSpeed");
        String shieldModelName = (String) shieldModel.get("name");
        return new EnergyShield(new EnergyShieldModel(shieldModelId, shieldModelName, maxEnergyLevel, regenerationSpeed, regenerationDelay));
    }

    private EnergyGenerator getEnergyGenerator(BSONDocument response) {
        BSONDocument generatorModels = (BSONDocument) response.get("generatorModels");

        assertEquals("Unexpected count of engine models", 1, generatorModels.size()

        );
        BSONDocument generatorModel = (BSONDocument) generatorModels.get("0");
        int modelId = (int) generatorModel.get("id");
        int maxEnergyLevel = (int) generatorModel.get("maxEnergyLevel");
        int regenerationSpeed = (int) generatorModel.get("regenerationSpeed");
        String generatorModelName = (String) generatorModel.get("name");

        return new EnergyGenerator(new EnergyGeneratorModel(modelId, generatorModelName, maxEnergyLevel, regenerationSpeed));
    }
}