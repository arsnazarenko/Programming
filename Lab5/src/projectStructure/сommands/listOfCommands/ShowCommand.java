package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;
import projectStructure.сommands.Receiver;

import java.util.Scanner;
/**
 * Класс команды show
 */
public class ShowCommand implements ICommand{
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.show;

    public ShowCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see Receiver#show()
     */
    public void execute(Scanner reader, String str) {
        receiver.show();
    }
}
