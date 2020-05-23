package client.servises;


import library.clientCommands.Command;
import library.clientCommands.UserData;

import java.io.InputStream;
import java.util.Queue;
import java.util.Scanner;

/**
 * Класс для создания Объекта команды
 * @see CommandCreator
 */
public class CommandCreator implements ICommandCreator{
    private IReader reader;


    public CommandCreator(IReader reader) {
        this.reader = reader;
    }

    /**
     *
     * @param inputStream - stream, с помощью которого создаются команды
     * @return объект команды
     */
    public Command createCommand(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        //получаем данные для авторизации или регистрации
        //получии команду

        Command command = reader.read(scanner, authorizationParameters(scanner));
        return command;


    }

    /**
     * Метод для создания очереди из команд для скрипта
     * @param inputStream - stream, с помощью которого создаются команды
     * @return очередь из команд с логином и паролем, которая были введены при запуске скрипта
     */
    public Queue<Command> createCommandQueue(InputStream inputStream, UserData userData) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        //получили очередь из команд для скрипта
        Queue<Command> commandQueue = reader.scriptRead(scanner, userData);
        return commandQueue;

    }
    private UserData authorizationParameters(Scanner reader) {
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
                System.out.println("ПАРОЛЬ И ЛОГИН НЕ МОГУТ БЫТЬ ПУСТОЙ СТРОКОЙ");
                continue;
            }
            return new UserData(login, password);
        }
    }
}
