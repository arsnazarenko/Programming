package server.business.handlers;

import library.clientCommands.Command;
import library.clientCommands.NameOfCommands;

import java.util.Arrays;


public class HelpCommandHandler implements ICommandHandler {
    @Override
    public String processCommand(Command command) {
        //команда без констроля авторизации, т к пользователь хотя бы должен знать, как ему зарегестрироваться
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(NameOfCommands.values()).
                map(NameOfCommands::toString).
                forEach(o1 -> stringBuilder.append(o1 + "\n"));
        return stringBuilder.toString();
    }
}
