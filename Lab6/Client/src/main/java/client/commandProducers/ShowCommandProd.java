package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.commandType.ShowCommand;

public class ShowCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand(String login, String password) {
        return new ShowCommand(login, password);
    }
}
