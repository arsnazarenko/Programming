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
            Organization organization = null;
            synchronized (collectionManager) {
                organization = collectionManager.getOrgCollection().
                        stream().
                        max(Comparator.comparing(Organization::getCreationDate)).
                        orElse(null);
            }
            if(organization != null) {
                return organization;
            }
            return "Объектов не найдено";
        }
        return SpecialSignals.AUTHORIZATION_FALSE;

        //выводим элемент с наибольшим показателем количества работников
    }
}
