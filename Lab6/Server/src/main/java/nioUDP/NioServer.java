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


    public static void run(int port) throws IOException {
        SocketAddress serverAddress = new InetSocketAddress(port);
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


    public static void main(String[] args) throws IOException {
        try {
            String pathFile = args[0];
            int port = Integer.parseInt(args[1]);
            File fileForSaveAndLoad = new File(pathFile);
            if (fileForSaveAndLoad.exists() && fileForSaveAndLoad.canRead() && fileForSaveAndLoad.canWrite()) {
                NioServer.file = fileForSaveAndLoad;
                run(port);
            } else {
                logger.error("THE FILE MUST HAVE READ AND WRITE PERMISSIONS");
            }
        } catch (IndexOutOfBoundsException e) {
            logger.error("NECESSARY TO SPECIFY THE PATH TO THE FILE\n", e);
        } catch (IOException e) {
            logger.error("SERVER STARTING ERROR");
        } catch (NumberFormatException e) {
            logger.error("INVALID PORT SPECIFIED");
        }

    }

}
