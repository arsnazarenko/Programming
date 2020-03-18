package project.client.serialization;

import project.client.commands.Command;
import project.client.commands.uiCommands.AddCommand;
import project.client.сlassModel.*;

import java.io.*;
import java.util.Date;

public class SerializationManager {
    public<T extends Serializable> byte[] objectSerial(T serial) {
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

    public<T extends Serializable> T objectDeserial(byte[] array) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(array);
             ObjectInputStream ois = new ObjectInputStream(bais)
        ) {
            return (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

