package client.servises;

import library.clientCommands.Command;

import java.util.*;

public class MessageSystem {
    private final Queue<List<Command>> queue = new LinkedList<>();

    public synchronized void putAndWait(List<Command> commands) {
        if(commands != null) {
            queue.offer(commands);
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public synchronized void invokeWriter() {
        notify();
    }

    public synchronized List<Command> get() {

       return queue.poll();
    }
}
