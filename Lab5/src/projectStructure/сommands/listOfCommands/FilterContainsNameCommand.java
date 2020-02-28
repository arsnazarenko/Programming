package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;

import java.util.Scanner;
/**
 * Класс команды filter_contains_name
 */
public class FilterContainsNameCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.filter_contains_name;

    public FilterContainsNameCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see projectStructure.сommands.Receiver#filterContainsName(String)
     * @param str
     */
    public void execute(Scanner reader, String str) {
        receiver.filterContainsName(str);
    }
}
