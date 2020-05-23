package server.business.handlers;


import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;
import library.clientCommands.commandType.FilterContainsNameCommand;
import library.сlassModel.Organization;
import server.business.CollectionManager;
import server.business.dao.UserDAO;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

public class FilterContainsNameCommandHandler implements ICommandHandler {
    private UserDAO<UserData, String> usrDao;
    private CollectionManager collectionManager;

    public FilterContainsNameCommandHandler(UserDAO<UserData, String> usrDao, CollectionManager collectionManager) {
        this.usrDao = usrDao;
        this.collectionManager = collectionManager;
    }

    @Override
    public Object processCommand(Command command) {
        if(authorization(command.getUserData(), usrDao) != 0L) {

            FilterContainsNameCommand filterContainsNameCommand = (FilterContainsNameCommand) command;
            String subString = filterContainsNameCommand.getSubString();
            Deque<Organization> result = collectionManager.getOrgCollection().
                    stream().
                    filter(o -> o.getName().contains(subString)).
                    collect(Collectors.toCollection(ArrayDeque::new));
            return result.isEmpty() ? null : result;
        }
        return SpecialSignals.AUTHORIZATION_FALSE;

    }
}
