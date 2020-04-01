package project.client.servises;

import project.client.commands.Command;
import java.io.*;
import java.util.Queue;
import java.util.Scanner;

public class CommandCreator {
    private Reader reader;


    public CommandCreator(Reader reader, Validator validator) {
        this.reader = reader;
    }

    public Command createCommand(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Command command = reader.read(scanner);
        return command;


    }

    public Queue<Command> createCommandQueue(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Queue<Command> commandQueue = reader.scriptRead(scanner);
        return commandQueue;

    }
}
