package client.commandProducers;

import client.servises.ValidateManager;
import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.RemoveIdCommand;

public class RemoveByIdCommandProd implements StandardCommandProducer, ArgumentProperties {
    private ValidateManager validateManager;
    private Long id = null;

    public RemoveByIdCommandProd(ValidateManager validateManager) {
        this.validateManager = validateManager;
    }

    @Override
    public void setArgument(String parameter) {
        this.id = validateManager.idValid(parameter);
    }

    @Override
    public Command createCommand(UserData userData) {
        return (id != null)?new RemoveIdCommand(id, userData):null;
    }
}
