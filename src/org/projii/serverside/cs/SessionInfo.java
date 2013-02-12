package org.projii.serverside.cs;

public class SessionInfo {
    private final int userId;
    private final int sessionId;
    private final int channelId;

    public SessionInfo(int userId, int sessionId, int channelId) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.channelId = channelId;
    }

    public int getUserId() {
        return userId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getChannelId() {
        return channelId;
    }
}