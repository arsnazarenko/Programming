package server.services;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.ForkJoinPool;

public class ServerSender implements IServerSender {
    private final DatagramSocket serverSocket;
    private final ForkJoinPool senderPool = new ForkJoinPool();

    public ServerSender(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void executeSendTask(Object letter, SocketAddress address) {
        senderPool.submit(new SendTask(serverSocket, letter, address));
    }
}
