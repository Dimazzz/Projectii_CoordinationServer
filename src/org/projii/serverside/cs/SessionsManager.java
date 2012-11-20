package org.projii.serverside.cs;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class SessionsManager {

    private final List<User> authorizedUsers = new LinkedList<>();

    public boolean isAuthorized(String username) {
        return find(username) != null;
    }

    public boolean logIn(String username, ByteBuffer password) {
        return username != null && authorizedUsers.add(new User(username, new Random().nextLong()));
    }

    public void logOut(long userid) {
        User u = find(userid);
        if (u == null) {
            //throw new UserNotAuthorizedException(userid);
        }
        authorizedUsers.remove(u);
    }

    public long getUserId(String username) {
        return find(username).userid;
    }

    private User find(long userid) {
        for (User u : authorizedUsers) {
            if (u.userid == userid) {
                return u;
            }
        }
        return null;
    }

    private User find(String username) {
        for (User u : authorizedUsers) {
            if (u.username.equals(username)) {
                return u;
            }
        }
        return null;
    }

    private class User {
        public final String username;
        public final long userid;

        private User(String username, long userid) {
            this.username = username;
            this.userid = userid;
        }
    }
}
