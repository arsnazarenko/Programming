package nioUDP;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.client.commands.Command;
import project.client.serialization.SerializationManager;
import project.client.servises.LetterInfo;
import project.client.servises.Receiver;
import project.client.servises.Sender;
import project.client.servises.XmlLoader;
import project.server.services.CollectionManager;
import project.server.services.FieldSetter;
import project.server.services.HandlersManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NioServer {
    private static final int BUFFER_SIZE = 8 * 1024;
    private static int port = 9999;
    public static File file;
    final static Logger logger = LogManager.getLogger(NioServer.class.getName());



    public static void run() throws IOException {
        SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        try (DatagramChannel serverChannel = DatagramChannel.open()) {
            serverChannel.bind(serverAddress);
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            SerializationManager serializationManager = new SerializationManager();
            CollectionManager collectionManager = XmlLoader.fromXmlToCollection(file);
            HandlersManager handlersManager = new HandlersManager(collectionManager, new FieldSetter());
            Sender sender = new Sender(serverChannel, serializationManager);
            //данные из файда загрузим на сервер без какого ибо оповещения серверу
            logger.info("SERVER STARTED: " + serverAddress + "\n" + collectionManager.getOrgCollection().size() + " objects in Collection");
            //System.err.println("SERVER STARTED: " + serverAddress + "\n" + collectionManager.getOrgCollection().size() + " объектов в коллекции");
            Receiver receiver = new Receiver(buffer, serverChannel);


            while (true) {
                LetterInfo letterInfo = receiver.receive();
                //сообщение
                byte[] bytes = letterInfo.getBytes();
                //адресс отправителя
                SocketAddress letterFrom = letterInfo.getAddress();
                Command receiveCommand = (Command) serializationManager.objectDeserial(bytes);

                //обработка команды и получение результата выполнения
                String msg = handlersManager.handling(receiveCommand);
                logger.info("CLIENT AT " + letterFrom + " SENT: " + receiveCommand.getClass().getName() + "\n");
                //System.err.println("Client at " + letterFrom + " sent: " + receiveCommand.getClass().getName() + "\n\n");
                //отправка сообщения клиенту
                sender.send(msg, letterFrom);
                logger.info("SERVER SENT ANSWER TO " + letterFrom + "\n");

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
