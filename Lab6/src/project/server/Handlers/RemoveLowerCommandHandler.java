package project.server.Handlers;

import project.client.commands.Command;
import project.server.CollectionManager;

public class RemoveLowerCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public RemoveLowerCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        //удаляем наименьший элемент коллекции

        return "не реализовано";
    }
}
