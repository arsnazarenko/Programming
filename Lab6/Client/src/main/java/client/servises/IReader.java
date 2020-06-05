package client.servises;

import library.clientCommands.Command;
import library.clientCommands.UserData;


import java.util.Queue;
import java.util.Scanner;

public interface IReader {
    Command readWorkCommand(Scanner scanner, UserData userData);
    Command readAuthorizationCommand(Scanner scanner);
    Queue<Command> scriptRead(Scanner scanner, UserData userData);

}
