package lessons.serialization;

import lessons.Message;

import java.io.Serializable;

public class Serial implements Serializable {
    private Types type;
    private int version;
    private String name;
    private Message mes;

    public Serial(Types type, int version, String name, Message mes) {
        this.type = type;
        this.version = version;
        this.name = name;
        this.mes = mes;
    }

    public Serial() {
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message getMes() {
        return mes;
    }

    public void setMes(Message mes) {
        this.mes = mes;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Serial{" +
                "type=" + type +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", mes=" + mes +
                '}';
    }
}
