package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;

import java.util.Scanner;
/**
 * Класс команды remove_by_id{element}
 */
public class RemoveIdCommand implements ICommand {
    private final IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.exit;


    public RemoveIdCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see projectStructure.сommands.Receiver#removeById(String)
     * @param str
     */
    public void execute(Scanner reader, String str) {
        receiver.removeById(str);
    }


}
