package client;



import client.servises.IAnswerHandler;
import client.servises.ICommandCreator;
import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.clientCommands.commandType.ExecuteScriptCommand;
import library.serialization.SerializationManager;

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

    private ICommandCreator commandCreator;
    private ByteBuffer buffer;
    private SocketAddress address;
    private IAnswerHandler IAnswerHandler;


    public NonBlockingClient(ICommandCreator commandCreator, ByteBuffer buffer,
                             SocketAddress address, IAnswerHandler IAnswerHandler) {
        this.commandCreator = commandCreator;
        this.buffer = buffer;
        this.address = address;
        this.IAnswerHandler = IAnswerHandler;
    }

    /**
     *
     * @param datagramChannel - канал
     * @throws IOException - в случае ошибки подключения
     */
    public void process(DatagramChannel datagramChannel) throws IOException {
        UserData userData = null;
        boolean auth = false;
        Selector selector = Selector.open();
        Queue<Command> commandQueue = null;
        datagramChannel.register(selector, SelectionKey.OP_WRITE);
        Command scriptCommandTmp = null;
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
                    Object response = read(selectionKey, buffer);
                    IAnswerHandler.answerHandle(response);
                    if(response instanceof SpecialSignals) {
                        SpecialSignals signal = (SpecialSignals) response;
                        if(signal == SpecialSignals.EXIT_TRUE) {
                            if (commandQueue == null) {     // если скрипт закончился и команда exit введена пользователем, то выходим из цикла
                                break out;
                            } else {
                                continue;
                            }
                        } else if(signal == SpecialSignals.SCRIPT_TRUE) {
                            commandQueue = queueUpdate(commandQueue, scriptCommandTmp);
                        } else if(signal == SpecialSignals.AUTHORIZATION_FALSE || signal == SpecialSignals.REG_FALSE) {
                            auth = false;
                        } else {
                            auth = true;
                        }
                    }

                } else if (selectionKey.isWritable()) {
                    Command command = null;
                    if (!(commandQueue == null)) {      //проверка, если скрипт уже был запущен
                        if (commandQueue.isEmpty()) {   //то проверяем нашу очередь из команд на пустоту, если пуста, значит прошлый скрипт закончился точно
                            commandQueue = null;    //то инициализируем его наллом, указав этим, что следующая команда не из скрипта
                        } else {
                            command = commandQueue.poll();      //иначе из очереди команд скрипта берем команду
                        }
                    } else {
                        if(!auth) {
                            command = commandCreator.authorization(System.in);
                            userData = command.getUserData();
                        } else {
                            command = commandCreator.createCommand(System.in, userData);
                        }
                    }
                    if (command != null) {
                        write(selectionKey, command);
                        if (command.getClass() == ExecuteScriptCommand.class) {//иначе если это скрипт, создаем очередь команд
                            scriptCommandTmp = command;//заменяем старую очередь
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
            //генерим список команд с логином и паролем, которые указаны при отправке команды execute_script
            commandQueue = commandCreator.createCommandQueue(scriptStream, script.getUserData());
            return commandQueue;
        } catch (NoSuchElementException e) {
            System.out.println("ОШИБКА СКРИПТА");
        } catch (IOException e) {
            System.out.println("ОШИБКА ФАЙЛА");
        }
        return commandQueue; // при возникшей ошибке возвращаем пустую очередь
    }

    private Object read(SelectionKey selectionKey, ByteBuffer buffer) throws IOException {    //пробрасываем исключения и обрабатываем их в NioClient
        buffer.clear();
        DatagramChannel channel = (DatagramChannel) selectionKey.channel();
        channel.receive(buffer);
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        return SerializationManager.objectDeserial(buffer.array());
    }

    private void write(SelectionKey selectionKey, Command command) throws IOException {
        ByteBuffer answer = ByteBuffer.wrap(SerializationManager.objectSerial(command));
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
