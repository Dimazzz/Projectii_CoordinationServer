import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import org.projii.commons.spaceship.SpaceshipModel;
import org.projii.serverside.cs.BasicDataStorage;
import org.projii.serverside.cs.DataStorage;
import org.projii.serverside.cs.UserInfo;

import java.sql.SQLException;

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

    public void testGetShipModel() {
        SpaceshipModel spaceshipModel = dataStorage.getSpaceshipModel(0);
        assertTrue(spaceshipModel != null);
        assertEquals("Unexpected name", "furrymosquito", spaceshipModel.getName());

    }

    public void testGetUserInfo() {
        UserInfo userInfo = dataStorage.getUserInfo(0);
        assertTrue(userInfo != null);
        assertEquals("Unexpected nickname", "LolLer", userInfo.getNickname());

        userInfo = dataStorage.getUserInfo(1);
        assertTrue(userInfo != null);
        assertEquals("Unexpected nickname", "Boss", userInfo.getNickname());
    }


}