package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;
import projectStructure.сommands.Receiver;

import java.util.Scanner;
/**
 * Класс команды head
 */
public class HeadCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.head;

    public HeadCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see Receiver#head()
     */
    public void execute(Scanner reader, String str) {
        receiver.head();
    }
}
