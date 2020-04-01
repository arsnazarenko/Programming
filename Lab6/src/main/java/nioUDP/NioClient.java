package nioUDP;


import project.client.commands.Command;
import project.client.commands.commandType.ExitCommand;
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

    public static void runClient() throws IOException {

        ValidateManager validateManager = new ValidateManager();
        IObjectCreator objectCreator = new ObjectCreator();
        IValidator validator = new Validator(objectCreator, validateManager);
        IReader reader = new Reader(validator);
        ICommandCreator commandCreator = new CommandCreator(reader);
        SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);



        try (DatagramChannel clientChannel = DatagramChannel.open()) {
            clientChannel.connect(serverAddress);
            SerializationManager serializationManager = new SerializationManager();
            IClientReceiver clientReceiver = new ClientReceiver(ByteBuffer.allocate(8*1024), clientChannel, serializationManager);
            IClientSender clientSender = new ClientSender(clientChannel, serializationManager);
            PostManager postManager = new PostManager(commandCreator, clientSender, clientReceiver);

            try(InputStream inputStream = System.in) {
                Command command;
                Command[] commandLetter = new Command[1];
                while (true) {
                    //создание команды
                    command = commandCreator.createCommand(inputStream);
                    commandLetter[0] = command;
                    //обмен с сервером(отправка и получение ответа)
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







    public static void main(String[] args) throws IOException {
        try {
            runClient();
        } catch (NoSuchElementException e) {
            System.err.println("ЭКСТРЕННОЕ ЗАВЕРШЕНИЕ");
        }

    }

}
