package projectStructure.сommands.listOfCommands;


import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;
import projectStructure.сommands.Receiver;

import java.util.Scanner;
/**
 * Класс команды help
 */
public class HelpCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.help;


    public HelpCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see Receiver#help()
     */
    public void execute(Scanner reader, String str) {
        receiver.help();
    }
}
