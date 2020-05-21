package client.commandData;


import library.clientCommands.NameOfCommands;
/**
 * Класс для промедуточного хранения данных команды для ее создания
*/
public class CommandData {

    private NameOfCommands command;
    private String param;
    private String login;
    private String password;

    public CommandData(NameOfCommands command, String param, String login, String password) {
        this.command = command;
        this.param = param;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
