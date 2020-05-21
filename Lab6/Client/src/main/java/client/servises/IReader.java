package client.servises;

import library.clientCommands.Command;


import java.util.Queue;
import java.util.Scanner;

public interface IReader {
    Command read(Scanner scanner, String login, String password);
    Queue<Command> scriptRead(Scanner scanner, String login, String password);

}
