package nioUDP;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.client.commands.Command;
import project.client.commands.commandType.ExecuteScriptCommand;
import project.client.commands.commandType.ExitCommand;
import project.client.serialization.ISerializationManager;
import project.client.serialization.SerializationManager;
import project.client.servises.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class NonBlockingClient {
    private ISerializationManager serializationManager;
    private ICommandCreator commandCreator;
    private ByteBuffer buffer;
    private SocketAddress address;

    public NonBlockingClient(ISerializationManager serializationManager, ICommandCreator commandCreator, ByteBuffer buffer, SocketAddress address) {
        this.serializationManager = serializationManager;
        this.commandCreator = commandCreator;
        this.buffer = buffer;
        this.address = address;
    }

    public void process(DatagramChannel datagramChannel) throws IOException {
        Selector selector = Selector.open();
        Queue<Command> commandQueue = null;
        datagramChannel.register(selector, SelectionKey.OP_WRITE);
        out:
        while (true) {
            selector.select();
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                if (!selectionKey.isValid()) {
                    continue;
                }
                if (selectionKey.isReadable()) {
                    System.out.println(read(selectionKey, buffer).toString());
                } else if (selectionKey.isWritable()) {
                    Command command = null;
                    if (!(commandQueue == null)) {      //проверка, если скрипт уже был запущен
                        if (commandQueue.isEmpty()) {   //то проверяем нашу очередь из команд на пустоту, если пуста, значит прошлый скрипт закончился точно
                            commandQueue = null;    //то инициализируем его наллом, указав этим, что следующая команда не из скрипта
                        } else {
                            command = commandQueue.poll();      //иначе из очереди команд скрипта берем команду
                        }
                    } else {
                        command = commandCreator.createCommand(System.in);
                    } // иначе команда создаетя пользователем
                    if (command != null) {
                        write(selectionKey, command);
                        if (command.getClass() == ExitCommand.class) {
                            if (commandQueue == null) {     // если скрипт закончился и команда exit введена пользователем, то выходим из цикла
                                break out;
                            } else {
                                continue;   //иначе это команда скрипта, и завершать программу не надо
                            }
                        } else if (command.getClass() == ExecuteScriptCommand.class) {   //иначе если это скрипт, создаем очередь команд
                            Queue<Command> commandQueue2 = scriptRun(command); //создаем очередь из данного файла
                            if (commandQueue != null) {
                                commandQueue2.addAll(commandQueue);     //добавляем оставшиеся члены очереди в конец новой очереди(при вложенном скрипте)
                            }
                            commandQueue = commandQueue2;       //заменяем старую очередь
                        }
                    }
                }
            }
        }

    }

    private Queue<Command> scriptRun(Command command) {
        Queue<Command> commandQueue = new LinkedList<>();
        ExecuteScriptCommand script = (ExecuteScriptCommand) command;
        try (InputStream scriptStream = new FileInputStream(new File(script.getScript()))) {
            commandQueue = commandCreator.createCommandQueue(scriptStream);
            return commandQueue;
        } catch (NoSuchElementException e) {
            System.out.println("ОШИБКА СКРИПТА");
        } catch (IOException e) {
            System.out.println("ОШИБКА ФАЙЛА");
        }
        return commandQueue; // при возникшей ошибке возвращаем пустую очередь
    }

    private Object read(SelectionKey selectionKey, ByteBuffer buffer) throws IOException {
        buffer.clear();
        DatagramChannel channel = (DatagramChannel) selectionKey.channel();
        channel.receive(buffer);
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        return serializationManager.objectDeserial(buffer.array());
    }

    private void write(SelectionKey selectionKey, Command command) throws IOException {
        ByteBuffer answer = ByteBuffer.wrap(serializationManager.objectSerial(command));
        DatagramChannel datagramChannel = (DatagramChannel) selectionKey.channel();
        datagramChannel.send(answer, address);  //отправляем команду сервера
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

}
