package projectStructure.сommands;

import projectStructure.сlassModel.Organization;
import projectStructure.collectionDB.CollectionManager;

import java.util.*;
/**
 * Класс содержащий методы все команд приложения со свойствами <b>collectionManager</b>, <b>objectCreator</b> и <b>fileIO</b>.
 */
public class Receiver implements IReceiver {
    private CollectionManager collectionManager;
    private IObjectCreator objectCreator;
    private IFileWorker fileIO;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param collectionManager - объект класса, отвечающего за коллекцию, создаваемую приложением
     * @param objectCreator - создатель объектов
     * @param fileIO - объект класса, отвечающий за загрузку и сохранения коллекции в файл
     * @see Receiver#Receiver(CollectionManager, IObjectCreator, IFileWorker)
     */
    public Receiver(CollectionManager collectionManager, IObjectCreator objectCreator, IFileWorker fileIO) {
        this.collectionManager = collectionManager;
        this.objectCreator = objectCreator;
        this.fileIO = fileIO;
    }


    /**
     * Метод закгрузки объектов их файла в коллекцию
     * @param path - путь к файлу
     * @see Receiver#load(String)
     */
    public void load(String path) {

        CollectionManager tmpCol = fileIO.fromXmlToObject(path);
        if (!tmpCol.getOrgCollection().isEmpty()) {
            tmpCol.getOrgCollection().forEach(o1 -> {
                collectionManager.getOrgCollection().addFirst(o1);
                long counter = o1.getId();
                if (counter > ObjectCreator.objectCounter) {
                    ObjectCreator.objectCounter = counter + 1;
                }
            });
            System.out.println("ОБЪЕКТЫ ЗАГУЖЕНЫ");
        }
    }
    /**
     * Метод запуска скрипта
     */
    @Override
    public void executeScript() {
        System.out.println("СКРИПТ");
    }

    /**
     * Метод получения справки по командам
     * @see Receiver#help()
     */
    @Override
    public void help() {
        Arrays.asList(NameOfCommands.values()).forEach(System.out::println);
    }
    /**
     * Метод получения информации о коллекции. Выводит на экран
     * @see Receiver#help()
     */
    @Override
    public void info() {

        System.out.printf("Тип: %s\nКоличество элементов: %d\nДата инициализации: %s\n",
                collectionManager.getOrgCollection().getClass(), collectionManager.getOrgCollection().size(),
                collectionManager.getCreationCollectionDate());
    }

    /**
     * Метод, выводящий все элементы коллекции в строковом представлении
     * @see Receiver#show()
     */
    @Override
    public void show() {
        System.out.println(collectionManager);
    }

    /**
     * Метод добавляющий объект в коллекцию
     * @param reader способ чтения: ввод из файла или из стандартного ввода
     *@see Receiver#add(Scanner) ()
     */
    @Override
    public void add(Scanner reader) {
        collectionManager.getOrgCollection().addFirst(objectCreator.create(reader));
        ObjectCreator.objectCounter++;
    }

    /**
     * Метод, который обнавляет значение полей элемента коллекции по его id
     * @param reader способ чтения: ввод из файла или из стандартного ввода
     * @param strId id элемента, чьи поля надо заменить
     * @see Receiver#updateId(Scanner, String)
     */
    @Override
    public void updateId(Scanner reader, String strId) {
        Organization organization = null;
        try {
            Long id = Long.parseLong(strId);
            for (Organization elem : collectionManager.getOrgCollection()) {
                if (elem.getId() == id) {
                    organization = elem;
                }
            }
            if (collectionManager.getOrgCollection().remove(organization)) {
                collectionManager.getOrgCollection().addFirst(objectCreator.create(reader, id));
                System.out.println("ОБЪЕКТ ОБНОВЛЕН");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка вода id");
        }
    }

    /**
     * Метод, который удаляет элемент коллекции по его id
     * @param strId id элемента, который надо удалить
     * @see Receiver#removeById(String)
     */

    @Override
    public void removeById(String strId) {
        Organization deleteElem = null;
        try {
            Long id = Long.parseLong(strId);
            for (Organization elem : collectionManager.getOrgCollection()) {
                if (elem.getId() == id) {
                    deleteElem = elem;
                    break;
                }
            }
            if (deleteElem != null) {
                collectionManager.getOrgCollection().remove(deleteElem);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка вода id");
        }
    }

    /**
     * Метод, который удаляет все элементы коллекции
     * @see Receiver#clear()
     */
    @Override
    public void clear() {
        collectionManager.getOrgCollection().clear();
    }

    /**
     * Метод, который сохраняет все элементы коллекции в файл
     * @param path имя файла, в который сохраняется коллекция
     * @see Receiver#save(String)
     */

    @Override
    public void save(String path) {
        fileIO.convertObjectToXml(collectionManager, path);
    }

    /**
     * Метод, завершающий программу
     * @param reader параметр, для закрытия потока ввода
     * @see Receiver#exit(Scanner)
     */
    @Override
    public void exit(Scanner reader) {
        System.out.println("ДО СВИДАНИЯ :)");

    }

    /**
     * Метод, который выводит первый элемент коллекции
     * @see Receiver#head()
     */
    @Override
    public void head() {
        try{System.out.println(collectionManager.getOrgCollection().getFirst());
        } catch (NoSuchElementException e) {
            System.out.println("КОЛЛЕКЦИЯ ПУСТА");
        }
    }

    /**
     * Метод, который добавляет объект в коллекцию, если он меньше, чем минимальный элемент коллекции
     * @param reader способ чтения
     * @see Receiver#addIfMin(Scanner)
     */
    @Override
    public void addIfMin(Scanner reader) {
        try{Organization minOrg = Collections.min(collectionManager.getOrgCollection());
            Organization org = objectCreator.create(reader);
            if (minOrg.compareTo(org) > 0) {
                collectionManager.getOrgCollection().addFirst(org);
                ObjectCreator.objectCounter++;
                System.out.println("ОБЪЕКТ ДОБАВЛЕН");
            }
        }
        catch (NoSuchElementException e) {
            System.out.println("КОЛЛЕКЦИЯ ПУСТА");
        }
    }

    /**
     * Метод, который удаляет все элементы меньше заданного
     * @param reader способ чтения
     * @see Receiver#removeLower(Scanner)
     */

    @Override
    public void removeLower(Scanner reader) {
        Organization myOrg = objectCreator.create(reader);
        collectionManager.getOrgCollection().removeIf(elem -> myOrg.compareTo(elem) > 0);
    }

    /**
     * Метод, который выводит любой элеменет, значения поля employeesCount
     *@see Receiver#maxByEmployeeCount()
     */

    @Override
    public void maxByEmployeeCount() {
        List<Organization> forMax = new ArrayList<>(collectionManager.getOrgCollection());
        Collections.sort(forMax, (o1, o2) -> -(o1.getEmployeesCount() - o2.getEmployeesCount()));
        System.out.println(forMax.get(0));

    }

    /**
     * Метод, который выводит элеменьет, поле name которого содержит заданную подстроку
     * @param str заданая подстрока, по которому надо производить фильтрацию
     * @see Receiver#filterContainsName(String)
     *
     */

    @Override
    public void filterContainsName(String str) {
        collectionManager.getOrgCollection().forEach(o1 -> {if(o1.getName().contains(str)){
            System.out.println(o1); }});
    }

    /**
     * Метод, который выводит элементы коллекции в порядке возрастания
     * @see Receiver#printAscending() (String)
     */
    @Override
    public void printAscending() {
        List<Organization> forSort = new ArrayList<>(collectionManager.getOrgCollection());
        Collections.sort(forSort);
    }
}
