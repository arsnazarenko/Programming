package project.client.servises;

import project.client.commands.Command;
import java.io.*;
import java.util.Queue;
import java.util.Scanner;

public class CommandCreator implements ICommandCreator{
    private IReader reader;


    public CommandCreator(IReader reader) {
        this.reader = reader;
    }

    public Command createCommand(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        //получии команду
        Command command = reader.read(scanner);
        return command;


    }

    public Queue<Command> createCommandQueue(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        //получили очередь из команд для скрипта
        Queue<Command> commandQueue = reader.scriptRead(scanner);
        return commandQueue;

    }
}
