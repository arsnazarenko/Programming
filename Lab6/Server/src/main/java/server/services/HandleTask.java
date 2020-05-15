package server.services;

import library.clientCommands.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramSocket;
import java.net.SocketAddress;

public class HandleTask implements Runnable {
    private DatagramSocket serverSocket;
    private LetterInfo request;
    private IHandlersController handlersController;
    private ServerSender serverSender;

    final static Logger logger = LogManager.getLogger(HandleTask.class.getName());

    public HandleTask(DatagramSocket serverSocket, LetterInfo request, IHandlersController handlersController, ServerSender serverSender) {
        this.serverSocket = serverSocket;
        this.request = request;
        this.handlersController = handlersController;
        this.serverSender = serverSender;
    }

    @Override
    public void run() {
        Command receiveCommand = request.getReceiveCommand();
        SocketAddress remoteAddress = request.getRemoteAddress();
        long start = System.currentTimeMillis();
        Object response = handlersController.handling(receiveCommand);
        long time = System.currentTimeMillis() - start;
        logger.info("CLIENT AT " + remoteAddress + " SENT: " + receiveCommand.getClass().getSimpleName());
        logger.debug("COMMAND EXECUTION TIME: " + time + " millis");
        serverSender.executeSendTask(response, remoteAddress);
    }
}
