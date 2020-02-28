package projectStructure.сommands;

import java.util.Scanner;

/**
 * Интерфейс, содержащий список методов, отвечающих за реализацию команд
 */
public interface IReceiver {


    void help();

    void info();

    void show();

    void add(Scanner reader);

    void updateId(Scanner reader, String strId);

    void removeById(String strId);

    void clear();

    void save(String path);

    void exit(Scanner reader);

    void head();

    void addIfMin(Scanner scanner);

    void removeLower(Scanner reader);

    void maxByEmployeeCount();

    void filterContainsName(String str);

    void printAscending();

    void load(String filePath);

    void executeScript();




}
