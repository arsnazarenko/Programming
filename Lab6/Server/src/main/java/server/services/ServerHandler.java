package server.services;

import java.net.DatagramSocket;
import java.util.concurrent.ForkJoinPool;

public class ServerHandler {
    private IHandlersController handlersController;
    private DatagramSocket serverSocket;
    private final ForkJoinPool handlePool = new ForkJoinPool();
    private final ServerSender serverSender;

    public ServerHandler(IHandlersController handlersController, DatagramSocket serverSocket) {
        this.handlersController = handlersController;
        this.serverSocket = serverSocket;
        this.serverSender = new ServerSender(serverSocket);
    }

    public void executeHandleTask(LetterInfo request) {
        handlePool.submit(new HandleTask(serverSocket, request, handlersController, serverSender));
    }

}
