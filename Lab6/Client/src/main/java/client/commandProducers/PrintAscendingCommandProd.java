package client.commandProducers;

import library.clientCommands.Command;
import library.clientCommands.commandType.PrintAscendingCommand;

public class PrintAscendingCommandProd implements StandardCommandProducer{

    @Override
    public Command createCommand(String login, String password) {
        return new PrintAscendingCommand(login, password);
    }
}
