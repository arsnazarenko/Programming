package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.ExecuteScriptCommand;

public class ExecuteScriptCommandProd implements StandardCommandProducer, ArgumentProperties{
    private String script = null;
    //тут проверка тсроки не нужна, проверяеся при запуске
    @Override
    public Command createCommand(UserData userData) {
        return new ExecuteScriptCommand(script, userData);
    }

    @Override
    public void setArgument(String parameter) {
        this.script = parameter;
    }
}
