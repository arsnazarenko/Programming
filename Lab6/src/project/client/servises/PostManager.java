package project.client.servises;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import project.client.commands.Command;
import project.client.commands.commandType.ExecuteScriptCommand;
import project.client.commands.commandType.ExitCommand;
import project.client.serialization.SerializationManager;

import java.io.*;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Queue;

public class PostManager {
    private CommandCreator commandCreator;
    private Sender sender;
    private Receiver receiver;
    SerializationManager serializationManager;

    public PostManager(CommandCreator commandCreator, Sender sender, Receiver receiver, SerializationManager serializationManager) {
        this.commandCreator = commandCreator;
        this.sender = sender;
        this.receiver = receiver;
        this.serializationManager = serializationManager;
    }

    public void exchange(Command command, SocketAddress address) {
        sender.send(command, address);
        String serverAnswer = (String) serializationManager.objectDeserial(receiver.receive().getBytes());
        System.out.println(serverAnswer);//временно
    }

    public void exchangeWithServer(Command[] commands, SocketAddress address) {
        for(Command command : commands) {
            if (command != null) {
                if (command.getClass() == ExecuteScriptCommand.class) {
                    exchange(command, address);
                    scriptRun(command, address);
                } else {
                    exchange(command, address);


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