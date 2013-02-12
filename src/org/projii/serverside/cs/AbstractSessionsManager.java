package org.projii.serverside.cs;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSessionsManager implements SessionsManager {
    private List<Session> sessions = new LinkedList<>();

    protected SessionInfo newSession(String username, int userId, int sessionId, int channelId) {
        Session session = new Session(username, userId, sessionId, channelId);
        sessions.add(session);
        return session.getSessionInfo();
    }

    @Override
    public boolean sessionExists(String login) {
        return getSessionByLogin(login) != null;
    }

    @Override
    public boolean sessionExists(Integer channelId) {
        return findSession(channelId) != null;
    }

    @Override
    public void destroySession(int channelId) {
        Session session = findSession(channelId);
        if (session != null) {
            sessions.remove(session);
        }
    }

    @Override
    public SessionInfo getSessionByChannelId(int channelId) {
        Session session = findSession(channelId);
        return session == null ? null : session.getSessionInfo();
    }

    @Override
    public SessionInfo getSessionByLogin(String login) {
        for (Session session : sessions) {
            if (session.getLogin().equals(login)) {
                return session.getSessionInfo();
            }
        }
        return null;
    }

    private Session findSession(int channelId) {
        for (Session session : sessions) {
            if (session.getSessionInfo().getChannelId() == channelId) {
                return session;
            }
        }
        return null;
    }

    private class Session {
        private final String username;
        private final SessionInfo sessionInfo;

        protected Session(String username, int userId, int sessionId, int channelId) {
            this.username = username;
            this.sessionInfo = new SessionInfo(userId, sessionId, channelId);
        }

        public String getLogin() {
            return username;
        }

        public SessionInfo getSessionInfo() {
            return sessionInfo;
        }
    }
}
