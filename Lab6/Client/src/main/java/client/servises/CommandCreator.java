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
     * @return объект команды для работы с колллекцией
     */

    public Command authorization(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        return reader.readAuthorizationCommand(scanner);
    }

    public Command createCommand(InputStream inputStream, UserData userData) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        //получаем данные для авторизации или регистрации
        //получии команду
        return reader.readWorkCommand(scanner, userData);
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

}
