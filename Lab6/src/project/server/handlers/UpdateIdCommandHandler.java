package project.server.handlers;

import project.client.commands.Command;
import project.client.commands.commandType.UpdateIdCommand;
import project.server.CollectionManager;
import project.server.FieldSetter;


public class UpdateIdCommandHandler implements ICommandHandler {
    private FieldSetter fieldSetter;
    private CollectionManager collectionManager;

    public UpdateIdCommandHandler(CollectionManager collectionManager, FieldSetter fieldSetter) {
        this.collectionManager = collectionManager;
        this.fieldSetter = fieldSetter;
    }

    @Override
    public String processCommand(Command command) {
        UpdateIdCommand updateIdCommand= (UpdateIdCommand) command;
        long id = updateIdCommand.getId();

        collectionManager.getOrgCollection().stream().filter(o -> o.getId().equals(id));


        return "не реализовано";
    }
}
