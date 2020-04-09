package client.servises;



import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.NameOfCommands;
import library.clientCommands.commandType.*;

import java.util.Scanner;

/**
 * Класс, отвечающий за валидацию данных
 * @see Validator
 */
public class Validator implements IValidator{
    private IObjectCreator objectCreator;
    private ValidateManager validateManager;

    public Validator(IObjectCreator objectCreator, ValidateManager validateManager) {
        this.objectCreator = objectCreator;
        this.validateManager = validateManager;
    }

    /**
     *
     * @param commandData - данные команды(название, аргумент)
     * @param scanner - способ чтения данных
     * @return - класс команды, если данные невалидны - null
     */
    public Command buildCommand(CommandData commandData, Scanner scanner) {
        //в случае невалидных параметров возвращается null вместо объкта Command
        Command command = null;
        NameOfCommands nameOfCommand = commandData.getCommand();
        String parameter = commandData.getParam();
        switch (nameOfCommand) {
            case add:
                command = new AddCommand(objectCreator.create(scanner));
                break;
            case add_if_min:
                command = new AddIfMinCommand(objectCreator.create(scanner));
                break;
            case remove_lower:
                command = new RemoveLowerCommand(objectCreator.create(scanner));
                break;
            case clear:
                command = new ClearCommand();
                break;
            case exit:
                command = new ExitCommand();
                break;
            case head:
                command = new HeadCommand();
                break;
            case help:
                command = new HelpCommand();
                break;
            case info:
                command = new InfoCommand();
                break;
            case max_by_employees_count:
                command = new MaxByEmployeeCommand();
                break;
            case print_ascending:
                command = new PrintAscendingCommand();
                break;
            case show:
                command = new ShowCommand();
                break;
            case execute_script:
                command = new ExecuteScriptCommand(parameter);
                break;
            case filter_contains_name:
                String name;
                //если параметр неверный, то возвращаем null и не создаем объект null
                if ((name = validateManager.nameValid(parameter))!= null) {
                    command = new FilterContainsNameCommand(parameter);
                }
                break;
            case remove_by_id:
                Long id;
                if ((id = validateManager.idValid(parameter)) != null) {
                    command = new RemoveIdCommand(id);
                }
                break;
            case save:
                command = new SaveCommand();
                break;
            case update_id:
                Long updateId;
                if ((updateId = validateManager.idValid(parameter)) != null) {
                    command = new UpdateIdCommand(objectCreator.create(scanner), updateId);
                }
                break;
        }

        return command;
    }


    public IObjectCreator getObjectCreator() {
        return objectCreator;
    }

    public void setObjectCreator(ObjectCreator objectCreator) {
        this.objectCreator = objectCreator;
    }

    public ValidateManager getValidateManager() {
        return validateManager;
    }

    public void setValidateManager(ValidateManager validateManager) {
        this.validateManager = validateManager;
    }

}
