package project.server.handlers;

import project.client.commands.Command;
import project.client.commands.commandType.FilterContainsNameCommand;
import project.client.сlassModel.Organization;
import project.server.services.CollectionManager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class FilterContainsNameCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public FilterContainsNameCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Deque<Organization> processCommand(Command command) {

        FilterContainsNameCommand filterContainsNameCommand = (FilterContainsNameCommand) command;
        String subString = filterContainsNameCommand.getSubString();
        Deque<Organization> result = collectionManager.getOrgCollection().
                stream().
                filter(o -> o.getName().contains(subString)).
                collect(Collectors.toCollection(ArrayDeque::new));
        return result.isEmpty() ? null : result;
    }
}
