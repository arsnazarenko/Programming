package nioUDP;


import project.client.commands.Command;
import project.client.commands.commandType.ExitCommand;
import project.client.commands.commandType.LoadCommand;
import project.client.serialization.SerializationManager;
import project.client.servises.*;
import project.client.servises.Reader;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.NoSuchElementException;

public class NioClient {
    static int serverPort = 9999;

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

                ValidateManager validateManager = new ValidateManager();
                ObjectCreator objectCreator = new ObjectCreator();
                Validator validator = new Validator(objectCreator, validateManager);
                Reader reader = new Reader(validator);
                FileLoader fileLoader = new FileLoader();
                CommandCreator commandCreator = new CommandCreator(reader, validator);
                SerializationManager manager = new SerializationManager();


                SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);



                try (DatagramChannel client = DatagramChannel.open()) {
                    client.bind(null);

                    SerializationManager serializationManager = new SerializationManager();
                    Receiver receiver = new Receiver(ByteBuffer.allocate(8*1024), client);
                    Sender sender = new Sender(client, serializationManager);
                    PostManager postManager = new PostManager(commandCreator, sender, receiver);
                    //Изначально серверу нужно отправить содержимое xml файла, причем только один раз, при запуске приложения
                    postManager.exchange(new LoadCommand(fileLoader.fileData(fileForSaveAndLoad)), serverAddress);


                    //для преобразования строки в InputStream, что потом поможет создать xml из строки , переданной с сервера
                    //DataInputStream a = new DataInputStream(new ByteArrayInputStream(new String("awdawd").getBytes()));
                    //a.readUTF();


                    try(InputStream inputStream = System.in) {
                        Command command;
                        Command[] commandLetter = new Command[1];
                        while (true) {
                            command = commandCreator.createCommand(inputStream);
                            commandLetter[0] = command;
                            postManager.exchangeWithServer(commandLetter, serverAddress);
                            if (command != null) {
                                if (command.getClass() == ExitCommand.class) {
                                    break;
                                }
                            }
                        }
                    }

                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("ФАЙЛ ДОЛЖЕН СУЩЕСТВОВАТЬ И ИМЕТЬ ПРАВА НА ЧТЕНИЕ И ЗАПИСЬ");
        } catch (IndexOutOfBoundsException e) {
            System.err.println("НУЖНО ВВЕСТИ ПУТЬ К ФАЙЛУ");
        } catch (NoSuchElementException e) {
            System.err.println("ЭКСТРЕННОЕ ЗАВЕРШЕНИЕ");
        }

    }
}
