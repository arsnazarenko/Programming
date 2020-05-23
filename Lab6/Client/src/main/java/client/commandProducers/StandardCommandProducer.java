package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.UserData;

public interface StandardCommandProducer {
    Command createCommand(UserData userData);
}
