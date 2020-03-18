package project.client.ui;

import project.client.сlassModel.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * Класс порождающий объект объект. Свойства <b>objectCounter</b>
 */
public class ObjectCreator implements IObjectCreator {
    public static long objectCounter = 1;

    /**
     * Метод для инициализации поля name {@link ObjectCreator#setName(Scanner)}
     * @return возвращает значение
     */
    private String setName(Scanner rd) {
        String str = null;
        while (true) {
            System.out.print("name: ");
            str = rd.nextLine();
            if (str != null) {
                if (!str.equals("")) {

                    System.out.println();
                    return str;
                }
            }
            System.out.println("Неверный формат");
        }

    }

    /**
     * Метод для инициализации поля coordinates {@link ObjectCreator#setCoordinates(Scanner)}
     * @return возвращает значение типа Coordinates
     */
    private Coordinates setCoordinates(Scanner rd) {
        System.out.println("Coordinates");
        Double x = setX(rd);
        Float y = setY(rd);
        return new Coordinates(x, y);

    }

    /**
     * Метод для инициализации поля x {@link ObjectCreator#setX(Scanner)}
     * @return возвращает значение типа double
     */
    private double setX(Scanner rd) {
        Double res = null;
        boolean flag;
        String line = null;
        while (true) {
            flag = true;
            System.out.print("x: ");
            try {
                line = rd.nextLine();
                res = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                flag = false;
            }
            if (flag == true && res > -98) {

                System.out.println();
                return res;
            }
            System.out.println("Неверный формат");
        }

    }

    /**
     * Метод для инициализации поля y {@link ObjectCreator#setY(Scanner)}
     * @return возвращает значение типа float
     */
    private float setY(Scanner rd) {
        Float res = null;
        boolean flag;
        String line = null;
        while (true) {
            flag = true;
            System.out.print("y: ");
            try {
                line = rd.nextLine();
                res = Float.parseFloat(line);
            } catch (NumberFormatException e) {
                flag = false;
            }

            if (flag && res > -148) {

                System.out.println();
                return res;
            }
            System.out.println("Неверный формат");
        }
    }

    /**
     * Метод для инициализации поля annualTurnover {@link ObjectCreator#setAnnualTurnover(Scanner)}
     * @return возвращает значение Double
     */
    private Double setAnnualTurnover(Scanner rd) {
        Double res = null;
        boolean flag;
        String line = null;
        while (true) {
            flag = true;
            System.out.print("annualTurnover: ");
            try {
                line = rd.nextLine();
                res = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                if (line.equals("")) {
                    System.out.println();
                    return res;
                }
                flag = false;
            }

            if (flag && res > 0) {

                System.out.println();
                return res;
            }
            System.out.println("Неверный формат");
        }
    }

    /**
     * Метод для инициализации поля employeesCount {@link ObjectCreator#setEmployeesCount(Scanner)}
     * @return возвращает значение типа Integer
     */
    private Integer setEmployeesCount(Scanner rd) {
        Integer res = null;
        boolean flag;
        String line = null;
        while (true) {
            flag = true;
            System.out.print("employeesCount: ");
            try {
                line = rd.nextLine();
                res = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                flag = false;
            }
            if (flag && res > 0) {
                System.out.println();
                return res;
            }
            System.out.println("Неверный формат");
        }
    }

    /**
     * Метод для инициализации поля organizationType {@link ObjectCreator#setOrganizationType(Scanner)}
     * @return возвращает значение типа Organization
     */
    private OrganizationType setOrganizationType(Scanner rd) {
        OrganizationType res = null;
        boolean flag;
        String line = null;
        while (true) {
            flag = true;
            System.out.print("type " + Arrays.asList(OrganizationType.values()) + " : ");
            try {
                line = rd.nextLine();
                res = OrganizationType.valueOf(line);
            } catch (IllegalArgumentException e) {
                flag = false;
            }

            if (flag) {
                System.out.println();
                return res;
            }
            System.out.println("Неверный формат");
        }
    }

    /**
     * Метод для инициализации поля officialAddress {@link ObjectCreator#setOfficialAddress(Scanner)}
     * @return возвращает объект типа Address
     */
    private Address setOfficialAddress(Scanner rd) {
        System.out.print("officialAddress (нажатие ENTER - null): ");
        String str = rd.nextLine();
        if (str == null) {
            return null;
        } else if (str.equals("")) {
            System.out.println();
            return null;
        } else {
            String street = setStreet(rd);
            String zipCode = setZipCode(rd);
            Location town = setTown(rd);
            System.out.println();
            return new Address(street, zipCode, town);
        }

    }

    /**
     * Метод для инициализации поля street {@link ObjectCreator#setStreet(Scanner)}
     * @return возвращает значение типа String
     */
    private String setStreet(Scanner rd) {
        String res = null;
        while (true) {
            System.out.print("street: ");
            res = rd.nextLine();
            if (res == null) {
                return res;
            } else if (res.equals("")) {
                System.out.println();
                return res;
            } else {
                System.out.println();
                return res;
            }
        }
    }

