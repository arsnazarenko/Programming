package client.commandData;


import library.clientCommands.NameOfCommands;
import library.clientCommands.UserData;

/**
 * Класс для промедуточного хранения данных команды для ее создания
*/
public class CommandData {

    private NameOfCommands command;
    private String param;
    private UserData userData;

    public CommandData(NameOfCommands command, String param, UserData userData) {
        this.command = command;
        this.param = param;
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
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
