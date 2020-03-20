package project.client.servises;

import project.client.commands.Command;
import project.client.commands.commandType.ExecuteScriptCommand;
import project.client.commands.commandType.ExitCommand;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Queue;

public class PostManager {
    private CommandCreator commandCreator;
    private Sender sender;
    private Receiver receiver;


    public PostManager(CommandCreator commandCreator, Sender sender, Receiver receiver) {
        this.commandCreator = commandCreator;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void exchange(Command command){
        sender.send(command);
        System.out.println(receiver.receive()); //временно
    }

    public void exchangeWithServer(Command[] commands) {
        for(Command command : commands) {
            if (command != null) {
                if (command.getClass() == ExecuteScriptCommand.class) {
                    scriptRun(command);
                } else {
                    exchange(command);
                    if (command.getClass() == ExitCommand.class) {
                        System.out.println("ЗАВЕРШЕНИЕ....");
                    }
                }
            }
        }
    }


    private void scriptRun(Command command) {
        ExecuteScriptCommand script = (ExecuteScriptCommand) command;
        try (InputStream scriptStream = new FileInputStream(new File(script.getScript()))) {
            System.out.println("С К Р И П Т");
            Queue<Command> commandQueue = commandCreator.createCommandQueue(scriptStream);
            Command[] commands = commandQueue.toArray(new Command[commandQueue.size()]);
            exchangeWithServer(commands);
            System.out.println("С К Р И П Т");
        } catch (NoSuchElementException e) {
            System.out.println("ОШИБКА СКРИПТА");
        } catch (IOException e) {
            System.out.println("ОШИБКА ФАЙЛА");
        }
    }

    public CommandCreator getCommandCreator() {
        return commandCreator;
    }

    public void setCommandCreator(CommandCreator commandCreator) {
        this.commandCreator = commandCreator;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}