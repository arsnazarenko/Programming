package server.business;

import library.clientCommands.commandType.*;
import server.business.services.ServerHandler;
import server.business.services.ServerReceiver;
import server.business.services.ServerSender;

import java.awt.image.AreaAveragingScaleFilter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class MessageSystem {
    private final Set<SocketAddress> clients = new HashSet<>();
    private final ArrayList<Class<?>> commandsForAll = new ArrayList<>();

    public void addNewClient(SocketAddress clientAddress) {
        clients.add(clientAddress);
    }

    private final Map<Class<?>, BlockingQueue<LetterInfo>> queues;

    public MessageSystem(Map<Class<?>, BlockingQueue<LetterInfo>> queues) {
        this.queues = queues;
        this.commandsForAll.addAll(Arrays.asList(AddIfMinCommand.class, AddCommand.class, RemoveLowerCommand.class,
                RemoveIdCommand.class, ClearCommand.class, UpdateIdCommand.class));

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

    public Set<SocketAddress> getClients() {
        return clients;
    }

    public Map<Class<?>, BlockingQueue<LetterInfo>> getQueues() {
        return queues;
    }

    public ArrayList<Class<?>> getCommandsForAll() {
        return commandsForAll;
    }
}
