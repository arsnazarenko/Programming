package project.server.handlers;

import project.client.commands.Command;
import project.client.commands.NameOfCommands;

import java.util.Arrays;

public class HelpCommandHandler implements ICommandHandler {
    @Override
    public String processCommand(Command command) {
        //информация о команде
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(NameOfCommands.values()).
                map(NameOfCommands::toString).
                forEach(o1 -> stringBuilder.append(o1 + "\n"));
        return stringBuilder.toString();
    }
}
