package project.client.commands;

import project.client.commands.NameOfCommands;

public class CommandData {
    private NameOfCommands command;
    private String param;

    public CommandData(NameOfCommands command, String param) {
        this.command = command;
        this.param = param;
    }

    public NameOfCommands getCommand() {
        return command;
    }

    public void setCommand(NameOfCommands command) {
        this.command = command;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
