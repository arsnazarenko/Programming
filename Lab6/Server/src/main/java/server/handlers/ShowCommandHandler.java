package server.handlers;


import library.clientCommands.Command;
import library.сlassModel.Organization;
import server.services.CollectionManager;

import java.util.Deque;


public class ShowCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public ShowCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Deque<Organization> processCommand(Command command) {

        return collectionManager.getOrgCollection().isEmpty()?null:collectionManager.getOrgCollection();
    }
}
