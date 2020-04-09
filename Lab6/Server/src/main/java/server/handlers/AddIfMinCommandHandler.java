package server.handlers;


import library.clientCommands.Command;
import library.clientCommands.commandType.AddIfMinCommand;
import library.сlassModel.Organization;
import server.services.CollectionManager;
import server.services.FieldSetter;

import java.util.Comparator;

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
        Organization organization = fieldSetter.setDateNow(addIfMinCommand.getOrganization());
        StringBuilder result = new StringBuilder("Коллекция пуста");

        collectionManager.getOrgCollection().
                stream().
                min(Comparator.comparing(Organization::getCreationDate)).
                ifPresent(
                        o -> {
                            if (o.compareTo(organization) > 0) {
                                collectionManager.getOrgCollection().
                                        add(fieldSetter.setId(organization, ++CollectionManager.OBJECT_ID_COUNTER));
                                result.delete(0, result.length()).append("Объект добавлен");
                            } else {
                                result.delete(0, result.length()).append("Объект не добавлен");
                            }

                        }

                );


        return result.toString();
    }

}