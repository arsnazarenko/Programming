package project.server.handlers;

import project.client.commands.Command;
import project.client.сlassModel.Organization;
import project.server.CollectionManager;

import java.util.Comparator;

public class MaxByEmployeeCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public MaxByEmployeeCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        String result = collectionManager.getOrgCollection().stream().sorted(Comparator.comparing(o -> - o.getEmployeesCount())).
                limit(1).
                map(Organization::toString).
                findFirst().
                get();
        //выводим элемент с наибольшим показателем количества работников

        return result;
    }
}
