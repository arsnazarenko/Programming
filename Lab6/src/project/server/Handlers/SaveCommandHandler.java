package project.server.Handlers;

import project.client.commands.Command;
import project.server.CollectionManager;

public class SaveCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public SaveCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        //берем нашу коллекцию, маршаллером преобразуем ее к XML, сохраняем на файл


        return "не реализовано";
    }
}
