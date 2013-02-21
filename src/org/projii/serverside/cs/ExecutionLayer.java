package org.projii.serverside.cs;

import org.jboss.netty.channel.Channel;
import org.projii.commons.net.InteractionMessage;
import org.projii.serverside.commons.RequestHandler;

import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ExecutionLayer {

    private final Map<Class<? extends InteractionMessage>, RequestHandler> handlers;
    private final ExecutorService workers;

    public ExecutionLayer(Map<Class<? extends InteractionMessage>, RequestHandler> handlers, ExecutorService workers) {
        this.handlers = handlers;
        this.workers = workers;
    }

    public void exec(InteractionMessage request, Channel channel) {
        Runnable task = new Task(request, channel);
        workers.execute(task);
    }

    private class Task implements Runnable {
        private final InteractionMessage request;
        private final Channel channel;

        private Task(InteractionMessage request, Channel channel) {
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
