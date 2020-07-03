package server.business.tasks;

import library.clientCommands.Command;
import library.clientCommands.commandType.AddCommand;
import library.clientCommands.commandType.AddIfMinCommand;
import library.clientCommands.commandType.ClearCommand;
import library.clientCommands.commandType.UpdateIdCommand;
import library.сlassModel.Organization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.business.IHandlersController;
import server.business.LetterInfo;
import server.business.MessageSystem;
import server.business.dao.OrganizationDAO;
import server.business.services.ServerHandler;

import java.net.SocketAddress;
import java.util.*;

public class HandleTask implements Runnable {
    private LetterInfo request;
    private IHandlersController handlersController;
    private MessageSystem messageSystem;

    final static Logger logger = LogManager.getLogger(HandleTask.class.getName());

    public HandleTask(LetterInfo request, IHandlersController handlersController, MessageSystem messageSystem) {
        this.request = request;
        this.handlersController = handlersController;
        this.messageSystem = messageSystem;
    }

    @Override
    public void run() {
        Command receiveCommand = (Command) request.getTransferObject();
        SocketAddress remoteAddress = request.getRemoteAddress();
        long start = System.currentTimeMillis();
        Object response = handlersController.handling(receiveCommand);
        long time = System.currentTimeMillis() - start;
        logger.info("CLIENT AT " + remoteAddress + " SENT: " + receiveCommand.getClass().getSimpleName());
        logger.debug("COMMAND EXECUTION TIME: " + time + " millis");
        if(messageSystem.getCommandsForAll().contains(receiveCommand.getClass())) {
            Set<SocketAddress> clients = messageSystem.getClients();
            for (SocketAddress client: clients) {
                messageSystem.putInQueues(ServerHandler.class, new LetterInfo(client, response));
            }
        } else {
            messageSystem.putInQueues(ServerHandler.class, new LetterInfo(remoteAddress, response));
        }
    }
}
