package org.projii.serverside.cs;


import org.projii.commons.spaceship.Spaceship;

import java.util.List;

public interface DataStorage {
    public UserInfo getUserInfo(int userid);

    public UserInfo getUserInfo(String login);

    public List<Spaceship> getUserSpaceships(int userid);

}
