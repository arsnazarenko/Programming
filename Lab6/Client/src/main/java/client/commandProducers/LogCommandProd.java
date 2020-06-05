package client.commandProducers;

import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.LogCommand;

public class LogCommandProd implements StandardCommandProducer{
    @Override
    public Command createCommand(UserData userData) {
        return new LogCommand(userData);
    }
}
