package server.business;

import server.business.services.ServerHandler;
import server.business.services.ServerReceiver;
import server.business.services.ServerSender;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class MessageSystem {

    private final Map<Class<?>, BlockingQueue<LetterInfo>> queues;

    public MessageSystem(Map<Class<?>, BlockingQueue<LetterInfo>> queues) {
        this.queues = queues;
    }

    public void putInQueues(Class<?> clazz, LetterInfo letter) {
        if (clazz == ServerReceiver.class) {
            queues.get(ServerHandler.class).add(letter);
        } else if (clazz == ServerHandler.class) {
            queues.get(ServerSender.class).add(letter);
        }
    }

    public LetterInfo getFromQueues(Class<?> clazz) throws InterruptedException {
        if (clazz == null) {
            throw new IllegalArgumentException();
        }
        return queues.get(clazz).take();
    }


    public Map<Class<?>, BlockingQueue<LetterInfo>> getQueues() {
        return queues;
    }
}
