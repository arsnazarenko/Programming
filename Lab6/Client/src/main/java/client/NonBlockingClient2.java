package client;

import client.servises.IAnswerHandler;
import client.servises.ICommandCreator;
import client.servises.WriteRunnable;
import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.clientCommands.commandType.LogCommand;
import library.clientCommands.commandType.RegCommand;
import library.serialization.SerializationManager;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

public class NonBlockingClient2 {
    UserData sessionUser = null;
    UserData user = null;
    private ICommandCreator commandCreator;
    private ByteBuffer buffer;
    private SocketAddress address;
    private client.servises.IAnswerHandler IAnswerHandler;
    private final Deque<List<Command>> changeRequest = new LinkedList<>();
    private Thread writeUserThread;

    public NonBlockingClient2(ICommandCreator commandCreator, ByteBuffer buffer,
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

        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);
        writeUserThread = new Thread(new WriteRunnable(commandCreator, changeRequest, selector));
        writeUserThread.setDaemon(true);
        writeUserThread.start();
        while (true) {
            synchronized (changeRequest) {
                if(!changeRequest.isEmpty()) {
                    SelectionKey key = datagramChannel.keyFor(selector);
                    key.interestOps(SelectionKey.OP_WRITE);
                }
            }
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
                    IAnswerHandler.handling(response);
                    if(response instanceof SpecialSignals) {
                        SpecialSignals ss = (SpecialSignals) response;
                        if (ss == SpecialSignals.AUTHORIZATION_TRUE || ss == SpecialSignals.REG_TRUE) {
                            sessionUser = user;
                        } else if(ss == SpecialSignals.EXIT_TRUE) {
                            System.exit(0);
                        }
                    }

                } else if (selectionKey.isWritable()) {
                    write(selectionKey);

                }
            }
        }

    }


    private Object read(SelectionKey selectionKey, ByteBuffer buffer) throws IOException {    //пробрасываем исключения и обрабатываем их в NioClient
        buffer.clear();
        DatagramChannel channel = (DatagramChannel) selectionKey.channel();
        channel.receive(buffer);
        return SerializationManager.objectDeserial(buffer.array());
    }

    private void write(SelectionKey selectionKey) throws IOException {
        synchronized (changeRequest) {
            List<Command> commands = changeRequest.pollFirst();
            for(Command c : commands) {
                if (c.getClass() == LogCommand.class || c.getClass() == RegCommand.class) {
                    user = c.getUserData();
                }
                ByteBuffer answer = ByteBuffer.wrap(SerializationManager.objectSerial(c));
                DatagramChannel datagramChannel = (DatagramChannel) selectionKey.channel();
                datagramChannel.send(answer, address);
            }
            selectionKey.interestOps(SelectionKey.OP_READ);
        }

    }

}
