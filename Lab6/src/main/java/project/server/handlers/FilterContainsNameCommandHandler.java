package project.server.handlers;

import project.client.commands.Command;
import project.client.commands.commandType.FilterContainsNameCommand;
import project.server.services.CollectionManager;

public class FilterContainsNameCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public FilterContainsNameCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        StringBuilder stringBuilder = new StringBuilder();
        FilterContainsNameCommand filterContainsNameCommand = (FilterContainsNameCommand) command;
        String subString = filterContainsNameCommand.getSubString();
        collectionManager.getOrgCollection().stream().filter((o) -> o.getName().contains(subString)).forEach(o -> stringBuilder.append(o + "\n"));
        return stringBuilder.toString();
    }
}
