package project.server.handlers;

import project.client.commands.Command;
import project.client.commands.commandType.AddCommand;
import project.client.сlassModel.Organization;
import project.server.CollectionManager;
import project.server.FieldSetter;

public class AddCommandHandler implements ICommandHandler {
    private FieldSetter fieldSetter;
    private CollectionManager collectionManager;

    public AddCommandHandler(CollectionManager collectionManager, FieldSetter fieldSetter) {
        this.collectionManager = collectionManager;
        this.fieldSetter = fieldSetter;
    }

    @Override
    public String processCommand(Command command) {
        AddCommand addCommand = (AddCommand) command;
        Organization organization = fieldSetter.setDateNow(
                fieldSetter.setId(addCommand.getOrganization(), ++CollectionManager.OBJECT_ID_COUNTER));
        //сделай настройку ай ди при добавлении объектов при запумке сервера
        collectionManager.getOrgCollection().addLast(organization);

        return "Объект добавлен";
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
