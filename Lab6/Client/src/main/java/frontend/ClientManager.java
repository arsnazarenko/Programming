package frontend;
import client.servises.ArgumentValidateManager;
import client.servises.MessageService;
import library.clientCommands.Command;
import library.clientCommands.SpecialSignals;
import library.clientCommands.UserData;


public class ClientManager {
    private MessageService messageService;
    private ArgumentValidateManager argumentValidator;
    private UserData userData;


    public ClientManager(MessageService messageService, ArgumentValidateManager argumentValidator) {
        this.messageService = messageService;
        this.argumentValidator = argumentValidator;
    }

    public void executeCommand(Command command) {
        messageService.putInRequestQueue(command);
    }

    public boolean handlerAuth(SpecialSignals logResponse) {
        if (logResponse != null) {
            if (logResponse == SpecialSignals.AUTHORIZATION_TRUE || logResponse == SpecialSignals.REG_TRUE) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public ArgumentValidateManager getArgumentValidator() {
        return argumentValidator;
    }
}