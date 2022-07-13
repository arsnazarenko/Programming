package lessons.network.nioUDPblocking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class NioClient2 {
    static int serverPort = 8080;
    public static void main(String[] args) throws IOException {
        SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);
        try(DatagramChannel client = DatagramChannel.open()) {
            client.bind(null);
            System.out.println("КЛИЕНТ ЗАПУЩЕН...");
            Scanner scanner = new Scanner(System.in);
            while(true) {
                String msg = scanner.nextLine();
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                client.send(buffer, serverAddress);
                buffer.clear();
                ByteBuffer bufferForAnswer = ByteBuffer.allocate(4 * 1048);
                client.receive(bufferForAnswer);
                bufferForAnswer.flip();
                int limits = bufferForAnswer.limit();
                byte bytes[] = new byte[limits];
                bufferForAnswer.get(bytes, 0, limits);
                System.out.println("ServerAnswer: " + new java.lang.String(bytes).trim());
                bufferForAnswer.clear();
            }

        }
    }
}
