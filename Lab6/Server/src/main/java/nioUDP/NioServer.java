package nioUDP;

import library.clientCommands.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.services.*;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class NioServer {
    private final static Logger logger = LogManager.getLogger(NioServer.class.getName());
    private static final ArrayList<SocketAddress> clients = new ArrayList<>();

    public static void run(SocketAddress serverAddress) throws IOException {
        IHandlersController handlersManager = new HandlersController(new CollectionManager(), new FieldSetter());
        logger.info("SERVER STARTED: " + serverAddress);
        Scanner scanner = new Scanner(System.in);


        try (DatagramSocket serverSocket = new DatagramSocket(serverAddress)) {
            ServerReceiver serverReceiver = new ServerReceiver(serverSocket, new byte[4 * 1024], handlersManager);
            serverReceiver.receive();
            while (true) {
                if (scanner.nextLine().equals("stop")) {
                    serverReceiver.getServerReceiverThread().interrupt();
                    break;
                }
            }





        }
    }


    public static void clientRegister(SocketAddress address) {
        synchronized (clients) {
            if (!clients.contains(address)) {
                clients.add(address);
            }
        }
        logger.info("NEW CLIENT: " + address);
    }


    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[1].trim());
            SocketAddress socketAddress = new InetSocketAddress(args[0].trim(), port);
            run(socketAddress);
        } catch (IndexOutOfBoundsException e) {
            logger.error("NECESSARY TO SPECIFY THE PATH TO THE FILE");
            logger.info("SAMPLE: java -jar Server.jar [host] [port] [file with objects]");
        } catch (IOException e) {
            logger.error("SERVER STARTING ERROR");
            logger.info("SAMPLE: java -jar Server.jar [host] [port] [file with objects]");
        } catch (NumberFormatException e) {
            logger.error("INVALID PORT SPECIFIED");
            logger.info("SAMPLE: java -jar Server.jar [host] [port] [file with objects]");
        } catch (IllegalArgumentException e) {
            logger.error("INVALID PARAMETERS");
            logger.info("SAMPLE: java -jar Server.jar [host] [port] [file with objects]");
        }

    }

}
