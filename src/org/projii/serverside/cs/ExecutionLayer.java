package org.projii.serverside.cs;

import org.projii.serverside.cs.requesthandlers.*;
import org.projii.serverside.cs.requesthandlers.client.requests.*;
import org.projii.serverside.cs.requesthandlers.gameserver.requests.GameServerRequest;

import java.util.concurrent.ExecutorService;


public class ExecutionLayer {

    private final ExecutorService workers;
    private final SessionsManager sessionManager;
    private final DataStorage dataStorage;
    private final GamesManager gamesManager;

    public ExecutionLayer(ExecutorService workers, SessionsManager sessionManager, DataStorage dataStorage, GamesManager gamesManager) {
        this.workers = workers;
        this.sessionManager = sessionManager;
        this.dataStorage = dataStorage;
        this.gamesManager = gamesManager;
    }

    public void exec(AbstractRequest request, int id) {


        workers.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


}
