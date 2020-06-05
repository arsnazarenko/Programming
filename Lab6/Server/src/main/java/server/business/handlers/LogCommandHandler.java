package server.business.handlers;

import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.business.dao.UserDAO;

public class LogCommandHandler implements ICommandHandler {
    private UserDAO<UserData, String> usrDao;
    private static final Logger logger = LogManager.getLogger(LogCommandHandler.class.getName());
    public LogCommandHandler(UserDAO<UserData, String> usrDao) {
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        //если пользоватеть с таким логином найден в базе, пишем что логин занят;
        //иначе регистрируем пользователя
        UserData userData = command.getUserData();
        //если налл, то такого пользователя нет, регистрируемся
        if (authorization(command.getUserData(), usrDao) != 0) {
            return SpecialSignals.AUTHORIZATION_TRUE;
        } else {
            return SpecialSignals.AUTHORIZATION_FALSE;
        }
    }
}
