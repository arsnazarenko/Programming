package project.server.handlers;

import project.client.commands.Command;
import project.client.сlassModel.Organization;
import project.server.services.CollectionManager;

import java.util.Comparator;

public class PrintAscendingCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public PrintAscendingCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        StringBuilder stringBuilder = new StringBuilder();
        collectionManager.getOrgCollection().stream().sorted(Comparator.comparing(Organization::getCreationDate)).forEach(o1 -> stringBuilder.append(o1 + "\n"));
        return stringBuilder.toString();
    }
}
