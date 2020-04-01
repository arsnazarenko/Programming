package project.client.servises;

import project.client.commands.Command;
import project.client.commands.commandType.ExecuteScriptCommand;
import project.client.serialization.SerializationManager;

import java.io.*;
import java.net.SocketAddress;
import java.util.NoSuchElementException;
import java.util.Queue;

public class PostManager {
    private ICommandCreator commandCreator;
    private IClientSender clientSender;
    private IClientReceiver clientReceiver;

    public PostManager(ICommandCreator commandCreator, IClientSender clientSender, IClientReceiver clientReceiver) {
        this.commandCreator = commandCreator;
        this.clientSender = clientSender;
        this.clientReceiver = clientReceiver;
    }

    public void exchange(Command command, SocketAddress address) {
        //отправка сообщения серверу
        clientSender.send(command, address);
        //получение ответа от сервера, его десериализация и вывод в консоль
        String serverAnswer = clientReceiver.receive();
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

    public ICommandCreator getCommandCreator() {
        return commandCreator;
    }

    public void setCommandCreator(ICommandCreator commandCreator) {
        this.commandCreator = commandCreator;
    }

    public IClientSender getClientSender() {
        return clientSender;
    }

    public void setClientSender(IClientSender clientSender) {
        this.clientSender = clientSender;
    }

    public IClientReceiver getClientReceiver() {
        return clientReceiver;
    }

    public void setClientReceiver(IClientReceiver clientReceiver) {
        this.clientReceiver = clientReceiver;
    }
}