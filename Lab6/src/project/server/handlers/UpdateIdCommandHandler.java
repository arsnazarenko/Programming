package project.server.handlers;

import project.client.commands.Command;
import project.client.commands.commandType.UpdateIdCommand;
import project.client.сlassModel.Organization;
import project.server.CollectionManager;
import project.server.FieldSetter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;


public class UpdateIdCommandHandler implements ICommandHandler {
    private FieldSetter fieldSetter;
    private CollectionManager collectionManager;

    public UpdateIdCommandHandler(CollectionManager collectionManager, FieldSetter fieldSetter) {
        this.collectionManager = collectionManager;
        this.fieldSetter = fieldSetter;
    }

    @Override
    public String processCommand(Command command) {
        UpdateIdCommand updateIdCommand = (UpdateIdCommand) command;
        long id = updateIdCommand.getId();
        StringBuilder stringBuilder = new StringBuilder("Элементов с таким ID нет");
        collectionManager.setOrgCollection(collectionManager.getOrgCollection().stream().map(o -> {if (o.getId().equals(id)) {
            Organization updateOrganization = fieldSetter.setId(updateIdCommand.getOrganization(), id);
            updateOrganization.setCreationDate(o.getCreationDate());
            stringBuilder.delete(0, stringBuilder.length()).append("Объект обновлён!");
            return updateOrganization;
        } else { return o; }
        }).collect(Collectors.toCollection(ArrayDeque::new)));

        return stringBuilder.toString();

    }
}
