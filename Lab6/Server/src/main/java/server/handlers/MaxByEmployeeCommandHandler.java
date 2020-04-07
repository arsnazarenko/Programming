package server.handlers;


import library.clientCommands.Command;
import library.сlassModel.Organization;
import server.services.CollectionManager;

import java.util.Comparator;

public class MaxByEmployeeCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public MaxByEmployeeCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Organization processCommand(Command command) {
        return collectionManager.getOrgCollection().
                stream().
                max(Comparator.comparing(Organization::getCreationDate)).
                orElse(null);
        //выводим элемент с наибольшим показателем количества работников
    }
}
