package project.server.handlers;

import project.client.commands.Command;
import project.client.commands.commandType.RemoveLowerCommand;
import project.client.сlassModel.Organization;
import project.server.CollectionManager;
import project.server.FieldSetter;

import java.util.Deque;

public class RemoveLowerCommandHandler implements ICommandHandler {
    private FieldSetter fieldSetter;
    private CollectionManager collectionManager;

    public RemoveLowerCommandHandler(CollectionManager collectionManager, FieldSetter fieldSetter) {
        this.collectionManager = collectionManager;
        this.fieldSetter = fieldSetter;
    }

    @Override
    public String processCommand(Command command) {
        RemoveLowerCommand removeLowerCommand = (RemoveLowerCommand) command;
        Organization organization = fieldSetter.setDateNow(removeLowerCommand.getOrganization());
        int oldSize = collectionManager.getOrgCollection().size();
        collectionManager.getOrgCollection().removeIf(o -> o.compareTo(organization) < 0);
        if (collectionManager.getOrgCollection().size() < oldSize) {return "Команда выполнена";} else {return "Объектов меньшет нет!";}
    }
}
