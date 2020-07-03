package client.servises;

import library.clientCommands.Command;

import java.nio.channels.Selector;
import java.util.ArrayDeque;
import java.util.Queue;

public class MessageService {
    private final Queue<Command> changeRequest;
    private final Selector selector;

    public MessageService(Selector selector) {
        this.changeRequest = new ArrayDeque<>();
        this.selector = selector;
    }

    public synchronized boolean isEmpty() {
        return changeRequest.isEmpty();
    }

    public synchronized Command getFromRequestQueue() {
        return changeRequest.poll();
    }

    public synchronized void putInRequestQueue(Command command) {
        changeRequest.offer(command); // request to server
        selector.wakeup();
    }
}
