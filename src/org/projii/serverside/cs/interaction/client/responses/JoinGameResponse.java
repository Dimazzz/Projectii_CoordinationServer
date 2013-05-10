package org.projii.serverside.cs.interaction.client.responses;

import org.jai.BSON.BSONSerializable;
import org.projii.commons.net.CoordinationServerRequests;
import org.projii.commons.net.InteractionMessage;

public class JoinGameResponse implements InteractionMessage{
    @BSONSerializable
    private final int type;
    private final int ip;
    private final boolean result;
    
	public JoinGameResponse(int ip, boolean result){
		this.result = result;
		this.ip = ip;
		type = CoordinationServerRequests.JOIN_GAME;
	}
	
    public int getType() {
        return type;
    }

}
