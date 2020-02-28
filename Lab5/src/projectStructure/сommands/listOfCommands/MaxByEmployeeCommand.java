package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;
import projectStructure.сommands.Receiver;

import java.util.Scanner;
/**
 * Класс команды max_by_employees_count
 */
public class MaxByEmployeeCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.max_by_employees_count;

    public MaxByEmployeeCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see Receiver#maxByEmployeeCount()
     */
    public void execute(Scanner reader, String str) {
        receiver.maxByEmployeeCount();
    }
}
