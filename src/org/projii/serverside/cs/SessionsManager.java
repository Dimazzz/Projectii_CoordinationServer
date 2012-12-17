package org.projii.serverside.cs;

import java.util.LinkedList;
import java.util.List;

public class SessionsManager {

    private final List<UserSession> authorizedUsers = new LinkedList<>();
    private final DataStorage dataStorage;
    private int lastSession;

    public SessionsManager(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.lastSession = 0;
    }

    private synchronized int generateSessionId() {
        return lastSession++;
    }

    public boolean isAuthorized(String username) {
        return findByUsername(username) != null;
    }

    public SessionInfo logIn(String username, String password) {
        if (username == null) {
            return null;
        }

        UserInfo userInfo = dataStorage.getUserInfo(username);

        if (userInfo == null || !userInfo.getPassword().equals(password)) {
            return null;
        }

        UserSession userSession = new UserSession(username, userInfo.getId(), generateSessionId());
        authorizedUsers.add(userSession);

        return new SessionInfo(userSession.getUserId(), userSession.getSessionId());
    }

    public int getSessionId(int userId) {
        return getSessionId(findByUserId(userId));
    }

    public int getSessionId(String username) {
        return getSessionId(findByUsername(username));
    }

    private int getSessionId(UserSession userSession) {
        return userSession == null ? -1 : userSession.getSessionId();
    }

    public void logOut(int userId) {
        UserSession u = findByUserId(userId);
        if (u != null) {
            authorizedUsers.remove(u);
        }
    }

    public int getUserId(String username) {
        UserSession u = findByUsername(username);
        if (u != null) {
            return u.getUserId();
        }

        return -1;
    }

    public SessionInfo getSessionInfo(int sessionId) {
        UserSession sessionInfo = findBySessionId(sessionId);
        return sessionInfo == null ? null : sessionInfo.getSessionInfo();
    }

    private UserSession findByUserId(int userId) {
        for (UserSession u : authorizedUsers) {
            if (u.getUserId() == userId) {
                return u;
            }
        }
        return null;
    }

    private UserSession findByUsername(String username) {

        for (UserSession u : authorizedUsers) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    private UserSession findBySessionId(int sessionId) {

        for (UserSession u : authorizedUsers) {
            if (u.getSessionId() == sessionId) {
                return u;
            }
        }
        return null;
    }

    private class UserSession {
        private final String username;
        private final SessionInfo sessionInfo;

        private UserSession(String username, int userId, int sessionId) {
            this.username = username;
            this.sessionInfo = new SessionInfo(userId, sessionId);
        }

        public String getUsername() {
            return username;
        }

        public int getUserId() {
            return sessionInfo.userId;
        }

        public int getSessionId() {
            return sessionInfo.sessionId;
        }

        public SessionInfo getSessionInfo() {
            return sessionInfo;
        }
    }
}
