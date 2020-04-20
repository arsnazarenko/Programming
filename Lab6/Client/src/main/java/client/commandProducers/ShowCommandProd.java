package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.commandType.ShowCommand;

public class ShowCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand() {
        return new ShowCommand();
    }
}
