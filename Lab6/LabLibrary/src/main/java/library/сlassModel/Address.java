package library.сlassModel;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * Класс, хранящий основные сведения об адресе.
 */
public class Address implements Serializable {
    /**
     * Название улицы
     * Поле не может быть null или пустой строкой.
     */
    private String street;

    /**
     * Индекс.
     * Поле не может быть null. Длина строки не меньше 7.
     */
    private String zipCode;

    /**
     * Город.
     * Поле может быть null.
     */
    private Location town;

    public Address(String street, String zipCode, Location town) {
        this.street = street;
        this.zipCode = zipCode;
        this.town = town;
    }

    public Address() {
    }


    public String getStreet() {
        return street;
    }


    public String getZipCode() {
        return zipCode;
    }


    public Location getTown() {
        return town;
    }

    @XmlElement
    public void setStreet(String street) {
        this.street = street;
    }

    @XmlElement
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @XmlElement
    public void setTown(Location town) {
        this.town = town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
                Objects.equals(zipCode, address.zipCode) &&
                Objects.equals(town, address.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, zipCode, town);
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", town=" + town +
                '}';
    }

}

