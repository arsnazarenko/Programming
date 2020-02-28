package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;
import projectStructure.сommands.Receiver;

import java.util.Scanner;
/**
 * Класс команды clear
 */
public class ClearCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.clear;

    public ClearCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see Receiver#clear()
     */
    public void execute(Scanner reader, String str) {
        receiver.clear();
    }
}
