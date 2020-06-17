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
    private final Queue<Command> changeRequest;
    private final Selector selector;
    private final Queue<Command> queue = new LinkedList<>();


    public WriteRunnable(ICommandCreator commandCreator, Queue<Command> changeRequest, Selector selector) {
        this.commandCreator = commandCreator;
        this.changeRequest = changeRequest;
        this.selector = selector;
    }


    @Override
    public void run() {
        process();

    }

    public void process() {
        while (!Thread.currentThread().isInterrupted()) {
            Command command = null;
            if (sessionUser == null) {
                command = commandCreator.authorization(System.in);
            } else {
                if (!queue.isEmpty()) {
                    System.out.println(queue);
                    command = queue.poll();
                } else {
                    command = commandCreator.createCommand(System.in, sessionUser);
                    if (command.getClass() == ExecuteScriptCommand.class) {
                        queue.addAll(recursiveCreatingScript(scriptRun(command)));
                    } else if (command.getClass() == ExitCommand.class) {
                        System.exit(0);
                    }
                }

            }
            if (command != null) {
                synchronized (changeRequest) {
                    changeRequest.offer(command); // request to server
                    selector.wakeup();  // selector wakeUp for writing
                    try {
                        changeRequest.wait(); //sleep until getting response
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
    }


    public void stop() {
        Thread.currentThread().interrupt();
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
                    commandQueue = newCommandQueue;
                }
            }
        }

        return newCommands;
    }

    public void setSessionUser(UserData sessionUser) {
        this.sessionUser = sessionUser;
    }
}
