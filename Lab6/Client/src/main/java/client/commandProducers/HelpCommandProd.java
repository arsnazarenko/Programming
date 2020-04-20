package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.commandType.HelpCommand;

public class HelpCommandProd  implements StandardCommandProducer{
    @Override
    public Command createCommand() {
        return new HelpCommand();
    }
}