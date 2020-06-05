package client.servises;



import library.clientCommands.Command;
import library.clientCommands.UserData;

import java.io.InputStream;
import java.util.Queue;
import java.util.Scanner;

public interface ICommandCreator {
    Command createCommand(InputStream inputStream, UserData userData);
    Command authorization(InputStream inputStream);
    Queue<Command> createCommandQueue(InputStream inputStream, UserData userData);

}
