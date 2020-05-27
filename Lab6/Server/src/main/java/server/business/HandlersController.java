package server.business;



import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.*;
import library.сlassModel.Organization;
import server.business.dao.ObjectDAO;
import server.business.dao.UserDAO;
import server.business.handlers.*;

import java.util.HashMap;
import java.util.Map;

public class HandlersController implements IHandlersController {
    private Map<Class<?>, ICommandHandler> handlers;

    public HandlersController(CollectionManager collectionManager, ObjectDAO<Organization, Long> orgDao, UserDAO<UserData, String> usrDao) {
        this.handlers = new HashMap<Class<?>, ICommandHandler>() {{
            put(ExitCommand.class, new ExitCommandHandler(usrDao));
            put(AddCommand.class, new AddCommandHandler(collectionManager, orgDao, usrDao));
            put(InfoCommand.class, new InfoCommandHandler(collectionManager, usrDao));
            put(HelpCommand.class, new HelpCommandHandler());
            put(PrintAscendingCommand.class, new PrintAscendingCommandHandler(collectionManager, usrDao));
            put(RemoveIdCommand.class, new RemoveIdCommandHandler(collectionManager, usrDao, orgDao));
            put(RemoveLowerCommand.class, new RemoveLowerCommandHandler(collectionManager, usrDao, orgDao));
            put(AddIfMinCommand.class, new AddIfMinCommandHandler(collectionManager, orgDao, usrDao));
            put(FilterContainsNameCommand.class, new FilterContainsNameCommandHandler(usrDao, collectionManager));
            put(HeadCommand.class, new HeadCommandHandler(collectionManager, usrDao));
            put(MaxByEmployeeCommand.class, new MaxByEmployeeCommandHandler(collectionManager, usrDao));
            put(ShowCommand.class, new ShowCommandHandler(collectionManager, usrDao));
            put(UpdateIdCommand.class, new UpdateIdCommandHandler(collectionManager, usrDao, orgDao));
            put(ClearCommand.class, new ClearCommandHandler(collectionManager, orgDao, usrDao));
            put(ExecuteScriptCommand.class, new ExecuteScriptCommandHandler(usrDao));
            put(RegCommand.class, new RegCommandHandler(usrDao));
        }};
    }

    public Object handling(Command command) {
        return handlers.get(command.getClass()).processCommand(command);
    }

}
