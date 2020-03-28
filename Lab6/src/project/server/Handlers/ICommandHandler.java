package project.server.Handlers;

import project.client.commands.Command;

public interface ICommandHandler {
    String processCommand(Command command);
}
