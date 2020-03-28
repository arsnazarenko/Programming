package nioUDP;


import project.client.commands.Command;
import project.client.serialization.SerializationManager;
import project.client.servises.Sender;
import project.client.сlassModel.Organization;
import project.server.CollectionManager;
import project.server.FieldSetter;
import project.server.HandlersManager;
import project.server.services.IFileWorker;
import project.server.services.JaxbWorker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Deque;
import java.util.NoSuchElementException;

public class NioServer {
    private static final int BUFFER_SIZE = 10 * 1024;
    static int port = 9999;

    public static void run(File fileForSaveOrLoad) throws IOException {
        SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        IFileWorker fileWorker = new JaxbWorker();
        try (DatagramChannel serverChannel = DatagramChannel.open()) {
            serverChannel.bind(serverAddress);
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            SerializationManager serializationManager = new SerializationManager();
            CollectionManager collectionManager = new CollectionManager();
            HandlersManager handlersManager = new HandlersManager(collectionManager, new FieldSetter());
            Sender sender = new Sender(serverChannel, serializationManager);
            //данные из файда загрузим на сервер без какого ибо оповещения серверу
            Deque<Organization> organizationsFromXml = fileWorker.fromXmlToObject(fileForSaveOrLoad).getOrgCollection();
            collectionManager.getOrgCollection().addAll(organizationsFromXml);
            System.out.println("SERVER STARTED: " + serverAddress + "\n" + collectionManager.getOrgCollection());


            while(true) {
                SocketAddress remoteAdd = serverChannel.receive(buffer);
                System.out.print("Buffer state before: " + buffer);
                buffer.flip();
                System.out.println(" Buffer state after: " + buffer + "\n\n");
                int limits = buffer.limit();
                byte bytes[] = new byte[limits];
                buffer.get(bytes, 0, limits);
                Command receiveCommand = serializationManager.objectDeserial(bytes);
                String msg = handlersManager.handling(receiveCommand);
                //String msg = receiveCommand.toString();
                System.out.println("Client at " + remoteAdd + " sent: " + receiveCommand.getClass() + "\n\n");
                buffer.clear();
                buffer.put(msg.getBytes());
                buffer.flip();
                serverChannel.send(buffer, remoteAdd);
                buffer.clear();
                //или воспользоваться нашим классом Sender

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
