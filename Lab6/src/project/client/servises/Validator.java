package project.client.servises;

import project.client.commands.Command;
import project.client.commands.CommandData;
import project.client.commands.NameOfCommands;
import project.client.commands.commandType.*;

import java.util.*;

public class Validator {
    private IObjectCreator objectCreator;
    private ValidateManager validateManager;

    public Validator(ObjectCreator objectCreator, ValidateManager validateManager) {
        this.objectCreator = objectCreator;
        this.validateManager = validateManager;
    }

    public Command buildCommand(CommandData commandData, Scanner scanner) {

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
                    /*
                    команды save принимает только название команды, т к серверу лишь только нужно отправить на данный запрос
                    строку , содержащую текст xml файла
                    */
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
