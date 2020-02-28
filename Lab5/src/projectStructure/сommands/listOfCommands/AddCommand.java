package projectStructure.сommands.listOfCommands;
import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;


import java.util.Scanner;

/**
 * Класс команды add{element}
 */
public class AddCommand implements ICommand {
    private IReceiver receiver;
    private NameOfCommands name = NameOfCommands.add;


    public AddCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see projectStructure.сommands.Receiver#add(Scanner)
     * @param reader
     */
    public void execute(Scanner reader, String str) {
        receiver.add(reader);
    }
}
