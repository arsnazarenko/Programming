package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.commandType.InfoCommand;

public class InfoCommandProd implements StandardCommandProducer{

    @Override
    public Command createCommand(String login, String password) {
        return new InfoCommand(login, password);
    }
}
