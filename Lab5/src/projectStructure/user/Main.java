package projectStructure.user;

import projectStructure.collectionDB.CollectionManager;
import projectStructure.сommands.*;
import projectStructure.сommands.listOfCommands.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author Арсений Назаренко и Попова Полина
 * Главный класс, в котором запускается программа
 */
public class Main {
    public static void main(String[] args) {
        try {
            String pathFile = args[0];
            File fileForSaveAndLoad = new File(pathFile);
            if (!fileForSaveAndLoad.exists()) {
                throw new FileNotFoundException();
            } else if (!fileForSaveAndLoad.canRead()) {
                throw new FileNotFoundException();
            } else if (!fileForSaveAndLoad.canWrite()) {
                throw new FileNotFoundException();
            } else {
                CollectionManager collectionManager = new CollectionManager();
                IObjectCreator objectCreator = new ObjectCreator();
                IFileWorker fileIO = new JaxbWorker();
                IReceiver receiver = new Receiver(collectionManager, objectCreator, fileIO);
                Invoker invoker = new Invoker(new AddCommand(receiver), new InfoCommand(receiver),
                        new ExitCommand(receiver), new HelpCommand(receiver), new ShowCommand(receiver),
                        new UpdateCommand(receiver), new ClearCommand(receiver),
                        new HeadCommand(receiver), new PrintAscendingCommand(receiver),
                        new AddMinCommand(receiver), new RemoveIdCommand(receiver),
                        new RemoveLowerCommand(receiver), new MaxByEmployeeCommand(receiver),
                        new FilterContainsNameCommand(receiver), new SaveCommand(receiver), new LoadCommand(receiver));
                ApplicationInterface myApp = new ApplicationInterface(invoker);
                myApp.startProgram(System.in, pathFile);
            }
        } catch (FileNotFoundException e) {
            System.err.println("ФАЙЛ ДОЛЖЕН СУЩЕСТВОВАТЬ И ИМЕТЬ ПРАВА НА ЧТЕНИЕ И ЗАПИСЬ");

        } catch (IndexOutOfBoundsException e) {
            System.err.println("НУЖНО ВВЕСТИ ПУТЬ К ФАЙЛУ");
        } catch (NoSuchElementException e) {

        }


    }

}




