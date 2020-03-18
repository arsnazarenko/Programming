package project.client.ui;

import project.client.commands.Command;
import project.client.commands.NameOfCommands;
import project.client.commands.uiCommands.ExecuteScriptCommand;
import project.client.commands.uiCommands.ExitCommand;
import project.client.commands.uiCommands.LoadCommand;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UI {
    private Reader reader;
    private Validator validator;


    public UI(Reader reader, Validator validator) {
        this.reader = reader;
        this.validator = validator;
    }

    public Command createCommand(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        CommandData commandData = reader.read(scanner);
        Command command = validator.buildCommand(commandData, scanner);
        return command;

    }
}
