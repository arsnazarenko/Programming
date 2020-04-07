package library.сlassModel;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Класс, хранящий основные сведения об Организации
 * Основной класс, чьи объекты будут хранится в коллекции
 */
public class Organization implements Comparable<Organization>, Serializable {
    /**
     * Уникальный индификатор.
     * Поле не может быть null. Значение больше 0.
     * Генерируется автоматически.
     */
    private Long id;

    /**
     * Название организации.
     * Поле не может быть null или пустой строкой.
     */
    private String name;

    /**
     * Координаты.
     * Поле не может быть null.
     */
    private Coordinates coordinates;

    /**
     * Дата
     * Поле не может быть null.
     * Генерируется автоматически
     */
    private Date creationDate;

    /**
     * Количество работников.
     * Поле не может быть null.Значение больше 0.
     */
    private Integer employeesCount;

    /**
     * Тип организации.
     */
    private OrganizationType type;

    /**
     * Годовой оборот.
     * Поле может быть null. Значение больше 0.
     */
    private Double annualTurnover;

    /**
     * Адрес.
     * Поле может быть null.
     */
    private Address officialAddress;

    public Organization(Long id, String name, Coordinates coordinates, Date creationDate, Integer employeesCount, OrganizationType type, Double annualTurnover, Address officialAddress) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.employeesCount = employeesCount;
        this.type = type;
        this.annualTurnover = annualTurnover;
        this.officialAddress = officialAddress;
    }

    public Organization() {
    }

    ;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Double getAnnualTurnover() {
        return annualTurnover;
    }

    public Integer getEmployeesCount() {
        return employeesCount;
    }

    public OrganizationType getType() {
        return type;
    }

    public Address getOfficialAddress() {
        return officialAddress;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @XmlElement
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @XmlElement
    public void setEmployeesCount(Integer employeesCount) {
        this.employeesCount = employeesCount;
    }

    @XmlElement
    public void setType(OrganizationType type) {
        this.type = type;
    }

    @XmlElement
    public void setAnnualTurnover(Double annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    @XmlElement
    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(coordinates, that.coordinates) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(annualTurnover, that.annualTurnover) &&
                Objects.equals(employeesCount, that.employeesCount) &&
                type == that.type &&
                Objects.equals(officialAddress, that.officialAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, annualTurnover, employeesCount, type, officialAddress);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", employeesCount=" + employeesCount +
                ", type=" + type +
                ", annualTurnover=" + annualTurnover +
                ", officialAddress=" + officialAddress +
                '}';
    }

    @Override
    public int compareTo(Organization o) {
        return this.creationDate.compareTo(o.getCreationDate());
    }


}



