package client.commandProducers;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.commandType.ExecuteScriptCommand;

public class ExecuteScriptCommandProd implements StandardCommandProducer, ArgumentProperties{
    private String script = null;
    //тут проверка тсроки не нужна, проверяеся при запуске
    @Override
    public Command createCommand() {
        return new ExecuteScriptCommand(script);
    }

    @Override
    public void setArgument(String parameter) {
        this.script = parameter;
    }
}
