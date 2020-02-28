package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;

import java.util.Scanner;
/**
 * Класс команды exit
 */
public class ExitCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.exit;


    public ExitCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see projectStructure.сommands.Receiver#exit(Scanner)
     * @param reader
     */
    public void execute(Scanner reader, String str) {
        receiver.exit(reader);
    }
}
