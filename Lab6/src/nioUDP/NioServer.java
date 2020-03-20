package nioUDP;


import project.client.serialization.SerializationManager;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
            System.out.println("SERVER STARTED: " + serverAddress + "\n\n");
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            SerializationManager manager = new SerializationManager();
            while(true) {
                SocketAddress remoteAdd = serverChannel.receive(buffer);
                System.out.print("Buffer state before: " + buffer);
                buffer.flip();
                System.out.println(" Buffer state after: " + buffer + "\n\n");
                int limits = buffer.limit();
                byte bytes[] = new byte[limits];
                buffer.get(bytes, 0, limits);
                String msg = manager.objectDeserial(bytes).toString();
                System.out.println("Client at " + remoteAdd + " sent: " + msg + "\n\n");
                buffer.flip();
                serverChannel.send(buffer, remoteAdd);
                buffer.clear();
            }
        }
    }
}
