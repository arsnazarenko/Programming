package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;

import java.util.Scanner;

/**
 * Класс команды update_id
 */
public class UpdateCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.update_id;

    public UpdateCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see projectStructure.сommands.Receiver#updateId(Scanner, String)
     * @param reader
     * @param str
     */
    public void execute(Scanner reader, String str) {
        receiver.updateId(reader, str);
    }
}
