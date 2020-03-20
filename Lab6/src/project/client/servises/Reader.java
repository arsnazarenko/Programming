package project.client.servises;

import project.client.commands.Command;
import project.client.commands.CommandData;
import project.client.commands.NameOfCommands;
import project.client.commands.commandType.ExitCommand;

import java.util.*;

public class Reader {
    private Validator validator;

    public Reader(Validator validator) {
        this.validator = validator;
    }


    public Command read(Scanner reader) {
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
        return validator.buildCommand(new CommandData(command, parameter), reader);
    }


    public Queue<Command> scriptRead(Scanner reader) {
        Queue<Command> commandQueue = new LinkedList<>();
        Command command;
        while (true) {
            command = read(reader);
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

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }


}
