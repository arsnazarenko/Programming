package nioUDP;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.client.commands.Command;
import project.client.serialization.ISerializationManager;
import project.client.serialization.SerializationManager;
import project.server.services.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;

public class NioServer {
    private static final int BUFFER_SIZE = 8 * 1024;
    private static int port = 9999;
    public static File file;
    final static Logger logger = LogManager.getLogger(NioServer.class.getName());


    public static void run() throws IOException {
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
                //обработали запрос и сформировали тестовый ответ
                String msg = handlersManager.handling(receiveCommand);
                logger.info("CLIENT AT " + remoteAddress + " SENT: " + receiveCommand.getClass().getName());
                //отправили ответ клиенту
                serverSender.send(msg, remoteAddress);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        try {
            String pathFile = args[0];
            File fileForSaveAndLoad = new File(pathFile);
            if (!fileForSaveAndLoad.exists()) {
                throw new FileNotFoundException();
            } else if (!fileForSaveAndLoad.canRead()) {
                throw new FileNotFoundException();
            } else if (!fileForSaveAndLoad.canWrite()) {
                throw new FileNotFoundException();
            } else {

                NioServer.file = fileForSaveAndLoad;
                run();


            }
        } catch (FileNotFoundException e) {
            //System.err.println("ФАЙЛ ДОЛЖЕН СУЩЕСТВОВАТЬ И ИМЕТЬ ПРАВА НА ЧТЕНИЕ И ЗАПИСЬ");
            logger.error("ФАЙЛ ДОЛЖЕН СУЩЕСТВОВАТЬ И ИМЕТЬ ПРАВА НА ЧТЕНИЕ И ЗАПИСЬ", e);
        } catch (IndexOutOfBoundsException e) {
            //System.err.println("НУЖНО ВВЕСТИ ПУТЬ К ФАЙЛУ");
            logger.error("НУЖНО ВВЕСТИ ПУТЬ К ФАЙЛУ", e);
        }

    }

}
