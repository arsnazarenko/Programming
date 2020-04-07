package project.server.services;

import project.client.commands.Command;

import java.io.Serializable;

public interface IHandlersManager {
    Object handling(Command command);
}
