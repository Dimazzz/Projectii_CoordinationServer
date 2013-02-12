package org.projii.serverside.cs.interaction.client.requests;

import org.jai.BSON.BSONSerializable;
import org.projii.commons.net.CoordinationServerRequests;

public class AuthorizationRequest extends ClientRequest {

    @BSONSerializable
    private final String login;
    @BSONSerializable
    private final String password;
    @BSONSerializable
    private final int type = CoordinationServerRequests.AUTHORIZATION;

    protected AuthorizationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


    public int getType() {
        return type;
    }
}
