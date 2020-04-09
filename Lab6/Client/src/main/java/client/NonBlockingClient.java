package client;



import client.servises.AnswerHandler;
import client.servises.ICommandCreator;
import library.clientCommands.Command;
import library.clientCommands.commandType.ExecuteScriptCommand;
import library.clientCommands.commandType.ExitCommand;
import library.serialization.ISerializationManager;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

/**
 * Реализация неблокиующего клиента
 */
public class NonBlockingClient {
    private ISerializationManager serializationManager;
    private ICommandCreator commandCreator;
    private ByteBuffer buffer;
    private SocketAddress address;
    private AnswerHandler answerHandler;

    public NonBlockingClient(ISerializationManager serializationManager, ICommandCreator commandCreator, ByteBuffer buffer,
                             SocketAddress address, AnswerHandler answerHandler) {
        this.serializationManager = serializationManager;
        this.commandCreator = commandCreator;
        this.buffer = buffer;
        this.address = address;
        this.answerHandler = answerHandler;
    }

    /**
     *
     * @param datagramChannel - канал
     * @throws IOException - в случае ошибки подключения
     */
    public void process(DatagramChannel datagramChannel) throws IOException {
        Selector selector = Selector.open();
        Queue<Command> commandQueue = null;
        datagramChannel.register(selector, SelectionKey.OP_WRITE);
        out:
        while (true) {
            selector.select();  //блокирующий
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next();
                selectionKeyIterator.remove(); //ВНИМАТЕЛЬНО!!!
                if (!selectionKey.isValid()) {
                    continue;
                }
                if (selectionKey.isReadable()) {
                    answerHandler.handling(read(selectionKey, buffer));
                } else if (selectionKey.isWritable()) {
                    Command command = null;
                    if (!(commandQueue == null)) {      //проверка, если скрипт уже был запущен
                        if (commandQueue.isEmpty()) {   //то проверяем нашу очередь из команд на пустоту, если пуста, значит прошлый скрипт закончился точно
                            commandQueue = null;    //то инициализируем его наллом, указав этим, что следующая команда не из скрипта
                        } else {
                            command = commandQueue.poll();      //иначе из очереди команд скрипта берем команду
                        }
                    } else {
                        command = commandCreator.createCommand(System.in);  // иначе команда создаетя пользователем
                    }
                    if (command != null) {
                        write(selectionKey, command);
                        if (command.getClass() == ExitCommand.class) {
                            if (commandQueue == null) {     // если скрипт закончился и команда exit введена пользователем, то выходим из цикла
                                break out;
                            } else {
                                continue;   //иначе это команда скрипта, и завершать программу не надо
                            }
                        } else if (command.getClass() == ExecuteScriptCommand.class) {   //иначе если это скрипт, создаем очередь команд
                            commandQueue = queueUpdate(commandQueue, command);       //заменяем старую очередь
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

    private Object read(SelectionKey selectionKey, ByteBuffer buffer) throws IOException {     //пробрасываем исключения и обрабатываем их в NioClient
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

    private Queue<Command> queueUpdate(Queue<Command> old, Command scriptCommand) {
        Queue<Command> newQueue = scriptRun(scriptCommand);
        if (old!= null) {
          newQueue.addAll(old);
        }
        return newQueue;
    }

}
