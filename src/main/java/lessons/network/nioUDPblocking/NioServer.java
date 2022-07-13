package lessons.network.nioUDPblocking;

import lessons.serialization.SerializationManager;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NioServer {
    private static final int BUFFER_SIZE = 4 * 1024;
    static int port = 9999;

    public static void main(String[] args) throws IOException {
        SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        //InetSocketAddress serverAddress = new InetSocketAddress(port);
        try (DatagramChannel serverChannel = DatagramChannel.open()) {
            serverChannel.bind(serverAddress);
            System.out.println("SERVER STARTED: " + serverAddress);
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            SerializationManager manager = new SerializationManager();
            while(true) {
                SocketAddress remoteAdd = serverChannel.receive(buffer);
                System.out.print("Buffer state before: " + buffer);
                buffer.flip();
                System.out.println(" Buffer state after: " + buffer);
                int limits = buffer.limit();
                byte bytes[] = new byte[limits];
                buffer.get(bytes, 0, limits);
                String msg = manager.objectDeserial(bytes).toString();
                System.out.println("Client at " + remoteAdd + " sent: " + msg);
                buffer.flip();
                serverChannel.send(buffer, remoteAdd);
                buffer.clear();
            }
        }
    }
}
