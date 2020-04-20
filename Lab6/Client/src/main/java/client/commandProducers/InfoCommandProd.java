package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.commandType.InfoCommand;

public class InfoCommandProd implements StandardCommandProducer{

    @Override
    public Command createCommand() {
        return new InfoCommand();
    }
}
