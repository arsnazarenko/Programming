package project.server.Handlers;

import project.client.commands.Command;
import project.server.CollectionManager;

public class InfoCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public InfoCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        //информация о колллекции

        return "не реализовано";
    }
}
