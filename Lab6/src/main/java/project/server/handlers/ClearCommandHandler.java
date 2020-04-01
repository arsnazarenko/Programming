package project.server.handlers;

import project.client.commands.Command;
import project.server.services.CollectionManager;

public class ClearCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public ClearCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        collectionManager.getOrgCollection().clear();
        //можно не кастовать к классу команды, т к параметры нам не нужны

        return "Коллекция отчищена";
    }
}
