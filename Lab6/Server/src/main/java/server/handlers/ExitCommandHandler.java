package server.handlers;


import library.clientCommands.Command;

public class ExitCommandHandler implements ICommandHandler {
    @Override
    public String processCommand(Command command) {
        //сервер получил команду закрытия клиента

        return "ЗАВЕРШЕНИЕ...";
    }
}
