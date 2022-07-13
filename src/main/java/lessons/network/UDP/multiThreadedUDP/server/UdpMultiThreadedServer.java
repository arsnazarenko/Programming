package lessons.network.UDP.multiThreadedUDP.server;

import lessons.network.UDP.multiThreadedUDP.data.PacketHandler;
import lessons.network.UDP.multiThreadedUDP.data.PacketHandlerImpl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @author Арсений
 * @version 1.0
 */
public class UdpMultiThreadedServer{

    private int port;
    private Thread receive;
    private DatagramSocket socket;


    public UdpMultiThreadedServer(int port) {
        this.port = port;
    }

    public void init() {
        try {
            socket = new DatagramSocket(8080);
            receive(new PacketHandlerImpl(socket));
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }


    public int getPort() {
        return this.port;
    }


    public void receive(PacketHandler handler) {
        receive = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " ожидает запроса...");
                    byte[] buffer = new byte[1024];
                    DatagramPacket dgpacket = new DatagramPacket(buffer, buffer.length);
                    try {
                        socket.receive(dgpacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        handler.handle(dgpacket);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    System.err.println("Прерывание, конец чтения");
                    Thread.currentThread().interrupt();
                    socket.close();
                }
            }
        }, "receive_thread");
        receive.start();
    }

}
