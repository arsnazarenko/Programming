package project.client.servises;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import project.client.commands.Command;
import project.client.commands.commandType.ExecuteScriptCommand;
import project.client.commands.commandType.ExitCommand;
import java.io.*;
import java.net.SocketAddress;
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

    public void exchange(Command command, SocketAddress address){
        sender.send(command, address);
        System.out.println(receiver.receive()); //временно
    }

    public void exchangeWithServer(Command[] commands, SocketAddress address) {
        for(Command command : commands) {
            if (command != null) {
                if (command.getClass() == ExecuteScriptCommand.class) {
                    exchange(command, address);
                    scriptRun(command, address);
                } else {
                    exchange(command, address);
                    if (command.getClass() == ExitCommand.class) {
                        System.out.println("ЗАВЕРШЕНИЕ....");
                    }
                }
            }
        }
    }




    private void scriptRun(Command command, SocketAddress address) {
        ExecuteScriptCommand script = (ExecuteScriptCommand) command;
        try (InputStream scriptStream = new FileInputStream(new File(script.getScript()))) {
            Queue<Command> commandQueue = commandCreator.createCommandQueue(scriptStream);
            Command[] commands = commandQueue.toArray(new Command[commandQueue.size()]);
            System.out.println("КОНЕЦ СКРИПТА");
            exchangeWithServer(commands, address);
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