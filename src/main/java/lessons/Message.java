package lessons;

import java.io.Serializable;

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
    public void setStr(String str) {
        this.str = str;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[" + id + "\n] -- " + str;

    }
}
