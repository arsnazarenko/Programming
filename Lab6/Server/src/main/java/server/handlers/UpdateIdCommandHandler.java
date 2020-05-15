package server.handlers;

import library.clientCommands.Command;
import library.clientCommands.commandType.UpdateIdCommand;
import library.сlassModel.Organization;
import server.services.CollectionManager;
import server.services.FieldSetter;

import java.util.ArrayDeque;
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
        synchronized (collectionManager) {
            collectionManager.setOrgCollection(collectionManager.getOrgCollection().
                    stream().
                    map(o -> {
                        if (o.getId().equals(id)) {
                            Organization updateOrganization = fieldSetter.setId(updateIdCommand.getOrganization(), id);
                            updateOrganization.setCreationDate(o.getCreationDate());
                            stringBuilder.delete(0, stringBuilder.length()).append("Объект обновлён!");
                            return updateOrganization;
                        } else {
                            return o;
                        }
                    }).collect(Collectors.toCollection(ArrayDeque::new)));
        }

        return stringBuilder.toString();

    }
}
