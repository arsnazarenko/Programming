package project.server.handlers;

import project.client.commands.Command;
import project.client.commands.commandType.AddIfMinCommand;
import project.client.сlassModel.Organization;
import project.server.CollectionManager;
import project.server.FieldSetter;

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
        try {
            boolean flag = (collectionManager.getOrgCollection().stream().
                    min(Comparator.comparing(Organization::getCreationDate)).
                    get().
                    compareTo(organization) > 0);
            if (flag) {
                fieldSetter.setId(organization, ++CollectionManager.OBJECT_ID_COUNTER);
                collectionManager.getOrgCollection().add(organization);
                return "Объект добавлен";
            } else {
                return "Объект не добавлен";
            }

        } catch (NullPointerException e) {
            return "Коллекция пуста";
        }



    }
    //то же самое, что адд, но добавляем в случае , если элемент меньше других

}
