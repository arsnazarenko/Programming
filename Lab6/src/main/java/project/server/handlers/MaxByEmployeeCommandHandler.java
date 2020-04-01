package project.server.handlers;

import project.client.commands.Command;
import project.client.сlassModel.Organization;
import project.server.services.CollectionManager;

import java.util.Comparator;

public class MaxByEmployeeCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public MaxByEmployeeCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        String result;
        result = collectionManager.getOrgCollection().stream().max(Comparator.comparing(Organization::getCreationDate)).map(Organization::toString).orElse("Коллекция пуста");
        //выводим элемент с наибольшим показателем количества работников
        return result;
    }
}
