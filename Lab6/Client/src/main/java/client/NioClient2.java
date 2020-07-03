package client;

import client.GuiClient;
import client.servises.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.NoSuchElementException;

public class NioClient2 {
    public static void main(String[] args) {
        try(DatagramChannel datagramChannel = DatagramChannel.open()) {
            //ConsoleClient consoleClient = null;
            int port = Integer.parseInt(args[1]);
            SocketAddress socketAddress = new InetSocketAddress(args[0], port);
            ObjectDataValidator objectDataValidator = new ObjectDataValidator();
            IObjectCreator objectCreator = new ObjectCreator(objectDataValidator);
            ArgumentValidateManager argumentValidateManager = new ArgumentValidateManager();
            ICommandProducerManager validator = new CommandProduceManager(objectCreator, argumentValidateManager);
            IReader reader = new Reader(validator);
            IAnswerHandler answerHandler = new AnswerHandler();
            ICommandCreator commandCreator = new CommandCreator(reader);
            datagramChannel.connect(socketAddress);
            datagramChannel.configureBlocking(false);
//            consoleClient = new ConsoleClient(commandCreator, ByteBuffer.allocate(256 * 1024),
//                    socketAddress, answerHandler);
//            consoleClient.process(datagramChannel);
            GuiClient client = new GuiClient(ByteBuffer.allocate(256 * 1024), socketAddress);
            client.process(datagramChannel);

        } catch (IOException e) {
            System.out.println("ОШИБКА ПОДКЛЮЧЕНИЯ К СЕРВЕРУ");
            System.exit(0);
        } catch (NoSuchElementException e) {
            System.out.println("ЭКСТРЕННОЕ ЗАВЕРШЕНИЕ");
            System.exit(0);
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