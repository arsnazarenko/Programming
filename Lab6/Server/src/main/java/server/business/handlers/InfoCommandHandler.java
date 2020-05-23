package server.business.handlers;


import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import server.business.CollectionManager;
import server.business.dao.UserDAO;

public class InfoCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;
    private UserDAO<UserData, String> usrDao;

    public InfoCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        if(authorization(command.getUserData(), usrDao) != 0L) {

            return "Тип: " + collectionManager.getOrgCollection().getClass() +
                    "\nКоличество элементов: " + collectionManager.getOrgCollection().size() +
                    "\nДата инициализации: " + collectionManager.getCreationCollectionDate() + "\n";
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
        //информация о колллекции


    }
}
