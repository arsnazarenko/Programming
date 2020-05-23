package client.servises;

import library.clientCommands.Command;
import library.clientCommands.UserData;


import java.util.Queue;
import java.util.Scanner;

public interface IReader {
    Command read(Scanner scanner, UserData userData);
    Queue<Command> scriptRead(Scanner scanner, UserData userData);

}
