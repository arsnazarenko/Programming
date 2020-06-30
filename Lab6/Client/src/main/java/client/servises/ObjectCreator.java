package client.servises;



import library.сlassModel.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * Класс порождающий объект объект.
 */
public class ObjectCreator implements IObjectCreator {

    private ObjectDataValidator validator;

    public ObjectCreator(ObjectDataValidator validator) {
        this.validator = validator;
    }

    /**
     * Метод для инициализации поля name {@link ObjectCreator#setName(Scanner)}
     * @return возвращает значение
     */
    private String setName(Scanner rd) {
        String str = null;
        while (true) {
            System.out.print("name: ");
            str = rd.nextLine();
            if (validator.stringFieldValidate(str)) {
                System.out.println();
                return str;
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
        String line = null;
        while (true) {
            System.out.print("x: ");
            line = rd.nextLine();
            if(validator.coordsXValidate(line)) {
                res = Double.parseDouble(line);
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
        String line = null;
        while (true) {
            System.out.print("y: ");
            line = rd.nextLine();
            if(validator.coordsYValidate(line)) {
                res = Float.parseFloat(line);
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
        String line = null;
        while (true) {
            System.out.print("annualTurnover: ");
            line = rd.nextLine();
            if(validator.annualTurnoverValidate(line)) {
                System.out.println();
                if (line.equals("")) {
                    return res;
                }
                return Double.parseDouble(line);
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
        String line = null;
        while (true) {
            System.out.print("employeesCount: ");
            line = rd.nextLine();
            if(validator.employeesCountValidate(line)) {
                res = Integer.parseInt(line);
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
        String line = null;
        while (true) {
            System.out.print("type " + Arrays.asList(OrganizationType.values()) + " : ");
            line = rd.nextLine();
            if(validator.organizationTypeValidate(line)) {
                res = OrganizationType.valueOf(line);
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
        String str = null;
        while (true) {
            System.out.print("street: ");
            str = rd.nextLine();
            if (validator.stringFieldValidate(str)) {
                System.out.println();
                return str;
            }
            System.out.println("Неверный формат");
        }
    }

    /**
     * Метод для инициализации поля zipCode {@link ObjectCreator#setZipCode(Scanner)}
     * @return возвращает значение типа String
     */
    private String setZipCode(Scanner rd) {
        String str = null;
        while (true) {
            System.out.print("zipCode: ");
            str = rd.nextLine();
            if (validator.zipCodeValidate(str)) {
                System.out.println();
                return str;
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
        String line = null;
        while (true) {
            System.out.print("x: ");
            line = rd.nextLine();
            if(validator.locationXValidate(line)) {
                System.out.println();
                res = Long.parseLong(line);
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
        String line = null;
        while (true) {
            System.out.print("y: ");
            line = rd.nextLine();
            if(validator.locationYValidate(line)) {
                res = Double.parseDouble(line);
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
        double res;
        String line = null;
        while (true) {
            System.out.print("z: ");
            line = rd.nextLine();
            if(validator.locationZValidate(line)) {
                res = Double.parseDouble(line);
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
            if (validator.stringFieldValidate(str)) {
                System.out.println();
                return str;
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
        String userName = null;
        Long id = null;
        String name = setName(rd);
        Coordinates coordinates = setCoordinates(rd);
        Double annualTurnover = setAnnualTurnover(rd);
        Integer employeesCount = setEmployeesCount(rd);
        OrganizationType type = setOrganizationType(rd);
        Address officialAddress = setOfficialAddress(rd);
        Date creationDate = null;
        return new Organization(userName, id, name, coordinates, creationDate, employeesCount,
                type, annualTurnover, officialAddress);
    }





}
