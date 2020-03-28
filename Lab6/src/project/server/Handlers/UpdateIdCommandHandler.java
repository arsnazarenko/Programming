package project.server.Handlers;

import project.client.commands.Command;
import project.client.commands.commandType.UpdateIdCommand;
import project.server.CollectionManager;
import project.server.FieldSetter;


public class UpdateIdCommandHandler implements ICommandHandler {
    private FieldSetter fieldSetter;
    private CollectionManager collectionManager;

    public UpdateIdCommandHandler(CollectionManager collectionManager, FieldSetter fieldSetter) {
        this.collectionManager = collectionManager;
        this.fieldSetter = fieldSetter;
    }

    @Override
    public String processCommand(Command command) {
        UpdateIdCommand updateIdCommand= (UpdateIdCommand) command;
        long id = updateIdCommand.getId();
        //ищем в коллекции такой ай ди , еслли нет отправляем строку что нет
        //если да, сеттим поле ай ди этим самым значение и заменяем тот элемент нашимж


        return "не реализовано";
    }
}
