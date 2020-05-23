package client.servises;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.NameOfCommands;
import library.clientCommands.UserData;
import library.clientCommands.commandType.ExitCommand;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Класс для чтения команд из консли/файла и создания их объектов
 */
public class Reader implements IReader{
    private IValidator validator;

    public Reader(IValidator validator) {
        this.validator = validator;
    }

    /**
     *
     * @param reader - класс сканер для чтения пользовательского ввода
     * @return объект команды
     */
    public Command read(Scanner reader, UserData userData) {
        String str;
        String parameter = "";
        NameOfCommands command = null;
        boolean flag;
        while (true) {
            flag = true;
            System.out.print("Введите команду: ");
            try {
                str = reader.nextLine();
                if (str.contains(" ")) {
                    parameter = str.substring(str.indexOf(" ") + 1);
                    str = str.substring(0, str.indexOf(" "));
                }
                System.out.println(str.toUpperCase() + " " + parameter);
                command = NameOfCommands.valueOf(str);
            } catch (IllegalArgumentException | NullPointerException e) {
                flag = false;
            }
            if (flag == true) {
                break;
            }
            System.out.println("НЕВЕРНАЯ КОМАНДА");
        }
        return validator.buildCommand(new CommandData(command, parameter, userData), reader);
    }

    /**
     *
     * @param reader - класс сканер для чтения пользовательского ввода
     * @return - очередь из команд
     */
    public Queue<Command> scriptRead(Scanner reader, UserData userData) {
        Queue<Command> commandQueue = new LinkedList<>();
        Command command;
        while (true) {
            command = read(reader, userData);
            commandQueue.offer(command);
            System.out.println();
            if (command != null) {
                if (command.getClass() == ExitCommand.class) {
                    break;
                }
            }
        }

        return commandQueue;
    }

    public IValidator getValidator() {
        return validator;
    }

    public void setValidator(IValidator validator) {
        this.validator = validator;
    }

}
