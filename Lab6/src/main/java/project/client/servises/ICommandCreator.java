package project.client.servises;

import project.client.commands.Command;

import java.io.InputStream;
import java.util.Queue;

public interface ICommandCreator {
    Command createCommand(InputStream inputStream);
    Queue<Command> createCommandQueue(InputStream inputStream);
}
