package project.server.handlers;

import project.client.commands.Command;
import project.server.CollectionManager;

public class HeadCommandHandler implements ICommandHandler {

    private CollectionManager  collectionManager;

    public HeadCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        //можно не кастовать к команде, просто выводим первый элемент списка

        return collectionManager.getOrgCollection().getFirst().toString();
    }
}
