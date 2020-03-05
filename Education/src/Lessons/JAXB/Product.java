package Lessons.JAXB;
import Lessons.Message;

import javax.xml.bind.annotation.XmlElement;

public class Product {
    private String name;
    private int price;
    private String description;
    private Message message;


    public Product() {
    }

    public Product(String name, int price, Message message) {
        this.name = name;
        this.price = price;
        this.message = message;
    }

    public Product(String name, int price, String description, Message message) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
    @XmlElement
    public void setMessage(Message message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    @XmlElement
    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", message=" + message +
                '}';
    }
}
