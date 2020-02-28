package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;
import projectStructure.сommands.Receiver;

import java.util.Scanner;
/**
 * Класс команды print_ascending
 */
public class PrintAscendingCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.exit;

    public PrintAscendingCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see Receiver#printAscending()
     */
    public void execute(Scanner reader, String str) {
        receiver.printAscending();
    }
}
