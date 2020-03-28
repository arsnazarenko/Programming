package project.server.Handlers;

import project.client.commands.Command;
import project.server.CollectionManager;

public class ShowCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public ShowCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {


        return collectionManager.getOrgCollection().toString();
    }
}
