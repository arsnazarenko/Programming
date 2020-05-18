package server.business.handlers;

import library.clientCommands.Command;
import library.clientCommands.commandType.RemoveLowerCommand;
import library.сlassModel.Organization;
import server.business.CollectionManager;
import server.business.FieldSetter;

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
        synchronized (collectionManager) {
            int oldSize = collectionManager.getOrgCollection().size();
            collectionManager.getOrgCollection().removeIf(o -> o.compareTo(organization) < 0);
            if (collectionManager.getOrgCollection().size() < oldSize) {
                return "Команда выполнена";
            }
        }
        return "Объектов меньшет нет!";
    }
}
