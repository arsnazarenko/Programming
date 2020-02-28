package projectStructure.user;

import projectStructure.сommands.ICommand;
import projectStructure.сommands.NameOfCommands;
import projectStructure.сommands.listOfCommands.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс, который декларирует, как пользователь может взаимодействовать с программой
 */

public class Invoker {
    private ICommand add;
    private ICommand info;
    private ICommand exit;
    private ICommand help;
    private ICommand show;
    private ICommand update_id;
    private ICommand clear;
    private ICommand head;
    private ICommand print_ascending;
    private ICommand add_if_min;
    private ICommand remove_by_id;
    private ICommand remove_lower;
    private ICommand max_by_employees_count;
    private ICommand filter_contains_name;
    private ICommand save;
    private ICommand load;



    public Invoker(ICommand add, ICommand info, ICommand exit,
                   ICommand help, ICommand show, ICommand update_id,
                   ICommand clear, ICommand head, ICommand print_ascending,
                   ICommand add_if_min, ICommand remove_by_id,
                   ICommand remove_lower, ICommand max_by_employees_count,
                   ICommand filter_contains_name, ICommand save,
                   ICommand load) {
        this.add = add;
        this.info = info;
        this.exit = exit;
        this.help = help;
        this.show = show;
        this.update_id = update_id;
        this.clear = clear;
        this.head = head;
        this.print_ascending = print_ascending;
        this.add_if_min = add_if_min;
        this.remove_by_id = remove_by_id;
        this.remove_lower = remove_lower;
        this.max_by_employees_count = max_by_employees_count;
        this.filter_contains_name = filter_contains_name;
        this.save = save;
        this.load = load;
    }


    public void add(Scanner rd, String str) {
        add.execute(rd, str);
    }

    public void help(Scanner rd, String str) {
        help.execute(rd, str);
    }

    public void info(Scanner rd, String str) {
        info.execute(rd, str);
    }

    public void exit(Scanner rd, String str) {
        exit.execute(rd, str);
    }

    public void show(Scanner rd, String str) {
        show.execute(rd, str);
    }

    public void updateId(Scanner rd, String str) {
        update_id.execute(rd, str);
    }

    public void clear(Scanner rd, String str) {
        clear.execute(rd, str);
    }

    public void head(Scanner rd, String str) {
        head.execute(rd, str);
    }

    public void printAscending(Scanner rd, String str) {
        print_ascending.execute(rd, str);
    }

    public void addIfMin(Scanner rd, String str) {
        add_if_min.execute(rd, str);
    }

    public void removeById(Scanner rd, String str) {
        remove_by_id.execute(rd, str);
    }

    public void removeLower(Scanner rd, String str) {
        remove_lower.execute(rd, str);
    }

    public void maxByEmployeesCount(Scanner rd, String str) {max_by_employees_count.execute(rd, str);}

    public void filterContainsName(Scanner rd, String str) {filter_contains_name.execute(rd, str);}

    public void save(Scanner rd, String str) {save.execute(rd, str);}

    public void load(Scanner rd, String str) {load.execute(rd, str);}


}