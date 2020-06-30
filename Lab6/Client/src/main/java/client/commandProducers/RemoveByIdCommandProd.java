package client.commandProducers;

import client.servises.ArgumentValidateManager;
import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.RemoveIdCommand;

public class RemoveByIdCommandProd implements StandardCommandProducer, ArgumentProperties {
    private ArgumentValidateManager argumentValidateManager;
    private Long id = null;

    public RemoveByIdCommandProd(ArgumentValidateManager argumentValidateManager) {
        this.argumentValidateManager = argumentValidateManager;
    }

    @Override
    public void setArgument(String parameter) {
        this.id = argumentValidateManager.idValid(parameter);
    }

    @Override
    public Command createCommand(UserData userData) {
        return (id != null)?new RemoveIdCommand(id, userData):null;
    }
}
