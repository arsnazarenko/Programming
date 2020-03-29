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
        ObjectCreator objectCreator = new ObjectCreator();
        Validator validator = new Validator(objectCreator, validateManager);
        Reader reader = new Reader(validator);
        CommandCreator commandCreator = new CommandCreator(reader, validator);


        SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);



        try (DatagramChannel client = DatagramChannel.open()) {
            client.bind(null);

            SerializationManager serializationManager = new SerializationManager();
            Receiver receiver = new Receiver(ByteBuffer.allocate(8*1024), client);
            Sender sender = new Sender(client, serializationManager);
            PostManager postManager = new PostManager(commandCreator, sender, receiver, serializationManager);

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







    public static void main(String[] args) throws IOException {
        try {
            runClient();
        } catch (NoSuchElementException e) {
            System.err.println("ЭКСТРЕННОЕ ЗАВЕРШЕНИЕ");
        }

    }

}
