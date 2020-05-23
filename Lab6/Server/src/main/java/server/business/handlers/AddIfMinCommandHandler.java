package server.business.handlers;


import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.clientCommands.commandType.AddIfMinCommand;
import library.сlassModel.Organization;
import server.business.CollectionManager;
import server.business.dao.ObjectDAO;
import server.business.dao.UserDAO;

import java.util.Comparator;
import java.util.Date;

public class AddIfMinCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;
    private ObjectDAO<Organization, Long, String> orgDao;
    private UserDAO<UserData, String> usrDao;

    public AddIfMinCommandHandler(CollectionManager collectionManager, ObjectDAO<Organization, Long, String> orgDao, UserDAO<UserData, String> usrDao) {
        this.collectionManager = collectionManager;
        this.orgDao = orgDao;
        this.usrDao = usrDao;
    }

    @Override
    public Object processCommand(Command command) {
        AddIfMinCommand addIfMinCommand = (AddIfMinCommand) command;
        Organization organization = addIfMinCommand.getOrganization();
        organization.setCreationDate(new Date());
        UserData userData = command.getUserData();
        Long userId = authorization(userData, usrDao); //если в базе есть, то не 0

        //авторизация
        if (userId != 0L) {
            //проверка, минимальный ли объект для добавления
            if (ifMin(organization) == true) {
                //добавляем в базу, с нужным id юзера из таблицы и получаем id объекта из таблицы объектов
                Long objectId = orgDao.create(organization, userId);
                //если добавление успешно то не 0, добавляем в колллекцию
                if (objectId != 0L) {
                    //устанавливаем id
                    organization.setUserLogin(userData.getLogin());
                    organization.setId(objectId);
                    synchronized (collectionManager) {
                        collectionManager.getOrgCollection().addLast(organization);
                        return "Объект добавлен";
                    }

                }
            }
            return "Объект не добавлен";
        }
        return SpecialSignals.AUTHORIZATION_FALSE;


    }

    public boolean ifMin(Organization org) {
        boolean[] result = new boolean[1];
        result[0] = true; //изначально тру, это если коллекция пустая, добавляем всегда
        //если оно не наименьшее, то оно поставит false
        collectionManager.getOrgCollection().
                stream().
                min(Comparator.comparing(Organization::getCreationDate)).
                //не выполнится, если элементов нет
                        ifPresent(o -> {
                            if (!(o.compareTo(org) > 0)) {
                                result[0] = false;
                            }
                        }

                );
        return result[0];
    }

}
