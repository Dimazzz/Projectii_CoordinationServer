package org.projii.serverside.cs.requesthandlers;

public abstract class AbstractRequest {

    private final int type;

    protected AbstractRequest(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
