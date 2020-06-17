package server.business.handlers;


import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import server.business.dao.UserDAO;

public class ExitCommandHandler implements ICommandHandler {
    private UserDAO<UserData, String> usrDao;

    public ExitCommandHandler(UserDAO<UserData, String> usrDao) {
        this.usrDao = usrDao;
    }

    @Override

    public Object processCommand(Command command) {
        if (authorization(command.getUserData(), usrDao) != 0L) {
            return "Завершение";
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }
}
