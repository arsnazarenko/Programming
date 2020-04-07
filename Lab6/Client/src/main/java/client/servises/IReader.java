package client.servises;

import library.clientCommands.Command;


import java.util.Queue;
import java.util.Scanner;

public interface IReader {
    Command read(Scanner scanner);
    Queue<Command> scriptRead(Scanner scanner);

}
