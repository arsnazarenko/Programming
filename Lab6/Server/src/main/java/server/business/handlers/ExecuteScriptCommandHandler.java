package server.business.handlers;


import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import server.business.dao.UserDAO;

public class ExecuteScriptCommandHandler implements ICommandHandler {
    private UserDAO<UserData, String> usrDao;

    public ExecuteScriptCommandHandler(UserDAO<UserData, String> usrDao) {
        this.usrDao = usrDao;
    }

    @Override
    public SpecialSignals processCommand(Command command) {
        //можно не кастовать, просто отправляем сообщение о том, что сервер получил команду запуска скрипта
        if (authorization(command.getUserData(), usrDao) != 0L) {
            return SpecialSignals.SCRIPT_TRUE;
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }
}
