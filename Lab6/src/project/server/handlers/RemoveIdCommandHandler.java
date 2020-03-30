package project.server.handlers;

import project.client.commands.Command;
import project.client.commands.commandType.RemoveIdCommand;
import project.client.сlassModel.Organization;
import project.server.CollectionManager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RemoveIdCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public RemoveIdCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        RemoveIdCommand removeIdCommand = (RemoveIdCommand) command;
        Long id = removeIdCommand.getId();
        int oldSize = collectionManager.getOrgCollection().size();
        //для разнообразия вместо removeIf, хотя и не оптимально в плане памяти
        Deque<Organization> newOrganizations = collectionManager.getOrgCollection().stream().filter(o1 -> !(o1.getId().equals(id))).collect(Collectors.toCollection(ArrayDeque::new));

        if (newOrganizations.size() < oldSize) {
            collectionManager.setOrgCollection(newOrganizations);
            return "Команды выполнена";
        } else {
            return "Объекта с таким ID нет";
        }
    }
}
