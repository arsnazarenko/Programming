package nioUDP;


import project.client.commands.Command;
import project.client.serialization.SerializationManager;
import project.client.servises.LetterInfo;
import project.client.servises.Receiver;
import project.client.servises.Sender;
import project.client.servises.XmlLoader;
import project.server.CollectionManager;
import project.server.FieldSetter;
import project.server.HandlersManager;

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
    static int port = 9999;

    public static void run(File fileForSaveOrLoad) throws IOException {
        SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        try (DatagramChannel serverChannel = DatagramChannel.open()) {
            serverChannel.bind(serverAddress);
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            SerializationManager serializationManager = new SerializationManager();
            CollectionManager collectionManager = XmlLoader.fromXmlToCollection(fileForSaveOrLoad);
            HandlersManager handlersManager = new HandlersManager(collectionManager, new FieldSetter());
            Sender sender = new Sender(serverChannel, serializationManager);
            //данные из файда загрузим на сервер без какого ибо оповещения серверу
            System.out.println("SERVER STARTED: " + serverAddress + "\n" + collectionManager.getOrgCollection().size() + " объектов в коллекции");
            Receiver receiver = new Receiver(buffer, serverChannel);


            while(true) {
                LetterInfo letterInfo = receiver.receive();
                byte[] bytes = letterInfo.getBytes();
                SocketAddress letterFrom = letterInfo.getAddress();
                Command receiveCommand = (Command) serializationManager.objectDeserial(bytes);
                String msg = handlersManager.handling(receiveCommand);
                System.out.println("Client at " + letterFrom + " sent: " + receiveCommand.getClass() + "\n\n");
                sender.send(msg, letterFrom);
                //SocketAddress remoteAdd = serverChannel.receive(buffer);
                //buffer.flip();

                //int limits = buffer.limit();
                //byte bytes[] = new byte[limits];
                //buffer.get(bytes, 0, limits);
                //buffer.clear();
                //;
                //String msg = handlersManager.handling(receiveCommand);
                ;
                //buffer.put(msg.getBytes());
                //buffer.flip();
                //serverChannel.send(buffer, remoteAdd);
                //buffer.clear();
                //

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



                    run(fileForSaveAndLoad);



            }
        } catch (FileNotFoundException e) {
            System.err.println("ФАЙЛ ДОЛЖЕН СУЩЕСТВОВАТЬ И ИМЕТЬ ПРАВА НА ЧТЕНИЕ И ЗАПИСЬ");
        } catch (IndexOutOfBoundsException e) {
            System.err.println("НУЖНО ВВЕСТИ ПУТЬ К ФАЙЛУ");
        }

    }

}
