package project.server.Handlers;

import project.client.commands.Command;

public interface ICommandHandler {
    void processCommand(Command command);
}
