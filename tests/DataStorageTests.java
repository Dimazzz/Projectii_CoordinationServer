import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import org.projii.commons.spaceship.Spaceship;
import org.projii.serverside.cs.BasicDataStorage;
import org.projii.serverside.cs.DataStorage;
import org.projii.serverside.cs.UserInfo;

import java.sql.SQLException;
import java.util.List;

public class DataStorageTests extends TestCase {
    DataStorage dataStorage;

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DataStorageTests.class);
    }

    public void setUp() {
        BasicDataStorage basicDataStorage = new BasicDataStorage("192.168.56.100", "projectiidb", "projectii", "p5o73Ct3");
        try {
            basicDataStorage.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        dataStorage = basicDataStorage;
    }

    public void tearDown() {
        ((BasicDataStorage) dataStorage).disconnect();
    }

    public void testGetUserInfo() {
        UserInfo userInfo = dataStorage.getUserInfo(0);
        assertTrue(userInfo != null);
        assertEquals("Unexpected nickname", "LolLer", userInfo.getNickname());

        userInfo = dataStorage.getUserInfo(1);
        assertTrue(userInfo != null);
        assertEquals("Unexpected nickname", "Boss", userInfo.getNickname());

        userInfo = dataStorage.getUserInfo("loller@loldomain.lol");
        assertTrue("User info shouldn't be null", userInfo != null);
        assertEquals("Unexpected user id", 0, userInfo.getId());

        userInfo = dataStorage.getUserInfo("lamer@lala.la");
        assertTrue(userInfo != null);
        assertEquals("Unexpected user id", 1, userInfo.getId());
    }

    public void testGetUserSpaceships() {
        List<Spaceship> spaceshipList = dataStorage.getUserSpaceships(0);
        assertTrue("Spaceships list shouldn't be null", spaceshipList != null);
        assertTrue("Spaceships list shouldn't be empty", !spaceshipList.isEmpty());
        for (Spaceship spaceship : spaceshipList) {
            assertEquals("Unexpected spaceship id", 0, spaceship.getId());
            assertTrue("Spaceships model shouldn't be null", spaceship.getModel() != null);
            assertEquals("Unexpected model id", 0, spaceship.getModel().id);

            assertTrue("Spaceships engine shouldn't be null", spaceship.getEngine() != null);
            assertEquals("Unexpected engine id", 0, spaceship.getEngine().getId());

            assertTrue("Spaceships generator shouldn't be null", spaceship.getGenerator() != null);
            assertTrue("Spaceships generator model shouldn't be null", spaceship.getGenerator().getModel() != null);
            assertEquals("Unexpected generator model id", 0, spaceship.getGenerator().getModel().id);

            assertTrue("Spaceships shield shouldn't be null", spaceship.getEnergyShield() != null);
            assertTrue("Spaceships shield shouldn't be null", spaceship.getEnergyShield().getModel() != null);
            assertEquals("Unexpected shield model id", 0, spaceship.getEnergyShield().getModel().id);

            assertTrue("Weapons count shouldn't be null", spaceship.getWeapons() != null);
            assertEquals("Unexpected count of weapons", 3, spaceship.getWeapons().length);
        }
    }

}