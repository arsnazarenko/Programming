package project.server.Handlers;

import project.client.commands.Command;
import project.client.commands.commandType.RemoveIdCommand;
import project.server.CollectionManager;

public class RemoveIdCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public RemoveIdCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        RemoveIdCommand removeIdCommand = (RemoveIdCommand) command;
        removeIdCommand.getId();

        return "не реализовано";
    }
}
