package client;


import client.servises.*;

import library.serialization.SerializationManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.NoSuchElementException;

public class NioClient {
    public static void main(String[] args) {
        try(DatagramChannel datagramChannel = DatagramChannel.open()) {
            int port = Integer.parseInt(args[1]);
            SocketAddress socketAddress = new InetSocketAddress(args[0], port);
            IObjectCreator objectCreator = new ObjectCreator();
            ValidateManager validateManager = new ValidateManager();
            IValidator validator = new Validator(objectCreator, validateManager);
            IReader reader = new Reader(validator);
            IAnswerHandler IAnswerHandler = new AnswerHandler();
            ICommandCreator commandCreator = new CommandCreator(reader);
            datagramChannel.connect(socketAddress);
            datagramChannel.configureBlocking(false);
            NonBlockingClient nonBlockingClient = new NonBlockingClient(commandCreator, ByteBuffer.allocate(4 * 1024),
                    socketAddress, IAnswerHandler);

            nonBlockingClient.process(datagramChannel);

        } catch (IOException e) {
            System.out.println("ОШИБКА ПОДКЛЮЧЕНИЯ К СЕРВЕРУ");
        } catch (NoSuchElementException e) {
            System.out.println("ЭКСТРЕННОЕ ЗАВЕРШЕНИЕ");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("УКАЖИТЕ ХОСТ И ПОРТ СЕРВЕРА\nSAMPLE: java -jar Client.jar [hostname] [port]");
        } catch (NumberFormatException e) {
            System.out.println("НЕКОРРЕКТНЫЙ ПОРТ\nSAMPLE: java -jar Client.jar [hostname] [port]");
        } catch (UnresolvedAddressException e) {
            System.out.println("НЕКОРРЕКТНЫЙ ХОСТ\nSAMPLE: java -jar Client.jar [hostname] [port]");
        } catch (IllegalArgumentException e) {
            System.out.println("НЕКОРРЕКТНЫЙ ВВОД\nSAMPLE: java -jar Client.jar [hostname] [port]");
        }




    }
}