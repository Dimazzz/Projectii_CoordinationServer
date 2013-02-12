package org.projii.serverside.cs;

public interface SessionsManager {
    SessionInfo createSession(String login, String password, int channelId);

    boolean sessionExists(String login);

    boolean sessionExists(Integer channelId);

    void destroySession(int channelId);

    SessionInfo getSessionByChannelId(int channelId);

    SessionInfo getSessionByLogin(String login);
}
