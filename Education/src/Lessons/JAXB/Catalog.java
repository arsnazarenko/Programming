package Lessons.JAXB;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
public class Catalog {
    private List<Product> products;

    public Catalog() {
        this.products = new ArrayList<>();
    }


    public boolean add(Product product) {
        if (product != null) {
            products.add(product);
            return true;
        }
        return false;
    }
    @XmlElement
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "products=" + products +
                '}';
    }
}
