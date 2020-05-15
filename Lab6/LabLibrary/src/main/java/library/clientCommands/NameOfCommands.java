package library.clientCommands;

/**
 * Класс с перечисением всех возможных команд
 **/
public enum NameOfCommands {
    add("add{element} - Добавить новый элемент в коллекцию"),
    info("info - Вывести в стандартный поток вывода информацию о коллекции"),
    help("help - Вывести справку по доступным командам"),
    exit("exit - Завершить порграмму без сохранения в файл"),
    show("show - Вывести в стандартный поток вывода все элементы коллекции в строковом представлении"),
    update_id("update_id{element} - Обновить значение элемента колллекции, id которого равен заданному"),
    remove_by_id("remove_by_id id - Удалить элемент из колллекции по его id"),
    clear("clear - Отчистить коллекцию"),
    execute_script("execute_script file_name - Считать скрипт из указанного файла"),
    head("head - Вывести первый элемент коллекции"),
    add_if_min("add_if_min{element} - Добавить новый элемент в коллекцию, если его значение меньше чем у наименьшего элемента коллекции"),
    remove_lower("remove_lower{element} - удалить из коллекции все элементы, меньшие чем заданный"),
    max_by_employees_count("max_by_employees_count - Вывести любой элемент их коллекции, значение поля employeesCount которого является максимальным"),
    filter_contains_name("filter_contains_name name - Вывести элементы, значения поля name которых содержит заданную подстроку"),
    print_ascending("print_ascending - ывести элементы коллекции в порядке возрастания");

    private String commandHelp;


    NameOfCommands(String commandHelp) {
        this.commandHelp = commandHelp;
    }

    @Override
    public String toString() {
        return commandHelp;

    }
}
