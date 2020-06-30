package server.business.handlers;

import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.сlassModel.Organization;
import server.business.CollectionManager;
import server.business.dao.UserDAO;

public class HeadCommandHandler implements ICommandHandler {

    private CollectionManager  collectionManager;
    private UserDAO<UserData, String> usrDao;

    public HeadCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        if(authorization(command.getUserData(), usrDao) != 0L) {
            Organization organization = null;
            synchronized (collectionManager) {
                organization = collectionManager.getOrgCollection().
                        stream().
                        findFirst().
                        orElse(null);
            }
            if (organization != null) {
                return organization;
            }
            return "Oбъектов не найдено";

        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }
}
