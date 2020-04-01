package project.server.services;

import project.client.commands.Command;

public interface IHandlersManager {
    String handling(Command command);
}
