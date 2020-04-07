package server.handlers;


import library.clientCommands.Command;

public interface ICommandHandler {
    Object processCommand(Command command);
}
