package project.server.handlers;

import project.client.commands.Command;
import project.client.commands.commandType.RemoveLowerCommand;
import project.client.сlassModel.Organization;
import project.server.CollectionManager;

import java.util.Deque;

public class RemoveLowerCommandHandler implements ICommandHandler {

    private CollectionManager collectionManager;

    public RemoveLowerCommandHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String processCommand(Command command) {
        RemoveLowerCommand removeLowerCommand = (RemoveLowerCommand) command;
        Organization organization = removeLowerCommand.getOrganization();
         collectionManager.getOrgCollection().removeIf(o -> o.compareTo(organization) < 0);
        return "не реализовано";
    }
}
