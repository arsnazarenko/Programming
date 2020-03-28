package project.server.Handlers;

import project.client.commands.Command;
import project.client.commands.commandType.MaxByEmployeeCommand;
import project.server.CollectionManager;

public class MaxByEmployeeCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public MaxByEmployeeCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        //выводим элемент с наибольшим показателем количества работников

        return "не реализовано";
    }
}
