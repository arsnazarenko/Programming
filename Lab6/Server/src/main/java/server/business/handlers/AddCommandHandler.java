package server.business.handlers;


import library.clientCommands.Command;

import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.clientCommands.commandType.AddCommand;
import library.сlassModel.Organization;
import server.business.CollectionManager;
import server.business.dao.ObjectDAO;
import server.business.dao.UserDAO;

import java.util.Date;

public class AddCommandHandler implements ICommandHandler {
    private CollectionManager collectionManager;
    private ObjectDAO<Organization, Long> orgDao;
    private UserDAO<UserData, String> usrDao;

    public AddCommandHandler(CollectionManager collectionManager, ObjectDAO<Organization, Long> orgDao, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.orgDao = orgDao;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        AddCommand addCommand = (AddCommand) command;
        UserData userData = command.getUserData();
        Long userId = authorization(command.getUserData(), usrDao);
        if (userId != 0L) {
            Organization organization = addCommand.getOrganization();
            organization.setUserLogin(userData.getLogin());
            organization.setCreationDate(new Date());
            Long objectId = orgDao.create(organization, userId);
            if (objectId != 0) {
                organization.setId(objectId);
                synchronized (collectionManager) {
                    collectionManager.getOrgCollection().addLast(organization);
                }
                return "Объект добавлен";
            }
            return "Объект не добавлен";
        }
        return SpecialSignals.AUTHORIZATION_FALSE;

    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
