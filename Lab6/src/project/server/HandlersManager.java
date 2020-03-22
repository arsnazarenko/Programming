package project.server;

import project.client.commands.Command;
import project.client.commands.commandType.*;
import project.server.Handlers.*;

import java.util.HashMap;
import java.util.Map;

public class HandlersManager {
    private Map<Class<?>, ICommandHandler> handlers = new HashMap<Class<?>, ICommandHandler>() {{
        put(ExitCommand.class, new ExitCommandHandler());
        put(AddCommand.class, new AddCommandHandler());
        put(InfoCommand.class, new InfoCommandHandler());
        put(HelpCommand.class, new HelpCommandHandler());
        put(PrintAscendingCommand.class, new PrintAscendingCommandHandler());
        put(RemoveIdCommand.class, new RemoveIdCommandHandler());
        put(RemoveLowerCommand.class, new RemoveLowerCommandHandler());
        put(AddIfMinCommand.class, new AddIfMinCommandHandler());
        put(FilterContainsNameCommand.class, new FilterContainsNameCommandHandler());
        put(HeadCommand.class, new HeadCommandHandler());
        put(LoadCommand.class, new LoadCommandHandler());
        put(MaxByEmployeeCommand.class, new MaxByEmployeeCommandHandler());
        put(SaveCommand.class, new SaveCommandHandler());
        put(ShowCommand.class, new ShowCommandHandler());
        put(UpdateIdCommand.class, new UpdateIdCommandHandler());
        put(ClearCommand.class, new ClearCommandHandler());
    }};


    public void handling(Command command) {
        handlers.get(command.getClass()).processCommand(command);
    }

    //метод, который выполняет метод commandProcess, возвращает строку - ответ каждой команды
}
