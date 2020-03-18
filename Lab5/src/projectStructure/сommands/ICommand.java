package projectStructure.сommands;

import java.util.Scanner;

/**
 * Инетрфейс, задающий поведение каждой из команд
 */
public interface ICommand {
    /**
     * Метод выполнения, общий для всех команд. Для универсальности, каждой команде передается два параметра, но в классе {@link Receiver}
     * каждый метод работает лишь с тем аргументом, который нужен команде
     * @param reader
     * @param str
     */
    void execute(Scanner reader, String str);
    NameOfCommands getName();
}
