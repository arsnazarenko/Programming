package project.server.handlers;

import project.client.commands.Command;

public interface ICommandHandler {
    Object processCommand(Command command);
}
