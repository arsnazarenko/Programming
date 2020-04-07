package library.сlassModel;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * Класс Локации
 */
public class Location implements Serializable {
    /**
     * Координата х.
     * Поле не может быть null.
     */
    private Long x;

    /**
     * Координата y.
     * Поле не может быть null.
     */
    private Double y;

    /**
     * Координата z.
     */
    private double z;

    /**
     * Название локации.
     * Поле не может быть null.
     */
    private String name;

    public Location(Long x, Double y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Location() {
    }

    ;

    @XmlElement
    public void setX(Long x) {
        this.x = x;
    }

    @XmlElement
    public void setY(Double y) {
        this.y = y;
    }

    @XmlElement
    public void setZ(double z) {
        this.z = z;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Long getX() {
        return x;
    }


    public Double getY() {
        return y;
    }


    public double getZ() {
        return z;
    }


    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.z, z) == 0 &&
                Objects.equals(x, location.x) &&
                Objects.equals(y, location.y) &&
                Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                '}';
    }

}
