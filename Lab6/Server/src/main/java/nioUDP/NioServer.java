package nioUDP;

import library.clientCommands.Command;
import library.serialization.ISerializationManager;
import library.serialization.SerializationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.services.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class NioServer {
    public static File file;
    private final static Logger logger = LogManager.getLogger(NioServer.class.getName());
    private static ArrayList<SocketAddress> clients = new ArrayList<>();


    public static void run(SocketAddress serverAddress) throws IOException {
        ISerializationManager serializationManager = new SerializationManager();
        CollectionManager collectionManager = XmlLoader.fromXmlToCollection(file);
        IHandlersManager handlersManager = new HandlersManager(collectionManager, new FieldSetter());
        logger.info("SERVER STARTED: " + serverAddress);
        logger.debug(collectionManager.getOrgCollection().size() + " objects in Collection\n");
        try (DatagramSocket serverSocket = new DatagramSocket(serverAddress)) {
            ServerReceiver serverReceiver = new ServerReceiver(serverSocket, new byte[4 * 1024], serializationManager);
            ServerSender serverSender = new ServerSender(serverSocket, serializationManager);
            while (true) {
                //получили запрос от сервера
                LetterInfo receiveLetter = serverReceiver.receive();
                //получили команду
                Command receiveCommand = receiveLetter.getReceiveCommand();

                //получили адрес клиента
                SocketAddress remoteAddress = receiveLetter.getRemoteAddress();
                clientRegister(remoteAddress);
                //обработали запрос и сформировали тестовый ответ
                long start = System.currentTimeMillis();
                Object msg = handlersManager.handling(receiveCommand);
                long time = System.currentTimeMillis() - start;
                logger.info("CLIENT AT " + remoteAddress + " SENT: " + receiveCommand.getClass().getSimpleName());
                logger.debug("COMMAND EXECUTION TIME: " + time + " millis");
                //отправили ответ клиенту
                serverSender.send(msg, remoteAddress);
            }
        }
    }
    private static void clientRegister(SocketAddress address) {
        if(!clients.contains(address)) {
            logger.info("NEW CLIENT: " + address);
            clients.add(address);
        }
    }


    public static void main(String[] args) {
        try {
            String pathFile = args[2];
            int port = Integer.parseInt(args[1]);
            SocketAddress socketAddress = new InetSocketAddress(args[0], port);
            File fileForSaveAndLoad = new File(pathFile);
            if (fileForSaveAndLoad.exists() && fileForSaveAndLoad.canRead() && fileForSaveAndLoad.canWrite()) {
                NioServer.file = fileForSaveAndLoad;
                run(socketAddress);
            } else {
                logger.error("THE FILE MUST HAVE READ AND WRITE PERMISSIONS");
            }
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
