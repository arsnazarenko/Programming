package server.handlers;

import library.clientCommands.Command;
import library.сlassModel.Organization;
import server.services.CollectionManager;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.stream.Collectors;

public class PrintAscendingCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public PrintAscendingCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Deque<Organization> processCommand(Command command) {
        Deque<Organization> result = collectionManager.getOrgCollection().
                stream().
                sorted(Comparator.comparing(Organization::getCreationDate)).
                collect(Collectors.toCollection(ArrayDeque::new));
        return result.isEmpty() ? null : result;
    }
}
