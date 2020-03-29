package project.server.handlers;

import project.client.commands.Command;
import project.server.CollectionManager;

public class ShowCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public ShowCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        StringBuilder stringBuilder = new StringBuilder();
        collectionManager.getOrgCollection().forEach((o) -> stringBuilder.append(o + "\n"));


        return stringBuilder.toString();
    }
}
