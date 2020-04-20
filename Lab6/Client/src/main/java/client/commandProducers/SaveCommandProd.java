package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.commandType.SaveCommand;

public class SaveCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand() {
        return new SaveCommand();
    }
}
