package server.business.handlers;

import library.clientCommands.Command;
import library.clientCommands.UserData;
import server.business.dao.OrganizationDAO;
import server.business.dao.UserDAO;

public class RegCommandHandler implements ICommandHandler {
    private UserDAO<UserData, String> usrDao;

    public RegCommandHandler(UserDAO<UserData, String> usrDao) {
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        //если пользоватеть с таким логином найден в базе, пишем что логин занят;
        //иначе регистрируем пользователя
        UserData userData = command.getUserData();
        //если налл, то такого пользователя нет, регистрируемся
        if (usrDao.read(userData.getLogin()).getValue() < 1) {
            usrDao.create(userData);
            return "Пользователь " + userData.getLogin() + " зарегестрирован";
        } else {
            return "Пользователь с логином " + userData.getLogin() + " уже найден";
        }
    }
}
