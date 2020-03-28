package project.server.Handlers;

import project.client.commands.Command;
import project.server.CollectionManager;

public class PrintAscendingCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public PrintAscendingCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        //возвращаем коллекцию отсортированную

        return "не реализовано";
    }
}
