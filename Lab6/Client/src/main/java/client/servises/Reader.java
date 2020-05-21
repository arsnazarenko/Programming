package client.servises;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.NameOfCommands;
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
    public Command read(Scanner reader, String login, String password) {
        String str;
        String parameter = "";
        NameOfCommands command = null;
        String thisRequestLogin = "";
        String thisRequestPassword = "";
        boolean flag;
        while (true) {
            flag = true;
            if (login == null || password == null) {
                System.out.print("Введите логин: ");
                thisRequestLogin = reader.nextLine();
                System.out.print("Введите пароль: ");
                thisRequestPassword = reader.nextLine();
                /*
                здесь мы только проверяем на пустую строку или строку только из проелов, но если введен хотя бы один символ с пробелом,
                пароль является допустимым и отправляется серверу
                 */
                if(thisRequestLogin.trim().equals("") || thisRequestPassword.trim().equals("")) {
                    System.out.println("ПАРОЛЬ И ЛОГИН НЕ МОГУТ БЫТЬ ПУСТОЙ СТРОКОЙ");
                    continue;
                }
            } else {
                thisRequestLogin = login;
                thisRequestPassword = password;
            }
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
        System.out.println("логин: " + thisRequestLogin + ", пароль: " + thisRequestPassword + ", команда: " + command.name());
        return validator.buildCommand(new CommandData(command, parameter, thisRequestLogin, thisRequestPassword), reader);
    }

    /**
     *
     * @param reader - класс сканер для чтения пользовательского ввода
     * @return - очередь из команд
     */
    public Queue<Command> scriptRead(Scanner reader, String login, String password) {
        Queue<Command> commandQueue = new LinkedList<>();
        Command command;
        while (true) {
            command = read(reader, login, password);
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
