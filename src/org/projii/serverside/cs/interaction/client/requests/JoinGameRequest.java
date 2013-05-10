package org.projii.serverside.cs.interaction.client.requests;

import org.jai.BSON.BSONSerializable;
import org.projii.commons.net.CoordinationServerRequests;

public class JoinGameRequest extends ClientRequest {
	@BSONSerializable
    private final int type = CoordinationServerRequests.JOIN_GAME;
	@BSONSerializable
	private final int id;
	
	protected JoinGameRequest(int id){
		this.id = id;
	}

    public int getId(){
    	return id;
    }
    
    @Override
    public int getType() {
        return type;
    }
}
