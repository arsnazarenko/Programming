package project.client.ui;

import project.client.commands.NameOfCommands;

import java.util.Scanner;

public class Reader {
    public CommandData read(Scanner reader) {
        String str;
        String parameter = "";
        NameOfCommands command = null;
        boolean flag;
        while (true) {
            flag = true;
            System.out.print("Введите команду: ");
            try {
                str = reader.nextLine();
                if (str.contains(" ")) {
                    parameter = str.substring(str.indexOf(" ") + 1);
                    str = str.substring(0, str.indexOf(" "));
                }
                command = NameOfCommands.valueOf(str);
            } catch (IllegalArgumentException | NullPointerException e) {
                flag = false;
            }
            if (flag == true) {
                break;
            }
            System.out.println("НЕВЕРНАЯ КОМАНДА");
        }
        return new CommandData(command, parameter);
    }
}
