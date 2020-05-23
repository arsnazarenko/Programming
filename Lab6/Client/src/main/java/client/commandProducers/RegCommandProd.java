package client.commandProducers;

import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.RegCommand;

public class RegCommandProd implements StandardCommandProducer {
    @Override
    public Command createCommand(UserData userData) {
        return new RegCommand(userData);
    }
}
