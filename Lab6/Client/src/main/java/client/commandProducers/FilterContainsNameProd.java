package client.commandProducers;

import client.servises.ArgumentValidateManager;
import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.FilterContainsNameCommand;

public class FilterContainsNameProd implements StandardCommandProducer, ArgumentProperties{
    private String name = null;
    private ArgumentValidateManager argumentValidateManager;

    public FilterContainsNameProd(ArgumentValidateManager argumentValidateManager) {
        this.argumentValidateManager = argumentValidateManager;
    }

    @Override
    public void setArgument(String parameter) {
        this.name = argumentValidateManager.subStringIsValid(parameter);
    }

    @Override
    public Command createCommand(UserData userData) {
        return name!= null?new FilterContainsNameCommand(name, userData):null;
    }
}
