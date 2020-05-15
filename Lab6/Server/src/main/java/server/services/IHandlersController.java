package server.services;


import library.clientCommands.Command;

public interface IHandlersController {
    Object handling(Command command);
}
