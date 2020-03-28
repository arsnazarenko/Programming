package project.server.Handlers;

import project.client.commands.Command;
import project.client.commands.commandType.FilterContainsNameCommand;
import project.server.CollectionManager;

public class FilterContainsNameCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public FilterContainsNameCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        FilterContainsNameCommand filterContainsNameCommand = (FilterContainsNameCommand) command;
        filterContainsNameCommand.getSubString();

        return "не реализовано";
    }
}
