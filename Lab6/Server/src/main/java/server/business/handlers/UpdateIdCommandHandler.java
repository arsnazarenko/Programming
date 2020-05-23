package server.business.handlers;

import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.UpdateIdCommand;
import library.сlassModel.Organization;
import server.business.CollectionManager;
import server.business.dao.ObjectDAO;
import server.business.dao.UserDAO;

import java.util.ArrayDeque;
import java.util.stream.Collectors;


public class UpdateIdCommandHandler implements ICommandHandler {
    private CollectionManager collectionManager;
    private UserDAO<UserData, String> usrDao;
    private ObjectDAO<Organization, Long, String> orgDao;

    public UpdateIdCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao, ObjectDAO<Organization, Long, String> orgDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
        this.orgDao = orgDao;
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
                            Organization updateOrganization = updateIdCommand.getOrganization();
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
