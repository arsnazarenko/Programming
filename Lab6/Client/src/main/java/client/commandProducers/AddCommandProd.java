package client.commandProducers;

import client.servises.IObjectCreator;
import client.servises.ObjectCreator;
import library.clientCommands.Command;
import library.clientCommands.commandType.AddCommand;
import library.clientCommands.commandType.RemoveLowerCommand;

import java.util.Scanner;

public class AddCommandProd implements StandardCommandProducer, ScanProperties{
    private IObjectCreator objectCreator;
    private Scanner scanner = null;

    public AddCommandProd(IObjectCreator objectCreator) {
        this.objectCreator = objectCreator;
    }

    @Override
    public void setReaderForCreate(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Command createCommand(String login, String password) {
        if (scanner != null) {
            return new AddCommand(objectCreator.create(scanner), login, password);
        } else {
            return null;
        }
    }
}
