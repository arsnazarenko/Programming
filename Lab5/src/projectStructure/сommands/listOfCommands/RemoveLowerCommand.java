package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;

import java.util.Scanner;
/**
 * Класс команды remove_lower
 */
public class RemoveLowerCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.remove_lower;

    public RemoveLowerCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see projectStructure.сommands.Receiver#removeLower(Scanner)
     * @param reader
     */
    public void execute(Scanner reader, String str) {
        receiver.removeLower(reader);
    }

}
