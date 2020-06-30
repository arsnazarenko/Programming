package client.servises;

import client.commandData.CommandData;
import library.clientCommands.Command;
import library.clientCommands.NameOfCommands;
import library.clientCommands.UserData;
import library.clientCommands.commandType.ExitCommand;
import java.io.Console;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для чтения команд из консли/файла и создания их объектов
 */
public class Reader implements IReader {
    private ICommandProducerManager validator;
    private final Console console = System.console();
    private final Map<String, NameOfCommands> workCommandsMap = Arrays.stream(NameOfCommands.values()).filter(o -> o != NameOfCommands.log || o != NameOfCommands.reg).collect(Collectors.toMap(NameOfCommands::name, o -> o));
    private final Map<String, NameOfCommands> authorizationCommandMap;

    public Reader(ICommandProducerManager validator) {
        this.validator = validator;

    }

    {
        authorizationCommandMap = new HashMap<>();
        authorizationCommandMap.put(NameOfCommands.log.name(), NameOfCommands.log);
        authorizationCommandMap.put(NameOfCommands.reg.name(), NameOfCommands.reg);
        authorizationCommandMap.put(NameOfCommands.help.name(), NameOfCommands.help);
    }

    /**
     * Метод для создания команд, для работы с коллекцией
     *
     * @param reader   - объект для чтения
     * @param userData - данные пользователя
     * @return - объект команды
     */
    public Command readWorkCommand(Scanner reader, UserData userData) {
        String str;
        String parameter = "";
        NameOfCommands command = null;
        while (true) {
            //System.out.print("Введите команду: ");
            str = reader.nextLine();
            if (str.contains(" ")) {
                parameter = str.substring(str.indexOf(" ") + 1);
                str = str.substring(0, str.indexOf(" "));
            }
            System.out.println(str.toUpperCase() + " " + parameter);
            command = workCommandsMap.get(str);
            if (command != null) {
                break;
            }
            System.out.println("НЕВЕРНАЯ КОМАНДА");
        }
        return validator.buildCommand(new CommandData(command, parameter, userData), reader);
    }

    /**
     * Метод для создания команд, для авторизации пользователя
     *
     * @param reader
     * @return
     */
    public Command readAuthorizationCommand(Scanner reader) {
        String str;
        UserData userData = null;
        NameOfCommands command = null;
        while (true) {
            System.out.print("Введите команду для авторизации\nКоманда: " );
            str = reader.nextLine().trim();
            command = authorizationCommandMap.get(str);
            if (command != null) {
                break;
            }
        }
        if (console != null) {
            userData = authorizationWithConsole(reader);
        } else {
            userData = authorization(reader);
        }
        return validator.buildCommand(new CommandData(command, "", userData), reader);
    }

    /**
     * @param reader - класс сканер для чтения пользовательского ввода
     * @return - очередь из команд
     */
    public Queue<Command> scriptRead(Scanner reader, UserData userData) {
        Queue<Command> commandQueue = new LinkedList<>();
        Command command;
        while (true) {
            command = readWorkCommand(reader, userData);
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

    private UserData authorizationWithConsole(Scanner reader) {
        String login;
        String password;
        while (true) {
            System.out.print("Введите логин: ");
            login = console.readLine();
            System.out.print("Введите пароль: ");
            char[] passwordChars = console.readPassword();
            password = new String(passwordChars);
            /*
            здесь мы только проверяем на пустую строку или строку только из проелов, но если введен хотя бы один символ с пробелом,
            пароль является допустимым и отправляется серверу
            */
            if(login.trim().equals("") || password.trim().equals("")) {
                System.out.println("Пароль и логин не могут быть пустой строкой");
                continue;
            }
            return new UserData(login, password);
        }
    }


    private UserData authorization(Scanner reader) {
        String login;
        String password;
        while (true) {
            System.out.print("Введите логин: ");
            login = reader.nextLine();
            System.out.print("Введите пароль: ");
            password = reader.nextLine();
            /*
            здесь мы только проверяем на пустую строку или строку только из проелов, но если введен хотя бы один символ с пробелом,
            пароль является допустимым и отправляется серверу
            */
            if(login.trim().equals("") || password.trim().equals("")) {
                System.out.println("Пароль и логин не могут быть пустой строкой");
                continue;
            }
            return new UserData(login, password);
        }
    }

    public ICommandProducerManager getValidator() {
        return validator;
    }

    public void setValidator(ICommandProducerManager validator) {
        this.validator = validator;
    }

}
