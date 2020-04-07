package server.services;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Класс, отвечающий за чтение и запись объектов из/в файл
 */
public class JaxbWorker{


    /**
     * Метод, предоставляющий процесс сериализации объекта в файл формата xml
     * @param organizationCollection объект типа CollectionManager
     * @param  file файл для сохранения
     */
    public static void convertObjectToXml(CollectionManager organizationCollection, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(organizationCollection, file);
        } catch (JAXBException e) {
            System.out.println("Ошибка!");
        }
    }

    /**
     * Метод, предоставляющий процесс десериализации в объект типа CollectionManager
     * @param file файл, из которого берутся данные для коллекции
     * @return объект типа CollectionManager
     */
    public static CollectionManager fromXmlToObject(File file) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CollectionManager.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            return (CollectionManager) un.unmarshal(file);
        } catch (JAXBException e) {
            System.out.println("Ошибка!");
        }
        return null;
    }


}
