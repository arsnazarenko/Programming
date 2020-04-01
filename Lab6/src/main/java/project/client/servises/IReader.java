package project.client.servises;

import project.client.commands.Command;

import java.util.Queue;
import java.util.Scanner;

public interface IReader {
    Command read(Scanner scanner);
    Queue<Command> scriptRead(Scanner scanner);

}
