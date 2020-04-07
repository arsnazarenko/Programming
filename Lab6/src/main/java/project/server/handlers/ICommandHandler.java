package project.server.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.client.commands.Command;

import java.io.Serializable;

public interface ICommandHandler {
    Object processCommand(Command command);
}
