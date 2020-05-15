package server.handlers;

import library.clientCommands.Command;
import server.services.CollectionManager;

public class ClearCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public ClearCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        synchronized (collectionManager) {
            collectionManager.getOrgCollection().clear();
        }
        //можно не кастовать к классу команды, т к параметры нам не нужны
        return "Коллекция отчищена от объектов";
    }
}
