package project.server.handlers;

import nioUDP.NioServer;
import project.client.commands.Command;
import project.server.services.CollectionManager;
import project.server.services.JaxbWorker;

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
