package project.server.handlers;

import project.client.commands.Command;

public interface ICommandHandler {
    String processCommand(Command command);
}
