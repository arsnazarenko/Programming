package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;

import java.util.Scanner;
/**
 * Класс команды add
 */
public class LoadCommand implements ICommand {
    private IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.load;

    public LoadCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see projectStructure.сommands.Receiver#load(String)
     * @param str
     */
    public void execute(Scanner reader, String str) {
        receiver.load(str);
    }
}
