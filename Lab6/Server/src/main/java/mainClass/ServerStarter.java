package mainClass;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.business.*;
import server.business.services.IService;
import server.business.services.ServerHandler;
import server.business.services.ServerReceiver;
import server.business.services.ServerSender;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerStarter {
    private final static Logger logger = LogManager.getLogger(ServerStarter.class.getName());
    private static final ArrayList<SocketAddress> clients = new ArrayList<>();

    public static void run(SocketAddress serverAddress) throws IOException {
        CollectionManager collectionManager = new CollectionManager();
        IHandlersController handlersManager = new HandlersController(collectionManager, new FieldSetter());
        Scanner scanner = new Scanner(System.in);

        Map<Class<?>, BlockingQueue<LetterInfo>> queues = new HashMap<>();
        queues.put(ServerHandler.class, new ArrayBlockingQueue<>(15));
        queues.put(ServerSender.class, new ArrayBlockingQueue<>(15));


        MessageSystem messageSystem = new MessageSystem(queues);

        logger.info("SERVER STARTED: " + serverAddress);
        try (DatagramSocket serverSocket = new DatagramSocket(serverAddress)) {

            IService[] services = new IService[3];

            services[0] = new ServerReceiver(messageSystem, serverSocket, 12 * 1024);
            services[1] = new ServerHandler(handlersManager, messageSystem);
            services[2] = new ServerSender(serverSocket, messageSystem);

            for (IService service : services) {
                service.start();
            }

            while (true) {
                if (scanner.nextLine().equals("stop")) {
                    for (IService service : services) {
                        service.stop();
                    }
                    break;
                }
            }
        }
    }


    public static void clientRegister(SocketAddress address) {

        if (!clients.contains(address)) {
            clients.add(address);
            logger.info("NEW CLIENT: " + address);
        }

    }
        public static void main (String[] args){
            try {
                String host = args[0].trim();
                int port = Integer.parseInt(args[1].trim());
                SocketAddress socketAddress = new InetSocketAddress(host, port);
                run(socketAddress);

            } catch (IndexOutOfBoundsException e) {
                logger.error("NECESSARY TO SPECIFY THE PATH TO THE FILE");
                e.printStackTrace();
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
