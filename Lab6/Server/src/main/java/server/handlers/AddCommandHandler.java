package server.handlers;


import library.clientCommands.Command;

import library.clientCommands.commandType.AddCommand;
import library.сlassModel.Organization;
import server.services.CollectionManager;
import server.services.FieldSetter;

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
        //сделай настройку ай ди при добавлении объектов при запуcке сервера
        synchronized (collectionManager) {
            collectionManager.getOrgCollection().addLast(organization);
        }
        return "Объект добавлен";
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
