package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.commandType.ExitCommand;

public class ExitCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand() {
        return new ExitCommand();
    }
}
