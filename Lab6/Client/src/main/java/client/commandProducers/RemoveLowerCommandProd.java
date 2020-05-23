package client.commandProducers;

import client.servises.IObjectCreator;
import client.servises.ObjectCreator;
import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.RemoveLowerCommand;

import java.util.Scanner;

public class RemoveLowerCommandProd implements StandardCommandProducer, ScanProperties {
    private IObjectCreator objectCreator;
    private Scanner scanner = null;

    public RemoveLowerCommandProd(IObjectCreator objectCreator) {
        this.objectCreator = objectCreator;
    }

    @Override
    public void setReaderForCreate(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Command createCommand(UserData userData) {
        if (scanner != null) {
            return new RemoveLowerCommand(objectCreator.create(scanner), userData);
        } else {
            return null;
        }
    }
}
