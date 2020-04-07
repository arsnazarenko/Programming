package client.servises;



import client.commandData.CommandData;
import library.clientCommands.Command;

import java.util.Scanner;

public interface IValidator {
    Command buildCommand(CommandData commandData, Scanner scanner);
}
