package project.server.Handlers;

import project.client.commands.Command;
import project.client.commands.commandType.AddIfMinCommand;
import project.server.CollectionManager;
import project.server.FieldSetter;

public class AddIfMinCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;
    private FieldSetter fieldSetter;

    public AddIfMinCommandHandler(CollectionManager collectionManager, FieldSetter fieldSetter) {
        this.collectionManager = collectionManager;
        this.fieldSetter = fieldSetter;
    }

    @Override
    public String processCommand(Command command) {
        AddIfMinCommand addIfMinCommand = (AddIfMinCommand) command;
        addIfMinCommand.getOrganization();
        //то же самое, что адд, но добавляем в случае , если элемент меньше других

        return "не реализовано";
    }
}
