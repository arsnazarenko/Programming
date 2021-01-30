package projectStructure.сommands;

import com.sun.xml.internal.ws.util.Pool;
import projectStructure.collectionDB.CollectionManager;
import projectStructure.сlassModel.Organization;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.function.DoubleToIntFunction;

/**
 * Класс, отвечающий за чтение и запись объектов из/в файл
 */
public class JaxbWorker implements IFileWorker {

    /**
     * Метод, предоставляющий процесс сериализации объекта в файл формата xml
     * @param organizationCollection объект типа CollectionManager
     * @param filePath имя файла, куда нужно записать объекты
     */
    public void convertObjectToXml(CollectionManager organizationCollection, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(CollectionManager.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(organizationCollection, new File(filePath));
        } catch (JAXBException e) {
            System.out.println("Ошибка!");
        }
    }

    /**
     * Метод, предоставляющий процесс десериализации в объект типа CollectionManager
     * @param filePath имя файла, хранящего объекты
     * @return объект типа CollectionManager
     */
    public CollectionManager fromXmlToObject(String filePath) {
        File file = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CollectionManager.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            file = new File(filePath);
            if (file.length() != 0) {
                return (CollectionManager) un.unmarshal(file);
            } else {
                return new CollectionManager();
            }
        } catch (JAXBException e) {
            System.out.println("Ошибка!");
        }
        return new CollectionManager();
    }


}
