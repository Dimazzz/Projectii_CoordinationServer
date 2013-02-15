package org.projii.serverside.cs;

import org.jboss.netty.channel.Channel;
import org.projii.commons.net.Request;
import org.projii.serverside.cs.interaction.client.RequestHandler;

import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ExecutionLayer {

    private final Map<Class<? extends Request>, RequestHandler> handlers;
    private final ExecutorService workers;

    public ExecutionLayer(Map<Class<? extends Request>, RequestHandler> handlers, ExecutorService workers) {
        this.handlers = handlers;
        this.workers = workers;
    }

    public void exec(Request request, Channel channel) {
        Runnable task = new Task(request, channel);
        workers.execute(task);
    }

    private class Task implements Runnable {
        private final Request request;
        private final Channel channel;

        private Task(Request request, Channel channel) {
            this.request = request;
            this.channel = channel;
        }

        @Override
        public void run() {
            RequestHandler requestHandler = handlers.get(request.getClass());
            requestHandler.handle(request, channel);
        }
    }

}
