package client;

import client.servises.IAnswerHandler;
import client.servises.ICommandCreator;
import client.servises.MessageService;
import client.servises.ServerWriter;
import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.clientCommands.commandType.LogCommand;
import library.clientCommands.commandType.RegCommand;
import library.serialization.SerializationManager;
import library.сlassModel.Organization;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;
import java.util.stream.Collectors;

public class ConsoleClient {
    private ICommandCreator commandCreator;
    private ByteBuffer buffer;
    private SocketAddress address;
    private IAnswerHandler answerHandler;
    private MessageService messageService;
    private UserData sessionUser = null;
//    private ObjectsMapController controller;

    public ConsoleClient(ICommandCreator commandCreator, ByteBuffer buffer,
                         SocketAddress address, IAnswerHandler answerHandler) {
        this.commandCreator = commandCreator;
        this.buffer = buffer;
        this.address = address;
        this.answerHandler = answerHandler;
    }

    /**
     * @param datagramChannel - канал
     * @throws IOException - в случае ошибки подключения
     */
    public void process(DatagramChannel datagramChannel) throws IOException {
//        ObjectsMapModel model = new ObjectsMapModel(new ArrayDeque<>());
//        SwingUtilities.invokeLater(() -> this.controller = new ObjectsMapController
//                (new ObjectsMapView(model.getOrganizationsCoordinateInfo(), model.getCellSize(), model.getCellCount()), model));
        Selector selector = Selector.open();
        messageService = new MessageService(selector);
        datagramChannel.register(selector, SelectionKey.OP_READ);
        ServerWriter serverWriter = new ServerWriter(commandCreator, messageService, selector);
        Thread writeUserThread = new Thread(serverWriter);
        writeUserThread.start();
        while (true) {
            if (!messageService.isEmpty()) {
                SelectionKey key = datagramChannel.keyFor(selector);
                key.interestOps(SelectionKey.OP_WRITE);
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
                    answerHandler.answerHandle(response); // getting response
                    responseHandle(serverWriter, response);
                } else if (selectionKey.isWritable()) {
                    write(selectionKey);

                }
            }
        }

    }

    private void responseHandle(ServerWriter serverWriter, Object response) {
        if (response instanceof SpecialSignals) {
            SpecialSignals ss = (SpecialSignals) response;
            if (ss == SpecialSignals.AUTHORIZATION_TRUE || ss == SpecialSignals.REG_TRUE) {
                serverWriter.setSessionUser(sessionUser);
                synchronized (messageService) {
                    messageService.notify();
                }
            } else if (ss == SpecialSignals.AUTHORIZATION_FALSE || ss == SpecialSignals.REG_FALSE) {
                synchronized (messageService) {
                    messageService.notify();
                }
            }
        } else if (response.getClass() == ArrayDeque.class) {
            Deque<Organization> mapObjects = ((Deque<?>) response).stream().map(o -> (Organization) o).collect(Collectors.toCollection(ArrayDeque::new));
//            SwingUtilities.invokeLater(() -> controller.updateObjectsMapView(mapObjects));

        }
    }


    private Object read(SelectionKey selectionKey, ByteBuffer buffer) throws IOException {    //пробрасываем исключения и обрабатываем их в NioClient
        buffer.clear();
        DatagramChannel channel = (DatagramChannel) selectionKey.channel();
        channel.receive(buffer);
        return SerializationManager.objectDeserial(buffer.array());
    }

    private void write(SelectionKey selectionKey) throws IOException {
        Command command;
        command = messageService.getFromRequestQueue();
        if (command.getClass() == LogCommand.class || command.getClass() == RegCommand.class) {
            sessionUser = command.getUserData();
        }
        ByteBuffer answer = ByteBuffer.wrap(SerializationManager.objectSerial(command));
        DatagramChannel datagramChannel = (DatagramChannel) selectionKey.channel();
        datagramChannel.send(answer, address);
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

}