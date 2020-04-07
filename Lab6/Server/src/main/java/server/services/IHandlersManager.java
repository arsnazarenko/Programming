package server.services;


import library.clientCommands.Command;

public interface IHandlersManager {
    Object handling(Command command);
}
