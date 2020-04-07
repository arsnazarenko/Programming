package server.handlers;

import library.clientCommands.Command;
import nioUDP.NioServer;
import server.services.CollectionManager;
import server.services.JaxbWorker;

public class SaveCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public SaveCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        JaxbWorker.convertObjectToXml(collectionManager, NioServer.file);
        return "Сохранено!";
    }
}
