package projectStructure.user;
import projectStructure.сommands.NameOfCommands;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Класс, отвечающий за чтение команд и их выполнения.
 */
public class ApplicationInterface {
    private Invoker invoker;


    public ApplicationInterface(Invoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Метод, отвечающий за загрузку объектов из файла в коллекцию и вызывающий метод с выполнением команд
     * @param in стандартный поток ввода
     * @param pathFileToSaveOrLoad путь к файлу для сохранения коллекции
     */
    public void startProgram(InputStream in,  String pathFileToSaveOrLoad) {
        invoker.load(null, pathFileToSaveOrLoad);
        circleExecute(in, pathFileToSaveOrLoad);
    }

    /**
     * Метод, служащий для считывания и выполнения комманд
     * @param in стандартный поток ввода
     * @param pathFileToSaveOrLoad путь к файлу для сохранения коллекции
     */

    public void circleExecute(InputStream in,  String pathFileToSaveOrLoad) {
        try(Scanner reader = new Scanner(in)) {
            outer:
            while (true) {
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
                switch (command) {
                    case add:
                        invoker.add(reader, parameter);
                        break;
                    case help:
                        invoker.help(reader, parameter);
                        break;
                    case info:
                        invoker.info(reader, parameter);
                        break;
                    case exit:
                        invoker.exit(reader, parameter);
                        break outer;
                    case save:
                        invoker.save(reader, pathFileToSaveOrLoad);
                        break;
                    case show:
                        invoker.show(reader, parameter);
                        break;
                    case head:
                        invoker.head(reader, parameter);
                        break;
                    case update_id:
                        invoker.updateId(reader, parameter);
                        break;
                    case remove_lower:
                        invoker.removeLower(reader, parameter);
                        break;
                    case clear:
                        invoker.clear(reader, parameter);
                        break;
                    case max_by_employees_count:
                        invoker.maxByEmployeesCount(reader, parameter);
                        break;
                    case add_if_min:
                        invoker.addIfMin(reader, parameter);
                        break;
                    case remove_by_id:
                        invoker.removeById(reader, parameter);
                        break;
                    case execute_script:
                        try (InputStream scriptStream = new FileInputStream(parameter)) {
                            System.out.println("С К Р И П Т");
                            circleExecute(scriptStream, pathFileToSaveOrLoad);
                            System.out.println("С К Р И П Т");
                        } catch (NoSuchElementException e) {
                            System.out.println("ОШИБКА СКРИПТА");
                        } catch (FileNotFoundException e) {
                            System.out.println("ОШИБКА ФАЙЛА");
                        } catch (IOException e) {
                            System.out.println("ОШИБКА ФАЙЛА");
                        }
                        break;
                    case print_ascending:
                        invoker.printAscending(reader, parameter);
                        break;
                    case filter_contains_name:
                        invoker.filterContainsName(reader, parameter);
                        break;
                }

            }
        }
    }
}
