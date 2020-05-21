package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.commandType.HeadCommand;

public class HeadCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand(String login, String password) {
        return new HeadCommand(login, password);
    }
}
