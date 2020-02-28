package projectStructure.сommands.listOfCommands;


import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;
import projectStructure.сommands.Receiver;

import java.util.Scanner;
/**
 * Класс команды add
 */
public class InfoCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.info;

    public InfoCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see Receiver#info()
     */
    public void execute(Scanner reader, String str) {
        receiver.info();
    }

}
