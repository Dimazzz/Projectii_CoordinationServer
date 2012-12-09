package org.projii.serverside.cs;

import java.util.LinkedList;
import java.util.List;

class SessionsManager {

    private final List<User> authorizedUsers = new LinkedList<>();
    private final DataStorage dataStorage;

    public SessionsManager(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public boolean isAuthorized(String username) {
        return find(username) != null;
    }

    public boolean logIn(String username, String password) {
        if (username == null) {
            return false;
        }
        System.out.println(username);
        UserInfo userInfo = dataStorage.getUserInfo(username);


        if (userInfo == null || !userInfo.getPassword().equals(password)) {
            return false;
        }

        authorizedUsers.add(new User(username, userInfo.getId()));

        return true;
    }

    public void logOut(int userid) {
        User u = find(userid);
        if (u != null) {
            authorizedUsers.remove(u);
        }
    }

    public int getUserId(String username) {
        User u = find(username);
        if(u != null){
            return u.userid;
        }

        return -1;
    }

    private User find(int userid) {
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
        public final int userid;

        private User(String username, int userid) {
            this.username = username;
            this.userid = userid;
        }
    }
}
