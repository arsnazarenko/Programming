package project.server.handlers;

import project.client.commands.Command;
import project.client.сlassModel.Organization;
import project.server.services.CollectionManager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

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
