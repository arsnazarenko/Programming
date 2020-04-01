package project.client.servises;

import project.client.commands.Command;
import project.client.commands.CommandData;

import java.util.Scanner;

public interface IValidator {
    Command buildCommand(CommandData commandData, Scanner scanner);
}
