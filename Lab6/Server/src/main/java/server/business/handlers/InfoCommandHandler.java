package server.business.handlers;


import library.clientCommands.Command;
import server.business.CollectionManager;

public class InfoCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public InfoCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        //информация о колллекции

        return "Тип: " + collectionManager.getOrgCollection().getClass() +
                "\nКоличество элементов: " + collectionManager.getOrgCollection().size() +
                "\nДата инициализации: " + collectionManager.getCreationCollectionDate() + "\n";

    }
}
