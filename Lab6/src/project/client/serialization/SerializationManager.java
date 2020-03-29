package project.client.serialization;

import project.client.commands.Command;

import java.io.*;

public class SerializationManager {
    public<T extends Serializable> byte[] objectSerial(T serial) {
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
            System.err.println("ОШИБКА СЕРИАЛИЗАЦИИ");
        }
        return null;
    }

    //public static void main(String[] args) {
    //    SerializationManager m= new SerializationManager();
    //    Coordinates coordinates = new Coordinates(12, 12);
    //    Organization organization = new Organization(12L, "awd", coordinates, new Date(), 12, OrganizationType.COMMERCIAL, 12.23,
    //            new Address("awdawd", "awdawd", new Location(12L, 1d, 12, "Ad")));
    //    Command c = new AddCommand(organization);
    //    byte[] arr = m.objectSerial(c);
    //c = (Command) m.objectDeserial(arr);
    //    AddCommand ac = (AddCommand) c;
    //    System.out.println(ac.getOrganization());
    //}
}

