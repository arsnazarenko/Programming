package lessons.network.UDP;

import lessons.network.UDP.multiThreadedUDP.server.UdpMultiThreadedServer;

import java.net.SocketException;

public class Main {
    public static void main(String[] args) throws SocketException {
        UdpMultiThreadedServer server = new UdpMultiThreadedServer(8080);
        server.init();
    }
}
