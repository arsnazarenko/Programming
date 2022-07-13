package lessons.serialization;

import lessons.Message;

import java.io.*;

public class SerializationManager {
    public byte[] objectSerial(Serial serial) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);
        ) {
            oos.writeObject(serial);
            oos.flush();
            baos.flush();
            byte[] objectByte = baos.toByteArray();
            return objectByte;
        } catch (IOException e) {
            System.err.println("ОШИБКА СЕРИАЛИЗАЦИИ");
        }
        return null;
    }

    public Serial objectDeserial(byte[] array) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(array);
             ObjectInputStream ois = new ObjectInputStream(bais)
        ) {
            return (Serial) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        Serial serial = new Serial(Types.GOOD, 12, "awdawd", new Message("awdawd", 23));
        SerializationManager ser = new SerializationManager();
        byte[] test = ser.objectSerial(serial);
        Serial newSerial = ser.objectDeserial(test);
        System.out.println(newSerial);
    }
}

