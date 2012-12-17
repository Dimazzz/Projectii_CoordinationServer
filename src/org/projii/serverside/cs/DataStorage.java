package org.projii.serverside.cs;


import org.projii.commons.GameInfo;
import org.projii.commons.space.Map;
import org.projii.commons.spaceship.Spaceship;
import org.projii.commons.spaceship.SpaceshipModel;

import java.util.List;

public interface DataStorage {
    public UserInfo getUserInfo(int userid);

    public UserInfo getUserInfo(String login);

    public List<Spaceship> getUserSpaceships(int userid);

}
