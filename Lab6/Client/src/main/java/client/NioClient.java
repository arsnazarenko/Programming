package client;


import client.servises.*;
import library.serialization.ISerializationManager;
import library.serialization.SerializationManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.NoSuchElementException;

public class NioClient {
    public static void main(String[] args) {
        try(DatagramChannel datagramChannel = DatagramChannel.open()) {
            int port = Integer.parseInt(args[1]);
            SocketAddress socketAddress = new InetSocketAddress(args[0], port);
            ISerializationManager serializationManager = new SerializationManager();
            IObjectCreator objectCreator = new ObjectCreator();
            ValidateManager validateManager = new ValidateManager();
            IValidator validator = new Validator(objectCreator, validateManager);
            IReader reader = new Reader(validator);
            AnswerHandler answerHandler = new AnswerHandler();
            ICommandCreator commandCreator = new CommandCreator(reader);
            datagramChannel.connect(socketAddress);
            datagramChannel.configureBlocking(false);
            NonBlockingClient nonBlockingClient = new NonBlockingClient(serializationManager, commandCreator, ByteBuffer.allocate(4 * 1024),
                    socketAddress, answerHandler);

            nonBlockingClient.process(datagramChannel);

        } catch (IOException e) {
            System.out.println("ОШИБКА ПОДКЛЮЧЕНИЯ К СЕРВЕРУ");
        } catch (NoSuchElementException e) {
            System.out.println("ЭКСТРЕННОЕ ЗАВЕРШЕНИЕ");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("УКАЖИТЕ ХОСТ И ПОРТ СЕРВЕРА");
        }




    }
}