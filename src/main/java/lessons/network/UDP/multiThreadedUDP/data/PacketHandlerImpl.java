package lessons.network.UDP.multiThreadedUDP.data;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class PacketHandlerImpl implements PacketHandler{
    private ForkJoinPool sendPool = new ForkJoinPool();

    private List<String> resources = new ArrayList<>();

    private ForkJoinPool handlePool = new ForkJoinPool();

    private DatagramSocket serverSocket;

    public PacketHandlerImpl(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void handle(DatagramPacket packet) throws ExecutionException, InterruptedException {
        handlePool.submit(new HandleTask(packet, resources, sendPool, serverSocket));
    }

    private static class HandleTask implements Runnable {
        private DatagramPacket packet;
        private final List<String> resources;
        private final ForkJoinPool sendPool;
        private final DatagramSocket serverSocket;


        public HandleTask(DatagramPacket packet, List<String> resources, ForkJoinPool sendPool, DatagramSocket serverSocket) {
            this.packet = packet;
            this.resources = resources;
            this.sendPool = sendPool;
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : Обработка сообщения");
            String data = new String(packet.getData()).trim();
            synchronized (resources) {
                resources.add(data);
            }
            String response = null;
            if (!data.equals("Bye")) {
                response = data + ", client";
            } else {
                response = "GoodBye, I'm going sleep";
            }
            byte[] bytes = response.getBytes();
            DatagramPacket responsePacket =  new DatagramPacket(bytes, bytes.length, packet.getAddress(), packet.getPort());
            sendPool.submit(new SendTask(responsePacket, serverSocket));
        }
    }
    private static class SendTask implements Runnable {

        private DatagramPacket packet;
        private DatagramSocket serverSocket;

        public SendTask(DatagramPacket packet, DatagramSocket serverSocket) {
            this.packet = packet;
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() +  " : отправка сообщения...");
                serverSocket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
