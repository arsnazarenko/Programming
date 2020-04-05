package project.server.handlers;

import project.client.commands.Command;
import project.client.сlassModel.Organization;
import project.server.services.CollectionManager;

public class HeadCommandHandler implements ICommandHandler {

    private CollectionManager  collectionManager;

    public HeadCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Object processCommand(Command command) {

        return collectionManager.getOrgCollection().stream().findFirst().orElse(null) + "\n";
    }
}
