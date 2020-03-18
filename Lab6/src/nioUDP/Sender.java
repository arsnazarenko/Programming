package nioUDP;

import project.client.commands.Command;
import project.client.commands.uiCommands.ExecuteScriptCommand;
import project.client.commands.uiCommands.ExitCommand;
import project.client.commands.uiCommands.LoadCommand;
import project.client.commands.uiCommands.SaveCommand;
import project.client.serialization.SerializationManager;
import project.client.ui.*;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.NoSuchElementException;

public class Sender {
    private ByteBuffer forAnswer = ByteBuffer.allocate(8*1024);
    private UI ui;
    private SerializationManager serializationManager = new SerializationManager();
    private SocketAddress serverAddress;
    private FileLoader fileLoader = new FileLoader();

    public Sender(UI ui, SocketAddress serverAddress) {
        this.ui = ui;
        this.serverAddress = serverAddress;
    }

    public void sendAndReceive(Command command, DatagramChannel client) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(serializationManager.objectSerial(command));
        client.send(buffer, serverAddress);
        buffer.clear();
        client.receive(forAnswer);
        forAnswer.flip();
        int limits = forAnswer.limit();
        byte bytes[] = new byte[limits];
        forAnswer.get(bytes, 0, limits);
        String answer = new String(bytes);
        System.out.println("ServerAnswer: " + answer);
        forAnswer.clear();
    }

    public void execute(InputStream inputStream, DatagramChannel client) throws IOException {
        while (true) {
            Command command = ui.createCommand(inputStream);
            if (command != null) {
                if (command.getClass() == ExecuteScriptCommand.class) {
                    scriptRun(command, client);
                } else if (command.getClass() == ExitCommand.class) {
                    System.out.println("ЗАВЕРШЕНИЕ....");
                    break;
                } else {
                    sendAndReceive(command, client);
                    //отправка команды серверу
                }
            }
        }
    }



    private void scriptRun(Command command, DatagramChannel client) {
        ExecuteScriptCommand script = (ExecuteScriptCommand) command;
        try (InputStream scriptStream = new FileInputStream(script.getScript())) {
            System.out.println("С К Р И П Т");
            execute(scriptStream, client);
            System.out.println("С К Р И П Т");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            System.out.println("ОШИБКА СКРИПТА");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ОШИБКА ФАЙЛА");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ОШИБКА ФАЙЛА");
        }
    }

}
