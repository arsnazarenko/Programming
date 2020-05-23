package server.business.handlers;

import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.clientCommands.commandType.RemoveLowerCommand;
import library.сlassModel.Organization;
import org.apache.logging.log4j.core.util.JsonUtils;
import server.business.CollectionManager;
import server.business.dao.ObjectDAO;
import server.business.dao.UserDAO;

import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveLowerCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;
    private UserDAO<UserData, String> usrDao;
    private ObjectDAO<Organization, Long, String> orgDao;

    public RemoveLowerCommandHandler(CollectionManager collectionManager, UserDAO<UserData, String> usrDao, ObjectDAO<Organization, Long, String> orgDao) {

        this.collectionManager = collectionManager;
        this.usrDao = usrDao;
        this.orgDao = orgDao;
    }

    @Override
    public Object processCommand(Command command) {
        System.out.println("выполнение");
        if(authorization(command.getUserData(), usrDao) != 0) {
            System.out.println("вход");
            RemoveLowerCommand removeLowerCommand = (RemoveLowerCommand) command;
            Organization organization = removeLowerCommand.getOrganization();
            List<Long> ids = lower(organization);
            //если объекты меньше есть
            if(!ids.isEmpty()) {
                //если удаление из базы прошло успешно, обновляем коллекцию
                if(orgDao.deleteByKeys(ids)) {
                    synchronized (collectionManager) {
                        collectionManager.getOrgCollection().removeIf(o -> ids.contains(o.getId()));
                        return "ОБъекты удалены";
                    }
                }
            }
            return "Объекты не удалены";
        }
        return SpecialSignals.AUTHORIZATION_FALSE;
    }


    private List<Long> lower(Organization organization) {
        System.out.println("lower");
        List<Long> orgId = collectionManager.getOrgCollection().stream().filter(o -> o.compareTo(organization) < 0).map(Organization::getId).collect(Collectors.toList());
        System.out.println(orgId);
        return orgId;
    }
}