    /**
     * Метод для инициализации поля zipCode {@link ObjectCreator#setZipCode(Scanner)}
     * @return возвращает значение типа String
     */
    private String setZipCode(Scanner rd) {
        String res = null;
        boolean flag;
        while (true) {
            System.out.print("zipCode: ");
            res = rd.nextLine();
            if (res == null) {
                return res;
            } else if (res.equals("")) {
                System.out.println();
                return res;
            } else if (res.length() >= 7) {

                System.out.println();
                return res;
            }
            System.out.println("Неверный формат");
        }
    }

    /**
     * Метод для инициализации поля Town {@link ObjectCreator#setTown(Scanner)}
     * @return возвращает значение ипа Location
     */
    private Location setTown(Scanner rd) {
        System.out.print("town (нажатие ENTER - null): ");
        String str = rd.nextLine();
        if (str == null) {
            return null;

        } else if (str.equals("")) {
            System.out.println();
            return null;
        } else {
            Long x = setLocationX(rd);
            Double y = setLocationY(rd);
            double z = setLocationZ(rd);
            String name = setLocationName(rd);
            System.out.println();
            return new Location(x, y, z, name);
        }
    }

    /**
     * Метод для инициализации поля x {@link ObjectCreator#setLocationX(Scanner)}
     * @return возвращает значение типа Long
     */
    private Long setLocationX(Scanner rd) {
        Long res = null;
        boolean flag;
        String line = null;
        while (true) {
            flag = true;
            System.out.print("x: ");
            try {
                line = rd.nextLine();
                res = Long.parseLong(line);
            } catch (NumberFormatException e) {
                flag = false;
            }
            if (flag) {
                System.out.println();
                return res;
            }
            System.out.println("Неверный формат");
        }
    }

    /**
     * Метод для инициализации поля y {@link ObjectCreator#setName(Scanner)}
     * @return возвращает значение типа Double
     */
    private Double setLocationY(Scanner rd) {
        Double res = null;
        boolean flag;
        String line = null;
        while (true) {
            flag = true;
            System.out.print("y: ");
            try {
                line = rd.nextLine();
                res = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                flag = false;
            }
            if (flag) {
                System.out.println();
                return res;
            }
            System.out.println("Неверный формат");
        }
    }

    /**
     * Метод для инициализации поля Z {@link ObjectCreator#setLocationZ(Scanner)}
     * @return возвращает значени типа double
     */
    private double setLocationZ(Scanner rd) {
        Double res = null;
        boolean flag;
        String line = null;
        while (true) {
            flag = true;
            System.out.print("z: ");
            try {
                line = rd.nextLine();
                res = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                flag = false;
            }

            if (flag) {
                System.out.println();
                return res;
            }
            System.out.println("Неверный формат");
        }
    }

    /**
     * Метод для инициализации поля locationName {@link ObjectCreator#setLocationName(Scanner)
     * @return возвращает значение типа String
     */
    private String setLocationName(Scanner rd) {
        String str = null;
        boolean flag;
        while (true) {
            flag = true;
            System.out.print("name: ");
            str = rd.nextLine();
            if (str != null) {
                if (!str.equals("")) {
                    System.out.println();
                    return str;
                }
            }
            System.out.println("Неверный формат");
        }


    }
    /**
     * Метод для создания объекта типа {@link Organization}
     * @see ObjectCreator#create(Scanner)
     * @return возвращает значение объекта
     */
    public Organization create(Scanner rd) {
        Long id = null;
        String name = setName(rd);
        Coordinates coordinates = setCoordinates(rd);
        Double annualTurnover = setAnnualTurnover(rd);
        Integer employeesCount = setEmployeesCount(rd);
        OrganizationType type = setOrganizationType(rd);
        Address officialAddress = setOfficialAddress(rd);
        Date creationDate = null;
        return new Organization(id, name, coordinates, creationDate, employeesCount,
                type, annualTurnover, officialAddress);
    }
    /**
     * Метод для создания объекта типа {@link Organization}
     * @see ObjectCreator#create(Scanner, Long)
     * @return возвращает значение объекта
     */
    public Organization create(Scanner rd, Long thatId) {
        Long id = thatId;
        String name = setName(rd);
        Coordinates coordinates = setCoordinates(rd);
        Double annualTurnover = setAnnualTurnover(rd);
        Integer employeesCount = setEmployeesCount(rd);
        OrganizationType type = setOrganizationType(rd);
        Address officialAddress = setOfficialAddress(rd);
        Date creationDate = null;
        return new Organization(id, name, coordinates, creationDate, employeesCount,
                type, annualTurnover, officialAddress);
    }
}
