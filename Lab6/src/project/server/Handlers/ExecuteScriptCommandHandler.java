package project.server.Handlers;

import project.client.commands.Command;

public class ExecuteScriptCommandHandler implements ICommandHandler {
    @Override
    public String processCommand(Command command) {
        //можно не кастовать, просто отправляем сообщение о том, что сервер получил команду запуска скрипта

        return "Запуск скрипта";
    }
}
