package server.business.handlers;

import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.сlassModel.Organization;
import server.business.CollectionManager;
import server.business.dao.ObjectDAO;
import server.business.dao.OrganizationDAO;
import server.business.dao.UserDAO;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class ClearCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;
    private ObjectDAO<Organization, Long, String> orgDao;
    private UserDAO<UserData, String> usrDao;

    public ClearCommandHandler(CollectionManager collectionManager, ObjectDAO<Organization, Long, String> orgDao, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.orgDao = orgDao;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        UserData userData = command.getUserData();
        if (authorization(userData, usrDao) != 0L) {
            List<Long> usersOrgId = collectionManager.getOrgCollection().stream().filter(o -> o.getUserLogin().equals(userData.getLogin())).map(Organization::getId).collect(Collectors.toList());
            if (!usersOrgId.isEmpty()) {
                //делаем изменения в базе, если они успешно сделаны, изменяем коллекцию
                if(orgDao.deleteByKeys(usersOrgId)) {
                    synchronized (collectionManager) {
                        //удаляем в коллекции
                        collectionManager.getOrgCollection().removeIf(o -> usersOrgId.contains(o.getId()));
                        return "Объекты удалены";
                    }
                }
            }
            return "Объектов не найдено";
        }
        return SpecialSignals.AUTHORIZATION_FALSE;

        //можно не кастовать к классу команды, т к параметры нам не нужны
    }
}
