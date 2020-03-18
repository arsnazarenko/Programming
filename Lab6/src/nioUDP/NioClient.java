package nioUDP;


import project.client.commands.Command;
import project.client.commands.uiCommands.ExecuteScriptCommand;
import project.client.commands.uiCommands.ExitCommand;
import project.client.commands.uiCommands.LoadCommand;
import project.client.serialization.SerializationManager;
import project.client.ui.*;
import project.client.ui.Reader;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
                Reader reader = new Reader();
                ValidateManager validateManager = new ValidateManager();
                ObjectCreator objectCreator = new ObjectCreator();
                Validator validator = new Validator(objectCreator, validateManager);
                FileLoader fileLoader = new FileLoader();



                UI ui = new UI(reader, validator);
                SerializationManager manager = new SerializationManager();


                SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);



                try (DatagramChannel client = DatagramChannel.open()) {
                    client.bind(null);
                    Sender sender = new Sender(ui, serverAddress);
                    //Изначально серверу нужно отправить содержимое xml файла, причем только один раз, при запуске приложения
                    sender.sendAndReceive(new LoadCommand(fileLoader.fileData(fileForSaveAndLoad)), client);
                    sender.execute(System.in, client);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("ФАЙЛ ДОЛЖЕН СУЩЕСТВОВАТЬ И ИМЕТЬ ПРАВА НА ЧТЕНИЕ И ЗАПИСЬ");

        } catch (IndexOutOfBoundsException e) {
            System.err.println("НУЖНО ВВЕСТИ ПУТЬ К ФАЙЛУ");
        } catch (
                NoSuchElementException e) {
            e.printStackTrace();
            System.err.println("ЭКСТРЕННОЕ ЗАВЕРШЕНИЕ");
        }

    }
}
