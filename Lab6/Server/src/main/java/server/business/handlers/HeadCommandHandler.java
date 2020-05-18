package server.business.handlers;

import library.clientCommands.Command;
import library.сlassModel.Organization;
import server.business.CollectionManager;

public class HeadCommandHandler implements ICommandHandler {

    private CollectionManager  collectionManager;

    public HeadCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Organization processCommand(Command command) {

        return collectionManager.getOrgCollection().
                stream().
                findFirst().
                orElse(null);
    }
}
