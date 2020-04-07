package library.сlassModel;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * Класс с координатами
 */
public class Coordinates implements Serializable {
    /**
     * Коордирната х.
     * Значение больше -98.
     */
    private double x;

    /**
     * Координата y.
     * Значение больше -148.
     */
    private float y;

    public Coordinates(double x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    ;

    public double getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @XmlElement
    public void setX(double x) {
        this.x = x;
    }

    @XmlElement
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.x, x) == 0 &&
                Float.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
