package project.client.serialization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.client.commands.Command;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class SerializationManager implements ISerializationManager{
    static final Logger logger = LogManager.getLogger(SerializationManager.class.getName());
    public byte[] objectSerial(Object serial) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);
        ) {
            oos.writeObject(serial);
            oos.flush();
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println("ОШИБКА СЕРИАЛИЗАЦИИ");
        }
        return null;
    }

    public byte[] objectSerialtest(Object serial) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);
        ) {
            oos.writeObject(serial);
            oos.flush();
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            System.err.println("ОШИБКА СЕРИАЛИЗАЦИИ");
        }
        return null;
    }

    public Object objectDeserial(byte[] array) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(array);
             ObjectInputStream ois = new ObjectInputStream(bais)
        ) {
            return  ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            //System.err.println("ОШИБКА СЕРИАЛИЗАЦИИ");
            logger.error("Error ser", e);
        }
        return null;
    }

    public static void main(String[] args) {
        SerializationManager m= new SerializationManager();
        Deque<Integer> o = new ArrayDeque<>();
        o.addFirst(123);
        o.addFirst(123);
        o.addFirst(123);
        o.addFirst(123);
        o.addFirst(123);
        byte[] arr = m.objectSerialtest(o);
        Deque<Integer> d = (Deque<Integer>) m.objectDeserial(arr);
        d.addFirst(234234);
        System.out.println(d.getFirst().getClass());

    }
}

