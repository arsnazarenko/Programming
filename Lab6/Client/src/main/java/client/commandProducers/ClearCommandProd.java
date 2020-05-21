package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.commandType.ClearCommand;

public class ClearCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand(String login, String password) {
        return new ClearCommand(login, password);
    }
}
