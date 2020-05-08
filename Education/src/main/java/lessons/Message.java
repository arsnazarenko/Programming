package lessons;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Message implements Serializable {
    private String str;
    private int id;

    public Message() {};



    public Message(String str, int id) {
        this.str = str;
        this.id = id;
    }

    public String getStr() {
        return str;
    }
    @XmlElement
    public void setStr(String str) {
        this.str = str;
    }

    public int getId() {
        return id;
    }
    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[" + id + "\n] -- " + str;

    }








}
