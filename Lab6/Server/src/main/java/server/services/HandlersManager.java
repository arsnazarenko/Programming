package server.services;


import library.clientCommands.Command;
import library.clientCommands.commandType.*;
import server.handlers.*;

import java.util.HashMap;
import java.util.Map;

public class HandlersManager implements IHandlersManager{
    private CollectionManager collectionManager;
    private Map<Class<?>, ICommandHandler> handlers;
    private FieldSetter fieldSetter;

    public HandlersManager(CollectionManager collectionManager, FieldSetter fieldSetter) {
        this.collectionManager = collectionManager;
        this.fieldSetter = fieldSetter;
        this.handlers = new HashMap<Class<?>, ICommandHandler>() {{
            put(ExitCommand.class, new ExitCommandHandler());
            put(AddCommand.class, new AddCommandHandler(collectionManager, fieldSetter));
            put(InfoCommand.class, new InfoCommandHandler(collectionManager));
            put(HelpCommand.class, new HelpCommandHandler());
            put(PrintAscendingCommand.class, new PrintAscendingCommandHandler(collectionManager));
            put(RemoveIdCommand.class, new RemoveIdCommandHandler(collectionManager));
            put(RemoveLowerCommand.class, new RemoveLowerCommandHandler(collectionManager, fieldSetter));
            put(AddIfMinCommand.class, new AddIfMinCommandHandler(collectionManager, fieldSetter));
            put(FilterContainsNameCommand.class, new FilterContainsNameCommandHandler(collectionManager));
            put(HeadCommand.class, new HeadCommandHandler(collectionManager));
            put(MaxByEmployeeCommand.class, new MaxByEmployeeCommandHandler(collectionManager));
            put(SaveCommand.class, new SaveCommandHandler(collectionManager));
            put(ShowCommand.class, new ShowCommandHandler(collectionManager));
            put(UpdateIdCommand.class, new UpdateIdCommandHandler(collectionManager, fieldSetter));
            put(ClearCommand.class, new ClearCommandHandler(collectionManager));
            put(ExecuteScriptCommand.class, new ExecuteScriptCommandHandler());
        }};
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Map<Class<?>, ICommandHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<Class<?>, ICommandHandler> handlers) {
        this.handlers = handlers;
    }

    public FieldSetter getFieldSetter() {
        return fieldSetter;
    }

    public void setFieldSetter(FieldSetter fieldSetter) {
        this.fieldSetter = fieldSetter;
    }

    public Object handling(Command command) {
        return handlers.get(command.getClass()).processCommand(command);
    }

}
