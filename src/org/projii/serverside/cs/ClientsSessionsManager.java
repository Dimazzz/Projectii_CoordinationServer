package org.projii.serverside.cs;

public class ClientsSessionsManager extends AbstractSessionsManager {
    private final DataStorage dataStorage;
    private int lastSession;

    public ClientsSessionsManager(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.lastSession = 0;
    }

    private synchronized int generateSessionId() {
        return lastSession++;
    }

    @Override
    public SessionInfo createSession(String login, String password, int channelId) {
        SessionInfo sessionInfo = getSessionByLogin(login);
        if (sessionInfo != null) {
            return sessionInfo;
        }
        UserInfo userInfo = dataStorage.getUserInfo(login);
        if (userInfo == null || !userInfo.getPassword().equals(password)) {
            return null;
        }
        return newSession(login, userInfo.getId(), generateSessionId(), 0);
    }

}
