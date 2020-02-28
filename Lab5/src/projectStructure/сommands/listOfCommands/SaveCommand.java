package projectStructure.сommands.listOfCommands;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.IReceiver;
import projectStructure.сommands.NameOfCommands;

import java.util.Scanner;
/**
 * Класс команды save
 */
public class SaveCommand implements ICommand {
    private IReceiver receiver;
    private final NameOfCommands name = NameOfCommands.save;


    public SaveCommand(IReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public NameOfCommands getName() {
        return name;
    }

    /**
     * @see projectStructure.сommands.Receiver#save(String)
     * @param str
     */
    public void execute(Scanner reader, String str) {
        receiver.save(str);
    }


}
