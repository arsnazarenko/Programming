package client.servises;


import client.commandData.CommandData;
import client.commandProducers.*;
import library.clientCommands.Command;
import library.clientCommands.NameOfCommands;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

import static library.clientCommands.NameOfCommands.*;

/**
 * Класс, отвечающий за валидацию данных и создания объекта команды для запроса серверу
 *
 * @see CommandProduceManager
 */
public class CommandProduceManager implements ICommandProducerManager {
    private Map<NameOfCommands, StandardCommandProducer> commandProducers = new EnumMap<>(NameOfCommands.class);

    public CommandProduceManager(IObjectCreator objectCreator, ArgumentValidateManager argumentValidateManager) {
        commandProducers.put(add, new AddCommandProd(objectCreator));
        commandProducers.put(add_if_min, new AddIfMinCommandProd(objectCreator));
        commandProducers.put(clear, new ClearCommandProd());
        commandProducers.put(execute_script, new ExecuteScriptCommandProd());
        commandProducers.put(exit, new ExitCommandProd());
        commandProducers.put(filter_contains_name, new FilterContainsNameProd(argumentValidateManager));
        commandProducers.put(head, new HeadCommandProd());
        commandProducers.put(help, new HelpCommandProd());
        commandProducers.put(info, new InfoCommandProd());
        commandProducers.put(max_by_employees_count, new MaxByEmployeesCommandProd());
        commandProducers.put(print_ascending, new PrintAscendingCommandProd());
        commandProducers.put(remove_by_id, new RemoveByIdCommandProd(argumentValidateManager));
        commandProducers.put(remove_lower, new RemoveLowerCommandProd(objectCreator));
        commandProducers.put(show, new ShowCommandProd());
        commandProducers.put(update_id, new UpdateIdCommandProd(argumentValidateManager, objectCreator));
        commandProducers.put(reg, new RegCommandProd());
        commandProducers.put(log, new LogCommandProd());

    }

    /**
     * @param commandData - данные команды(название, аргумент)
     * @param scanner     - способ чтения данных
     * @return - класс команды, если данные невалидны - null
     */
    public Command buildCommand(CommandData commandData, Scanner scanner) {
        //Находим нужного продюсера команды по названию команды
        StandardCommandProducer commandProducer = commandProducers.get(commandData.getCommand());
        //проверяем, есть ли в команде аргументы
        if (commandProducer instanceof ArgumentProperties) {
            ((ArgumentProperties) commandProducer).setArgument(commandData.getParam());
        }
        //проверяем, работает ли команда с данными, уоторые вводятся пользователями
        if (commandProducer instanceof ScanProperties) {
            ((ScanProperties) commandProducer).setReaderForCreate(scanner);
        }
        //создаем объект команды
        //в случае невалидных параметров возвращается null вместо объкта Command
        return commandProducer.createCommand(commandData.getUserData());
    }


}
