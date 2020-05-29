package server.business.handlers;


import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.сlassModel.Organization;
import server.business.CollectionManager;
import server.business.dao.UserDAO;

import java.util.Comparator;

public class MaxByEmployeeCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;
    private UserDAO<UserData, String> usrDao;

    public MaxByEmployeeCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        if (authorization(command.getUserData(), usrDao) != 0L) {
            synchronized (collectionManager) {
                return collectionManager.getOrgCollection().
                        stream().
                        max(Comparator.comparing(Organization::getCreationDate)).
                        orElse(null);
            }
        }
        return SpecialSignals.AUTHORIZATION_FALSE;

        //выводим элемент с наибольшим показателем количества работников
    }
}
