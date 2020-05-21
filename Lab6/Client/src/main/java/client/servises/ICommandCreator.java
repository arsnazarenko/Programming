package client.servises;



import library.clientCommands.Command;

import java.io.InputStream;
import java.util.Queue;

public interface ICommandCreator {
    Command createCommand(InputStream inputStream);
    Queue<Command> createCommandQueue(InputStream inputStream, String login, String password);
}
