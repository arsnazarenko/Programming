package project.server.services;

import project.server.CollectionManager;

import java.io.File;


/**
 * Интерфейс, содердащий два метода для сериализации объектов и наоборот
 */
public interface IFileWorker {
    void convertObjectToXml(CollectionManager organizationCollection, String filePath);
    CollectionManager fromXmlToObject(File file);
}
