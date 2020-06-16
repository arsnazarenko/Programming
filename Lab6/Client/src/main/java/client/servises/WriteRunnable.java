package client.servises;

import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.clientCommands.commandType.ExecuteScriptCommand;
import library.clientCommands.commandType.ExitCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Selector;
import java.util.*;
import java.util.stream.Collectors;

public class WriteRunnable implements Runnable {
    private volatile UserData sessionUser;
    private final ICommandCreator commandCreator;
    private final Queue<List<Command>> changeRequest;
    private final Selector selector;


    public WriteRunnable(ICommandCreator commandCreator, Queue<List<Command>> changeRequest, Selector selector) {
        this.commandCreator = commandCreator;
        this.changeRequest = changeRequest;
        this.selector = selector;
    }


    @Override
    public void run() {
        process();

    }

    public void process() {
        while (true) {
            Command command;
            List<Command> newCommands = null;
            if (sessionUser == null) {
                command = commandCreator.authorization(System.in);
                newCommands = new ArrayList<>(1);
                newCommands.add(command);
            } else {
                command = commandCreator.createCommand(System.in, sessionUser);
                if (command instanceof ExecuteScriptCommand) {
                    newCommands = recursiveCreatingScript(scriptRun(command));
//                } else if (command instanceof ExitCommand) {
//                    System.exit(0);
                } else {
                    newCommands = new ArrayList<>(1);
                    newCommands.add(command);
                }
            }
            synchronized (changeRequest) {
                if (newCommands != null)
                    changeRequest.offer(newCommands);
            }
            selector.wakeup();
        }
    }

    private Queue<Command> scriptRun(Command command) {
        Queue<Command> commandQueue = new LinkedList<>();
        ExecuteScriptCommand script = (ExecuteScriptCommand) command;
        try (InputStream scriptStream = new FileInputStream(new File(script.getScript()))) {
            //генерим список команд с логином и паролем, которые указаны при отправке команды execute_script
            commandQueue = commandCreator.createCommandQueue(scriptStream, script.getUserData());
            return commandQueue;
        } catch (NoSuchElementException e) {
            System.out.println("ОШИБКА СКРИПТА");
        } catch (IOException e) {
            System.out.println("ОШИБКА ФАЙЛА");
        }
        return commandQueue; // при возникшей ошибке возвращаем пустую очередь
    }

    public List<Command> recursiveCreatingScript(Queue<Command> commands) {
        List<Command> newCommands = new ArrayList<>();
        Queue<Command> commandQueue = commands.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayDeque::new));
        while (true) {
            Command command = commandQueue.poll();
            if (command == null) {
                break;
            } else {
                newCommands.add(command);
                if (command.getClass() == ExecuteScriptCommand.class) {
                    Queue<Command> newCommandQueue = scriptRun(command);
                    newCommandQueue.addAll(commandQueue);
                }
            }
        }
        return newCommands;
    }

}
