package client.commandProducers;

import client.servises.IObjectCreator;
import client.servises.ObjectCreator;
import client.servises.ValidateManager;
import library.clientCommands.Command;
import library.clientCommands.commandType.UpdateIdCommand;

import java.util.Scanner;

public class UpdateIdCommandProd implements ScanProperties, ArgumentProperties {
    private ValidateManager validateManager;
    private IObjectCreator objectCreator;
    private Long id = null;
    private Scanner scanner;

    public UpdateIdCommandProd(ValidateManager validateManager, IObjectCreator objectCreator) {
        this.validateManager = validateManager;
        this.objectCreator = objectCreator;
    }

    @Override
    public void setArgument(String parameter) {
        this.id = validateManager.idValid(parameter);
    }

    @Override
    public void setReaderForCreate(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Command createCommand(String login, String password) {
        if(!(scanner == null) && !(id == null)) {
            return new UpdateIdCommand(objectCreator.create(scanner), id, login, password);
        } else {
            return null;
        }
    }
}
