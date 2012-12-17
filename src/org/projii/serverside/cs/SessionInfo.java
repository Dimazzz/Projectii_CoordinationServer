package org.projii.serverside.cs;

public class SessionInfo {
    public final int userId;
    public final int sessionId;

    public SessionInfo(int userId, int sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }
}
