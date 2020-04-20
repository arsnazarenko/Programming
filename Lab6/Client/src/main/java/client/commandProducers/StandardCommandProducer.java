package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;

public interface StandardCommandProducer {
    Command createCommand();
}
