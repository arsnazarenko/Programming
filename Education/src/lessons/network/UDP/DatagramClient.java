package lessons.network.UDP;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class DatagramClient {
    public static void main(String[] args) throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            DatagramPacket packet = encodePacket("Hello, world!");
            packet.setSocketAddress(new InetSocketAddress(InetAddress.getLocalHost(), 11111));
            socket.send(packet);

        }
    }

    private static DatagramPacket encodePacket(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        return new DatagramPacket(bytes, bytes.length);
    }
}
