package project.server.handlers;

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
        Long id = removeIdCommand.getId();
        collectionManager.getOrgCollection().removeIf((o1) -> o1.getId().equals(id));
        return "Команды выполнена";
    }
}
