package projectStructure.сommands.listOfCommands;


import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;
import projectStructure.сommands.Receiver;

import java.util.Scanner;
/**
 * Класс команды add_if_min{element}
 */
public class AddMinCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.add_if_min;

    public AddMinCommand(IReceiver receiver) {
        this.receiver = receiver;
    }


    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see Receiver#addIfMin(Scanner)
     * @param reader
     */
    public void execute(Scanner reader, String str) {
        receiver.addIfMin(reader);
    }
}

