package org.projii.serverside.cs;

public class SessionInfo {
    private final int userId;
    private final int sessionId;

    public SessionInfo(int userId, int sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public int getSessionId() {
        return sessionId;
    }
}
