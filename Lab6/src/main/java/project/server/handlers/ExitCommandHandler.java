package project.server.handlers;

import project.client.commands.Command;

public class ExitCommandHandler implements ICommandHandler {
    @Override
    public String processCommand(Command command) {
        //сервер получил команду закрытия клиента

        return "ЗАВЕРШЕНИЕ...";
    }
}
