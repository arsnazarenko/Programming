package server.business.handlers;

import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.RemoveIdCommand;
import library.сlassModel.Organization;
import server.business.CollectionManager;
import server.business.dao.ObjectDAO;
import server.business.dao.UserDAO;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

public class RemoveIdCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;
    private UserDAO<UserData, String> usrDao;
    private ObjectDAO<Organization, Long, String> orgDao;

    public RemoveIdCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao, ObjectDAO<Organization, Long, String> orgDao) {
        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
        this.orgDao = orgDao;
    }

    @Override
    public String processCommand(Command command) {
        RemoveIdCommand removeIdCommand = (RemoveIdCommand) command;
        Long id = removeIdCommand.getId();
        synchronized (collectionManager) {
            int oldSize = collectionManager.getOrgCollection().size();
            Deque<Organization> newOrganizations = collectionManager.getOrgCollection().
                    stream().
                    filter(o1 -> !(o1.getId().equals(id))).
                    collect(Collectors.toCollection(ArrayDeque::new));


            if (newOrganizations.size() < oldSize) {
                collectionManager.setOrgCollection(newOrganizations);
                return "Команды выполнена";
            }
        }
        return "Объекта с таким ID нет";
    }
}
