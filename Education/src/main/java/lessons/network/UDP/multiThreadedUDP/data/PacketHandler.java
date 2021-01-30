package lessons.network.UDP.multiThreadedUDP.data;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

public interface PacketHandler {
     void handle(DatagramPacket packet) throws ExecutionException, InterruptedException;
}
