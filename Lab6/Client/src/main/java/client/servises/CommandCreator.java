package client.servises;


import library.clientCommands.Command;

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
        //получии команду
        Command command = reader.read(scanner);
        return command;


    }

    /**
     * Метод для создания очереди из команд для скрипта
     * @param inputStream - stream, с помощью которого создаются команды
     * @return очередб из команд
     */
    public Queue<Command> createCommandQueue(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        //получили очередь из команд для скрипта
        Queue<Command> commandQueue = reader.scriptRead(scanner);
        return commandQueue;

    }
}
