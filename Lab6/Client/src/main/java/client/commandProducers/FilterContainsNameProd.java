package client.commandProducers;

import client.servises.ValidateManager;
import library.clientCommands.Command;
import library.clientCommands.commandType.FilterContainsNameCommand;

public class FilterContainsNameProd implements StandardCommandProducer, ArgumentProperties{
    private String name = null;
    private ValidateManager validateManager;

    public FilterContainsNameProd(ValidateManager validateManager) {
        this.validateManager = validateManager;
    }

    @Override
    public void setArgument(String parameter) {
        this.name = validateManager.nameValid(parameter);
    }

    @Override
    public Command createCommand() {
        return name!= null?new FilterContainsNameCommand(name):null;
    }
}
