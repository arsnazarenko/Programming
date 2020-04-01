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
    public String processCommand(Command command) {
        //можно не кастовать к команде, просто выводим первый элемент списка

        return collectionManager.getOrgCollection().stream().findFirst().map(Organization::toString).orElse("Коллекция пуста");
    }
}
